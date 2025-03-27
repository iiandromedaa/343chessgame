package cids.grouptwo.gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetDescriptor;
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
    private Assets assets;

    public ChessGame(int height) {
        /**
         * making the viewport a different size than the actual window, to allow for people
         * with fucked up evil ultrawide monitors to still play without stretching the game
         */
        width = (int) Math.ceil(height * 1.7777777f);
        this.height = height;
    }

    @Override
    public void create() {
        assets = new Assets();
        camera = new OrthographicCamera(width, height);
        setScreen(new MainMenuScreen(width, height, this));
    }

    // @Override
    // public void render() {

    // }

    public void dispose() {
        
    }

    public Camera getCamera() {
        return camera;
    }

    /**
     * ignore the cast lol its fine trust me
     * @param key look at the Assets constructor for reference on keys
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getAsset(String key) {
        AssetDescriptor<?> descriptor = assets.getMap().get(key);
        return (T) assets.getAssetManager().get(descriptor);
    }

}
