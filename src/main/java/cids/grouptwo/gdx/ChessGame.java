package cids.grouptwo.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * libgdx game loop, originally implemented ApplicationListener, 
 * now extends Game (which itself implements Application Listener),
 * switched because Game has the setScreen() method :]
 */
public class ChessGame extends Game {

    private Camera camera;
    private int width, height;

    public ChessGame(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        camera = new OrthographicCamera(1,1);
        setScreen(new MainMenuScreen(width, height, assets.getAssetManager(), this));
    }

    // @Override
    // public void render() {

    // }

    public void dispose() {
        
    }

    public Camera getCamera() {
        return camera;
    }

}
