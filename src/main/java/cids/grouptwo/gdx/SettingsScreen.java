package cids.grouptwo.gdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SettingsScreen extends MenuScreen {

    private Table table;
    private MainMenuScreen mms;
    
    SettingsScreen(int width, int height, Assets assets, ChessGame game) {
        super(width, height, assets, game);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        table = new Table();
        table.setSize(width, height/4);
        table.setPosition(0, 0);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);
    }
    
}
