package cids.grouptwo.gdx.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import cids.grouptwo.Coordinate;
import cids.grouptwo.gdx.ChessGame;

public class Board extends Table {

    private ChessGame game;
    private TextureAtlas tilesAtlas;
    private Cell<Tile> board[][];
    private Cell<Tile> selected;

    private final char LETTERS[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    
    @SuppressWarnings("unchecked")
    public Board(ChessGame game, int width, int height) {
        this.game = game;
        tilesAtlas = this.game.getAsset("tilesAtlas");
        board = new Cell[8][8];
        setUpTiles(width, height);
    }

    
    public void swap(Tile tile, int colour) {
        switch (colour) {
            case 0:
                tile.setTileBg(tilesAtlas.findRegion("white"));
                break;
            case 1:
                tile.setTileBg(tilesAtlas.findRegion("black"));
                break;
            case 2:
                tile.setTileBg(tilesAtlas.findRegion("whiteselect"));
                break;
            case 3:
                tile.setTileBg(tilesAtlas.findRegion("blackselect"));
                break;
            case 4:
                tile.setTileBg(tilesAtlas.findRegion("whitemove"));
                break;
            case 5:
                tile.setTileBg(tilesAtlas.findRegion("blackmove"));
                break;
        }
    }

    public void clickHandle(Tile tile) {
        tileSelection(tile);
    }

    /**
     * @param event
     * @param tile
     * @param flag 0: enter, 1: exit
     */
    public void hoverHandle(Tile tile, int flag) {
        // TODO if tile is valid move highlighted, swap to hover bg, swap back to move bg on mouse exit
    }

    public Tile getTileFromCoordinate(Coordinate coordinate) {
        return board[coordinate.Y][coordinate.X].getActor();
    }

    public String coordinateToAlgebraic(Coordinate coordinate) {
        return Character.toString(LETTERS[coordinate.X]) + (coordinate.Y + 1);
    }

    private void tileSelection(Tile tile) {
        // TODO if selected is null, validate that tile has a piece, if not, discard click
        // tile is selected
        if (selected == getCell(tile)) {
            selected = null;
            tileBgDeselect(tile);
            return;
        }

        // tile already selected
        if (selected != null) {
            tileBgDeselect(selected.getActor());
            Gdx.app.log("chessgame", "move from " + coordinateToAlgebraic(selected.getActor()
                .getCoordinate()) + " to " + coordinateToAlgebraic(tile.getCoordinate()));
            selected = null;
            return;
        }

        // no tile selected
        selected = getCell(tile);
        tileBgSelect(tile);
    }

    private void tileBgSelect(Tile tile) {
        if (tile.getColour() == Colours.WHITE)
            swap(tile, Colours.WHITESELECT);
        else
            swap(tile, Colours.BLACKSELECT);
    }

    private void tileBgDeselect(Tile tile) {
        if (tile.getColour() == Colours.WHITESELECT)
            swap(tile, Colours.WHITE);
        else
            swap(tile, Colours.BLACK);
    }
    
    private void tileBgMove(Tile tile) {
        if (tile.getColour() == Colours.WHITE)
            swap(tile, Colours.WHITEMOVE);
        else
            swap(tile, Colours.BLACKMOVE);
    }

    private void tileBgHover(Tile tile) {
        if (tile.getColour() == Colours.WHITEMOVE)
            swap(tile, Colours.WHITEHOVER);
        else
            swap(tile, Colours.BLACKHOVER);
    }
    
    private void setUpTiles(int width, int height) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                Tile actor = new Tile(new Coordinate(x, y), tilesAtlas);
                board[y][x] = add(actor).size(height/11);
                actor.addListener(new TileClickListener(actor, this));
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        board[y][x].getActor().setTileBg(tilesAtlas.findRegion("white"));
                    } else {
                        board[y][x].getActor().setTileBg(tilesAtlas.findRegion("black"));
                    }
                } else {
                    if (x % 2 == 0) {
                        board[y][x].getActor().setTileBg(tilesAtlas.findRegion("black"));
                    } else {
                        board[y][x].getActor().setTileBg(tilesAtlas.findRegion("white"));
                    }
                }
            }
            this.row();
        }
    }

    /**
     * for reference when calling the tile swapping method
     */
    public final class Colours {
        public static final int
            WHITE = 0,
            BLACK = 1,
            WHITESELECT = 2,
            BLACKSELECT = 3,
            WHITEMOVE = 4,
            BLACKMOVE = 5,
            WHITEHOVER = 6,
            BLACKHOVER = 7;
    }  

}
