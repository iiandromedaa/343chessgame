package cids.grouptwo.gdx;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.crashinvaders.vfx.VfxManager;

import cids.grouptwo.ChessGame;

public class ShopScreen extends MenuScreen {

    private VfxManager vfxManager;
    private Music sound;

    ShopScreen(int width, int height, GdxChessGame game, VfxManager vfxManager, ChessGame backend) {
        super(width, height, game, backend);
        sound = game.getAsset("moveSound");
        this.vfxManager = vfxManager;
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(Actions.fadeIn(0.5f));

        // draws background, note to self do not add actors above here
        Background background = new Background(game.getAsset("background"));
        background.setSize(width*5, height*5);
        background.setColor(Color.GRAY);
        centerActor(background);
        Random rand = new Random();
        background.setShear(0.1f + rand.nextFloat() * (0.6f), 0.1f + rand.nextFloat() * (0.2f));
        stage.addActor(background);

        Table menu = new Table();
        menu.setSize(width/4, height/4);
        centerActor(menu);

        addButton("pretend this is a modifier option", menu, 1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OnCompletionListener OCL = new OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        newRound();
                    }
                };
                sound.setOnCompletionListener(OCL);
                sound.play();
            }
        });
        stage.addActor(menu);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render(float delta) {
        game.getCamera().update();

        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
        // anything after this and before endInputCapture() will be in the vfxBuffer
        // and then will have effects applied
        
        stage.act();
        stage.draw();

        // anything beyond here does not have effects applied
        vfxManager.endInputCapture();

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        vfxManager.applyEffects();
        vfxManager.renderToScreen(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    private void newRound() {
        Gdx.app.log("chessgame", "swapping out gamescreen, now round " + 
            game.getBackend().getRound());
        ((Music) game.getAsset("notifySound")).play();
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new GameScreen(width, height, game, vfxManager, backend));
                ShopScreen.this.dispose();
            }
        }));
        stage.getRoot().addAction(sequenceAction);
    }
    
}
