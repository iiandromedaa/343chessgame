package cids.grouptwo.gdx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.sun.tools.javac.Main;

public class Assets {
    
    private Skin Skin;
    private Music moveSound;
    private AssetDescriptor<Texture> logo;

    private AssetManager assetManager = new AssetManager();
    
    Assets() {
		try {
            loadFromClasspath("uiskin.png");
            Skin = new Skin(
                new FileHandle(loadFromClasspath("uiskin.json")),
                new TextureAtlas(new FileHandle(loadFromClasspath("uiskin.atlas")))
            );
            moveSound = Gdx.audio.newMusic(new FileHandle(loadFromClasspath("moveShort.mp3")));
			logo = new AssetDescriptor<>(
			    new FileHandle(loadFromClasspath("logo.png")), Texture.class
			);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
    }
    
    public void loadAll() {
        assetManager.load(logo);
    }
    
    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    public Skin getSkin() {
        return Skin;
    }

    public AssetDescriptor<Texture> getLogo() {
        return logo;
    }

    public Music getMoveSound() {
        return moveSound;
    }

    private File loadFromClasspath(String filename) throws IOException, 
        URISyntaxException, FileNotFoundException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            throw new FileNotFoundException(filename);
        }

        File tmp = new File(System.getProperty("java.io.tmpdir"));
        File tempFile = new File(tmp, filename);
        tempFile.deleteOnExit();

        OutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        } 
        
        return tempFile;
    }

}
