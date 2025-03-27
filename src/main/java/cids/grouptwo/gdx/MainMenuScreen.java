package cids.grouptwo.gdx;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;

public class MainMenuScreen extends MenuScreen {

    private Texture logo;
    private Texture background;
    private Table table;
    private CameraShake cShake;
    private Music sound;
    private VfxManager vfxManager;
    private GaussianBlurEffect blur;
    private boolean settingsOpen;
    private Vector3 blurAmount;

    MainMenuScreen(int width, int height, ChessGame game) {
        super(width, height, game);
        logo = game.getAsset("logo");
        logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        background = game.getAsset("background");
        background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        cShake = new CameraShake();
        sound = game.getAsset("moveSound");

        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
        blur = new GaussianBlurEffect();
        blur.setPasses(7);
        blurAmount = Vector3.Zero;
        vfxManager.addEffect(blur);
    }

    @Override
    public void show() {
        Image background = new Image(this.background);
        background.setSize(width*1.25f, height*1.25f);
        background.setPosition(width / 2 - background.getWidth() / 2, 
            height / 2 - background.getHeight() / 2);
        stage.addActor(background);

        Image logo = new Image(this.logo);
        // logo.setZIndex(0);
        logo.setSize(width/1.5f, height/1.5f);
        // places image a little above the center of the screen
        logo.setPosition(width / 2 - logo.getWidth()/2, height / 2 - logo.getHeight()/3);
        logo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cShake.shake(15, 0.15f);
                sound.play();
            }
        });

        stage.addActor(logo);

        table = new Table();
        table.setSize(width, height/4);
        table.setPosition(0, 0);
        stage.addActor(table);

        addButton("play", table, 0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuScreen.this.swapToGame();
            }
        });

        addButton("settings", table, 0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO unhide settings table and enable blur
                if (settingsOpen)
                    settingsOpen = false;
                else
                    settingsOpen = true;
                sound.play();
            }
        });;

        addButton("quit", table, 0).addListener(new ClickListener(){
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

        if (settingsOpen) 
            blur.setAmount(blurAmount.lerp(new Vector3(5,0,0), 0.015f).x);
        else 
            blur.setAmount(blurAmount.lerp(new Vector3(0.001f,0,0), 0.1f).x);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        vfxManager.applyEffects();
        vfxManager.renderToScreen(viewport.getScreenX(), viewport.getScreenY(),
            viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    @Override
    public void resize(int width, int height) {
        vfxManager.resize(width, height);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        // disposeVfx();
        super.dispose();
    }

    private void swapToGame() {
        System.out.println("swapping to game menu!");
        sound.play();
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new GameScreen(width, height, game, vfxManager));
                MainMenuScreen.this.dispose();
            }
        }));
        stage.getRoot().addAction(sequenceAction);
    }

}
