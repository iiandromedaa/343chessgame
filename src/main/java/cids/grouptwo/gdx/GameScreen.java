package cids.grouptwo.gdx;

import java.util.Random;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;

import cids.grouptwo.ChessGame;
import cids.grouptwo.KillListener;
import cids.grouptwo.gdx.board.GdxBoard;
import cids.grouptwo.pieces.*;

public class GameScreen extends MenuScreen implements KillListener {

    private CameraShake cShake;
    private Music sound;
    private VfxManager vfxManager;
    private GaussianBlurEffect blur;
    private Vector2 blurAmount;
    private boolean modalOpen;
    private GdxBoard boardTable;
    private String currentFen;

    GameScreen(int width, int height, GdxChessGame game, VfxManager vfxManager, ChessGame backend) {
        super(width, height, game, backend);

        cShake = new CameraShake();
        sound = game.getAsset("moveSound");

        this.vfxManager = vfxManager;
        this.vfxManager.removeAllEffects();
        blur = new GaussianBlurEffect();
        blur.setPasses(7);
        blurAmount = Vector2.Zero;
        vfxManager.addEffect(blur);

        currentFen = game.getRandomFen();
        backend.reset();
        backend.newBoard(currentFen);
        boardTable = new GdxBoard(game, width/2, height, this);
        backend.addListener(this);
        backend.getBoard().addListener(boardTable);
        Gdx.app.log("chessgame", backend.getPieceSet().toString());
        boardTable.populate();

    }

    public CameraShake getCameraShake() {
        return cShake;
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
        menu.setPosition(width/16, 0);
        menu.setSize(width/20, height/4);

        // ignore this awful slop code, its just to test piece replacing and some new piece movements
        addButton("debug piece swap", menu, 1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Piece p : game.getBackend().getPieceSet().values()) {
                    if (p instanceof Pawn && !(p instanceof Shogi)) {
                        ShogiPawn sp = new ShogiPawn(p.getColor(), p.getX(), p.getY());
                        backend.swapPiece(p, sp);
                        Gdx.app.log("chessgame", game.getBackend().getPieceSet().toString());
                        break;
                    } else if (p instanceof Rook && !(p instanceof Shogi)) {
                        Lance sp = new Lance(p.getColor(), p.getX(), p.getY());
                        backend.swapPiece(p, sp);
                        Gdx.app.log("chessgame", game.getBackend().getPieceSet().toString());
                        break;
                    } else if (p instanceof Bishop && !(p instanceof Alfil)) {
                        Alfil sp = new Alfil(p.getColor(), p.getX(), p.getY());
                        backend.swapPiece(p, sp);
                        Gdx.app.log("chessgame", game.getBackend().getPieceSet().toString());
                        break;
                    }
                }
            }
        });

        addButton("new board", menu, 1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.getBackend().getTurn() != 0)
                    game.getBackend().setTurn(0);
                newRound();
            }
        });

        addButton("quit!!!", menu, 1).addListener(new ClickListener() {
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
        
        stage.addActor(menu);
        
        addBoard();

        Label fenLabel = new Label(currentFen.split("@")[1], skin);
        fenLabel.setSize(width, height/7);
        fenLabel.setPosition(width/50, height-(height/7));
        fenLabel.setFontScale(2f);
        stage.addActor(fenLabel);

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
            blur.setAmount(blurAmount.lerp(new Vector2(5,0), 0.015f).x);
        else 
            blur.setAmount(blurAmount.lerp(new Vector2(0.001f,0), 0.1f).x);

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
        // vfxManager.dispose();
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

    private void toShop() {
        Gdx.app.log("chessgame", "swapping to shop, now round " + 
            game.getBackend().getRound());
        ((Music) game.getAsset("notifySound")).play();
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new ShopScreen(width, height, game, vfxManager, backend));
                game.getBackend().killKillListener(GameScreen.this);
                GameScreen.this.dispose();
            }
        }));
        stage.getRoot().addAction(sequenceAction);
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
                game.getBackend().killKillListener(GameScreen.this);
                GameScreen.this.dispose();
            }
        }));
        stage.getRoot().addAction(sequenceAction);
    }

    @Override
    public void killNotify(int turn) {
        Gdx.app.log("chessgame", turn == 0 ? "white wins!" : "black wins!");
        toShop();
    }
    
}
