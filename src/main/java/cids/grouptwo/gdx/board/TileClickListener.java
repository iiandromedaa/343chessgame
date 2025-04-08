package cids.grouptwo.gdx.board;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TileClickListener extends ClickListener {
    
    private Tile tile;
    private Board board;

    public TileClickListener(Tile tile, Board board) {
        this.tile = tile;
        this.board = board;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        board.clickHandle(tile);
        // super.clicked(event, x, y);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        board.hoverHandle(tile, 0);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        board.hoverHandle(tile, 1);
    }

    

}
