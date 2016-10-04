package com.rpg.prueba.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.rpg.prueba.levels.Level;
import com.rpg.prueba.sprites.ChampionSprite;
import com.rpg.prueba.sprites.StationarySprite;


public class View {
    private Array<ChampionSprite> sprites;
    private Array<ChampionSprite> sortedUpSprites;
    private Array<ChampionSprite> sortedDownSprites;
    private Array<StationarySprite> stationarySprites;
    private float red;
    private float green;
    private float blue;

    private Stage stage;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer mapRenderer;
    private int[] baseLayer;
    private int[] underlayer1;
    private int[] underlayer2;
    private int[] overLayer;

    private SpriteBatch spriteBatch;

    private Label hpLabel;
    private Label hp;

    private Skin skin;

    private BitmapFont font25;

    private Array<BitmapFont> fonts;

    private GameInputProcessor inputProcessor;

    public View(Level level) {
        stage = new Stage();
        inputProcessor = new GameInputProcessor();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 48f, Gdx.graphics.getHeight() / 48f);
        camera.update();

        sprites = level.getSprites();
        stationarySprites = level.getStationarySprites();
        red = level.getRed();
        green = level.getGreen();
        blue = level.getBlue();
        spriteBatch = new SpriteBatch();

        // Hide the mouse cursor.
        Gdx.input.setCursorCatched(true);

        TiledMap map = level.getMap();
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 48f);
        baseLayer = new int[1];
        baseLayer[0] = 0;
        underlayer1 = new int[1];
        underlayer1[0] = 1;
        underlayer2 = new int[1];
        underlayer2[0] = 2;
        overLayer = new int[1];
        overLayer[0] = 3;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.classpath("fonts/advocut-webfont.ttf")/*new FileHandle("fonts/advocut-webfont.ttf")*/);
        // BitmapFont font15 = generator.generateFont(15);
        font25 = generator.generateFont(25);
        generator.dispose();

        fonts = new Array<BitmapFont>();
        fonts.add(font25);

        setupHUD();
    }

    public void setupHUD() {
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", font25);

        // Configure a TextButtonStyle and name it "default".
        // Skin resources are stored by type, so this doesn't overwrite the font.
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().left();
        stage.addActor(table);

       /* hpLabel = new Label("  HP:  ", skin);
        hp = new Label(Integer.toString(ChampionSprites.get(0).getHP()), skin);
        table.add(hpLabel);
        table.add(hp).width(50);*/
    }

    public void render() {
        Gdx.gl.glClearColor(red, green, blue, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(sprites.get(0).getX() + 1, sprites.get(0).getY(), 0);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);

        mapRenderer.render(baseLayer);
        mapRenderer.render(underlayer1);
        mapRenderer.render(underlayer2);

        sortedUpSprites = new Array<ChampionSprite>(sprites);
        /*sortedUpChampionSprites.sort(new ChampionSpriteUpComparator());*/

        // sortedDownChampionSprites = new Array<ChampionSprite>(ChampionSprites);
        // sortedDownChampionSprites.sort(new ChampionSpriteDownComparator());

        spriteBatch.begin();
        renderStationaryChampionSprites();
        renderChampionSprites();
        // renderChampionSpritesTop();
        renderStationaryChampionSpritesTop();
        spriteBatch.end();

        mapRenderer.render(overLayer);

        //hp.setText(Integer.toString(ChampionSprites.get(0).getHP()));
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
    }

    private void renderChampionSprites() {
    	
    	ShaderProgram shader= this.colorShader();
    	Color c;
    	//Usar los lamdas de java 8
        for (ChampionSprite sprite : sortedUpSprites) {
        	sprite.setAnimationTime(Gdx.graphics.getDeltaTime());
              //Hacer State
            switch (sprite.getState()) {
                case standFront:
                	sprite.setCurrentFrame(sprite.getStandFrontFrame());
                    break;
                case standBack:
                	sprite.setCurrentFrame(sprite.getStandBackFrame());
                    break;
                case standLeft:
                	sprite.setCurrentFrame(sprite.getStandLeftFrame());
                    break;
                case standRight:
                	sprite.setCurrentFrame(sprite.getStandRightFrame());
                    break;
                case walkFront:
                	sprite.setCurrentFrame(sprite.getWalkFrontFrame());
                    break;
                case walkBack:
                	sprite.setCurrentFrame(sprite.getWalkBackFrame());
                    break;
                case walkLeft:
                	sprite.setCurrentFrame(sprite.getWalkLeftFrame());
                    break;
                case walkRight:
                	sprite.setCurrentFrame(sprite.getWalkRightFrame());
                    break;
                case attackFront:
                	c=spriteBatch.getColor();
                	sprite.setCurrentFrame(sprite.getStandFrontFrame());
                	spriteBatch.setColor(Color.BLUE);
                    sprite.setColor(Color.BLUE);
                    spriteBatch.draw(sprite.getTexture(),sprite.getX(),sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),sprite.getWidth(),sprite.getHeight(),1,1);
                    spriteBatch.setColor(c);
                    
                    break;
                case attackBack:
                	c=spriteBatch.getColor();
                	sprite.setCurrentFrame(sprite.getStandBackFrame());
                	spriteBatch.setColor(Color.BLUE);
                    sprite.setColor(Color.BLUE);
                    spriteBatch.draw(sprite.getTexture(),sprite.getX(),sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),sprite.getWidth(),sprite.getHeight(),1,1);
                    spriteBatch.setShader(null);
                    spriteBatch.setColor(c);
                    break;
                case attackLeft:
                	c=spriteBatch.getColor();
                	sprite.setCurrentFrame(sprite.getStandLeftFrame());
                	spriteBatch.setColor(Color.BLUE);
                    sprite.setColor(Color.BLUE);
                    spriteBatch.draw(sprite.getTexture(),sprite.getX(),sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),sprite.getWidth(),sprite.getHeight(),1,1);
                    spriteBatch.setShader(null);
                    spriteBatch.setColor(c);
                	break;
                case attackRight:
                	c=spriteBatch.getColor();
                	sprite.setCurrentFrame(sprite.getStandRightFrame());
                	spriteBatch.setColor(Color.BLUE);
                    sprite.setColor(Color.BLUE);
                    spriteBatch.draw(sprite.getTexture(),sprite.getX(),sprite.getY(),sprite.getOriginX(),sprite.getOriginY(),sprite.getWidth(),sprite.getHeight(),1,1);
                    spriteBatch.setShader(null);
                    spriteBatch.setColor(c);
                	break;
                case death:
                	sprite.setCurrentFrame(sprite.getDeathFrame());
                    break;
            }

            if (!sprite.isFacingLeft()) {
                spriteBatch.draw(sprite.getCurrentFrame(), sprite.getX(), sprite.getY(),
                                 ChampionSprite.SIZE, ChampionSprite.SIZE);
            } else {
                spriteBatch.draw(sprite.getCurrentFrame(), sprite.getX() + ChampionSprite.SIZE, sprite.getY(),
                                 -ChampionSprite.SIZE, ChampionSprite.SIZE);
            }
        }
    }

    private ShaderProgram colorShader() {
    	String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
            String fragmentShader = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "varying LOWP vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords).a;\n" //
                + "}";

            return new ShaderProgram(vertexShader, fragmentShader);
	}

	private void renderChampionSpritesTop() {
        for (ChampionSprite sprite : sortedDownSprites) {
        	sprite.setAnimationTime(Gdx.graphics.getDeltaTime());

            switch (sprite.getState()) {
                case standFront:
                	sprite.setCurrentFrameTop(sprite.getStandFrontFrame());
                    break;
                case standBack:
                	sprite.setCurrentFrameTop(sprite.getStandBackFrame());
                    break;
                case standLeft:
                	sprite.setCurrentFrameTop(sprite.getStandLeftFrame());
                    break;
                case standRight:
                	sprite.setCurrentFrameTop(sprite.getStandRightFrame());
                    break;
                case walkFront:
                	sprite.setCurrentFrameTop(sprite.getWalkFrontFrame());
                    break;
                case walkBack:
                	sprite.setCurrentFrameTop(sprite.getWalkBackFrame());
                    break;
                case walkLeft:
                	sprite.setCurrentFrameTop(sprite.getWalkLeftFrame());
                    break;
                case walkRight:
                	sprite.setCurrentFrameTop(sprite.getWalkRightFrame());
                    break;
                case attackFront:
                	sprite.setCurrentFrameTop(sprite.getAttackFrontFrame());
                    
                    break;
                case attackBack:
                	sprite.setCurrentFrameTop(sprite.getAttackBackFrame());
                    
                    break;
                case attackLeft:
                	sprite.setCurrentFrameTop(sprite.getAttackLeftFrame());
                    
                    break;
                case attackRight:
                	sprite.setCurrentFrameTop(sprite.getAttackRightFrame());
                    
                    break;
                case death:
                	sprite.setCurrentFrameTop(sprite.getDeathFrame());
                    break;
            }

            if (!sprite.isFacingLeft()) {
                spriteBatch.draw(sprite.getCurrentFrameTop(), sprite.getX(), sprite.getY() + 1,
                        ChampionSprite.SIZE, ChampionSprite.SIZE);
            } else {
                spriteBatch.draw(sprite.getCurrentFrameTop(), sprite.getX() + ChampionSprite.SIZE, sprite.getY() + 1,
                        -ChampionSprite.SIZE, ChampionSprite.SIZE);
            }
        }
    }

    private void renderStationaryChampionSprites() {
        for (StationarySprite stationaryChampionSprite : stationarySprites) {
            stationaryChampionSprite.setAnimationTime(Gdx.graphics.getDeltaTime());

            stationaryChampionSprite.setCurrentFrame(stationaryChampionSprite.getAnimationFrame());

            spriteBatch.draw(stationaryChampionSprite.getCurrentFrame(), stationaryChampionSprite.getX(), stationaryChampionSprite.getY(),
                             stationaryChampionSprite.getSize(), stationaryChampionSprite.getSize());
        }
    }

    private void renderStationaryChampionSpritesTop() {
        for (StationarySprite stationaryChampionSprite : stationarySprites) {
            spriteBatch.draw(stationaryChampionSprite.getHeadTexture(),
                             stationaryChampionSprite.getX(), stationaryChampionSprite.getY() + 1,
                             stationaryChampionSprite.getSize(), stationaryChampionSprite.getSize());
        }
    }

    
    public void getInput() {
    	Champion player = (Champion) sprites.get(0);
        player.handleInputOn(this);
        
    }
    
    public Vector3 getWorldPoint(float x, float y) {
        Vector3 worldPoint = new Vector3(x, y, 0);
        camera.unproject(worldPoint);

        float worldX = sprites.get(0).getX() - 15;
        float worldY = sprites.get(0).getY() - 9.375f;

        worldPoint.set(worldPoint.x + worldX, worldPoint.y + worldY, 0);

        return worldPoint;
    }

    public Stage getStage() {
        return stage;
    }

    public Array<BitmapFont> getFonts() {
        return fonts;
    }
}