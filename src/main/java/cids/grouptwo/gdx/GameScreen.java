package cids.grouptwo.gdx;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;

import cids.grouptwo.gdx.board.Board;

public class GameScreen extends MenuScreen {

    private CameraShake cShake;
    private Music sound;
    private VfxManager vfxManager;
    private GaussianBlurEffect blur;
    private Vector3 blurAmount;
    private boolean modalOpen;
    private Texture background; 
    private Board boardTable;

    GameScreen(int width, int height, ChessGame game, VfxManager vfxManager) {
        super(width, height, game);

        background = game.getAsset("background");
        background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        cShake = new CameraShake();
        sound = game.getAsset("moveSound");

        this.vfxManager = vfxManager;
        this.vfxManager.removeAllEffects();
        blur = new GaussianBlurEffect();
        blur.setPasses(7);
        blurAmount = Vector3.Zero;
        vfxManager.addEffect(blur);
        boardTable = new Board(game, width/2, height);
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.5f));

        // draws background, note to self do not add actors above here
        Image background = new Image(this.background);
        background.setSize(width*1.25f, height*1.25f);
        background.setColor(Color.GRAY);
        centerActor(background);
        stage.addActor(background);

        Table menu = new Table();
        addButton("quit!!!", menu, 0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OnCompletionListener OCL = new OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        music.dispose();
                        Gdx.app.exit();
                    }
                };
                sound.setOnCompletionListener(OCL);
                sound.play();
            }
        });
        
        menu.setPosition(0, 0);
        menu.setSize(width/4, height/4);
        stage.addActor(menu);
        
        addBoard();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.getCamera().update();

        if (cShake.getTime() > 0){
            cShake.tick(Gdx.graphics.getDeltaTime());
            game.getCamera().translate(cShake.getPosition());
        } else {
            game.getCamera().position.lerp(new Vector3(width / 2, height / 2, 0), 0.05f);
        }

        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
        // anything after this and before endInputCapture() will be in the vfxBuffer
        // and then will have effects applied
        
        stage.act();
        stage.draw();

        // anything beyond here does not have effects applied
        vfxManager.endInputCapture();

        if (modalOpen) 
            blur.setAmount(blurAmount.lerp(new Vector3(5,0,0), 0.015f).x);
        else 
            blur.setAmount(blurAmount.lerp(new Vector3(0.001f,0,0), 0.1f).x);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        vfxManager.applyEffects();
        vfxManager.renderToScreen(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    @Override
    public void resize(int width, int height) {
        vfxManager.resize(width, height);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        vfxManager.dispose();
        blur.dispose();
        super.dispose();
    }

    private void addBoard() {
        Pixmap bg = new Pixmap(1, 1, Pixmap.Format.RGB888);
        bg.setColor(Color.LIGHT_GRAY);
        bg.fill();

        boardTable.center();
        // boardTable.debug();
        boardTable.setSize(width/2, height);
        boardTable.background(new TextureRegionDrawable(new TextureRegion(new Texture(bg))));

        centerActor(boardTable);
        stage.addActor(boardTable);
    }
    
}
