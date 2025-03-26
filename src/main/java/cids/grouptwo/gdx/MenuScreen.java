package cids.grouptwo.gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter{

    Skin skin;
    int width, height;
    Viewport viewport;
    Stage stage;
    ChessGame game;

    MenuScreen(int width, int height, Assets assets, ChessGame game) {
        this.game = game;
        this.width = width;
        this.height = height;
        skin = assets.getSkin();
        viewport = new FitViewport(width, height, game.getCamera());
        stage = new Stage(viewport);
    }
    
    TextButton addButton(String name, Table table) {
        TextButton button = new TextButton(name, skin);
        table.add(button).fill().padRight(10);
        return button;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
