package cids.grouptwo.gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cids.grouptwo.ChessGame;

public class MenuScreen extends ScreenAdapter{

    Skin skin;
    int width, height;
    Viewport viewport;
    Stage stage;
    GdxChessGame game;
    ChessGame backend;

    MenuScreen(int width, int height, GdxChessGame game, ChessGame backend) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.backend = backend;
        skin = game.getAsset("skin");
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
        // dont worry about these random float multipliers
        if (flag == 0)
            cell.fill().padRight(width * 0.0078f);
        else {
            cell.fill(0.5f, 1f).padTop(height * 0.0014f).align(Align.top);
            table.row();
        }
        return button;
    }

    public void centerActor(Actor actor) {
        actor.setPosition(width / 2 - actor.getWidth() / 2, 
        height / 2 - actor.getHeight() / 2);
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
