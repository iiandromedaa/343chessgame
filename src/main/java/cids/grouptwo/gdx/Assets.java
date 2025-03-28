package cids.grouptwo.gdx;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    
    /**
     * i didnt want to have a million getters and setters, so we're using this map
     * as an asset bundle that gets handed out to classes that want assets
     * <p>if you forget the key names, idk man just read the constructor
     */
    private Map<String, AssetDescriptor<?>> map;

    private AssetManager assetManager = new AssetManager();
    
    Assets() {
        map = new HashMap<>();
        map.put("moveSound", new AssetDescriptor<>("moveShort.mp3", Music.class));
        map.put("skin", new AssetDescriptor<>("uiskin.json", Skin.class));
        map.put("logo", new AssetDescriptor<>("logo.png", Texture.class));
        map.put("background", new AssetDescriptor<>("background.jpg", Texture.class));
        map.put("tilesAtlas", new AssetDescriptor<>("tiles.atlas", TextureAtlas.class));
        loadAll();
        assetManager.finishLoading();
    }

    public Map<String,AssetDescriptor<?>> getMap() {
        return map;
    }
    
    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    private void loadAll() {
        map.values().forEach(v -> assetManager.load(v));
    }

}
