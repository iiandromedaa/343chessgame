package cids.grouptwo.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    
    public static AssetDescriptor<Skin> SKIN;
    public static AssetDescriptor<Texture> logo;

    private AssetManager assetManager = new AssetManager();
    
    Assets() {
        SKIN = new AssetDescriptor<>(
            Gdx.files.classpath("src/main/resources/uiskin.json"), 
            Skin.class, new SkinLoader.SkinParameter("src/main/resources/uiskin.atlas")
        );

        logo = new AssetDescriptor<>(
            Gdx.files.classpath("src/main/resources/logo.png"), Texture.class
        );
    }
    
    public void loadAll() {
        assetManager.load(SKIN);
        assetManager.load(logo);
    }
    
    public AssetManager getAssetManager() {
        return this.assetManager;
    }

}
