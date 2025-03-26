package cids.grouptwo.gdx;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends MenuScreen {

    private Texture logo;
    private Table table;
    private CameraShake cShake;
    private Music sound;

    MainMenuScreen(int width, int height, Assets assets, ChessGame game) {
        super(width, height, assets, game);
        logo = assets.getAssetManager().get(assets.getLogo());
        logo.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        cShake = new CameraShake();
        sound = assets.getMoveSound();
    }

    @Override
    public void show() {
        Image logo = new Image(this.logo);
        logo.setZIndex(0);
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

        addButton("play", table).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO swap to game screen and dispose menu
                sound.play();
                System.out.println("now hold on, the game doesnt exist yet!");
            }
        });

        addButton("settings", table).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO swap to settings screen and hide menu
                sound.play();
            }
        });;

        addButton("quit", table).addListener(new ClickListener(){
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
        // its not really necessary to set a background for when the buffer
        // is cleared, it probably will never be seen, but the examples
        // i was learning from did it, so it can't hurt
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if (cShake.getTime() > 0){
            cShake.tick(Gdx.graphics.getDeltaTime());
            game.getCamera().translate(cShake.getPosition());
        } else {
            game.getCamera().position.lerp(new Vector3(width / 2, height / 2, 0), 0.05f);
        }
    }

}
