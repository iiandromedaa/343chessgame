package cids.grouptwo.gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
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
    
    /**
     * does what it says on the box, adds button to a table, 
     * tabled buttons will always go left -> right if horizontal
     * and top -> bottom if vertical
     * @param name button text
     * @param table table to add button to
     * @param flag 0: horizontal, >0: vertical
     * @return
     */
    TextButton addButton(String name, Table table, int flag) {
        TextButton button = new TextButton(name, skin);
        Cell<TextButton> cell = table.add(button);
        if (flag == 0)
            cell.fill().padRight(10);
        else
            cell.fill().padTop(10).align(Align.top);

        return button;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
