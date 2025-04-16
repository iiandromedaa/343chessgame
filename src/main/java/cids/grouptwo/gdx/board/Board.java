package cids.grouptwo.gdx.board;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.audio.Music;

import cids.grouptwo.Coordinate;
import cids.grouptwo.gdx.GameScreen;
import cids.grouptwo.gdx.GdxChessGame;
import cids.grouptwo.pieces.Piece;

public class Board extends Table {

    private GdxChessGame game;
    private TextureAtlas tilesAtlas;
    private TextureAtlas piecesAtlas;
    private Cell<Tile> cellBoard[][];
    private Cell<Tile> selected;
    private List<Cell<Tile>> valid;
    private GameScreen gameScreen;

    private cids.grouptwo.Board realBoard;

    private final char LETTERS[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    
    @SuppressWarnings("unchecked")
    public Board(GdxChessGame game, int width, int height, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        tilesAtlas = this.game.getAsset("tilesAtlas");
        piecesAtlas = this.game.getAsset("piecesAtlas");
        cellBoard = new Cell[8][8];
        realBoard = game.getBackend().getBoard();
        valid = new ArrayList<>();
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
            case 6:
                tile.setTileBg(tilesAtlas.findRegion("whitehover"));
                break;
            case 7:
                tile.setTileBg(tilesAtlas.findRegion("blackhover"));
                break;
            case 8:
                tile.setTileBg(tilesAtlas.findRegion("whitetake"));
                break;
            case 9:
                tile.setTileBg(tilesAtlas.findRegion("blacktake"));
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
        // Gdx.app.log("chessgame", tile + " hovered" + " | in valid moves " + 
        //     Boolean.toString(valid.contains(getCell(tile))) + " | hover flag " + flag);
        if (valid.contains(getCell(tile)) && flag == 0)
            tileBgHover(tile);
        else if (valid.contains(getCell(tile)) && flag == 1)
            tileBgDehover(tile);
    }

    public Tile getTileFromCoordinate(Coordinate coordinate) {
        return cellBoard[coordinate.Y][coordinate.X].getActor();
    }

    public String coordinateToAlgebraic(Coordinate coordinate) {
        return Character.toString(LETTERS[coordinate.X]) + (8 - coordinate.Y);
    }

    public void populate() {
        for (int row = 0; row < realBoard.getBoard().length; row++) {
            for (int col = 0; col < realBoard.getBoard()[row].length; col++) {
                if (realBoard.getBoard()[row][col] != null) {
                    this.cellBoard[row][col].getActor().setPiece(
                        realBoard.getBoard()[row][col], 
                        PieceSpriteLookup.pieceToSprite(realBoard.getBoard()[row][col], 
                            piecesAtlas)
                    );
                }
            }
        }
    }

    private void tileSelection(Tile tile) {
        // tile is selected
        if (selected == getCell(tile)) {
            selected.getActor().getPiece().getValidMoves(realBoard.getBoard()).forEach(c -> {
                tileBgDemove(getTileFromCoordinate(c));
            });
            valid.clear();
            Gdx.app.log("chessgame", coordinateToAlgebraic(selected.getActor()
                .getCoordinate()) + " deselect | " + selected.getActor().getPiece()
                .getClass().getCanonicalName() + " " + selected.getActor().getPiece().getColor());
            selected = null;
            tileBgDeselect(tile);
            return;
        }

        // tile already selected
        if (selected != null) {
            if (!valid.contains(getCell(tile)) && tile.getPiece() == null)
                return;
            if (!valid.contains(getCell(tile)) && tile.getPiece().getColor() != 
                (game.getBackend().getTurn() == 0 ? Piece.Color.WHITE : Piece.Color.BLACK))
                return;
            tileBgDeselect(selected.getActor());
            selected.getActor().getPiece().getValidMoves(realBoard.getBoard()).forEach(c -> {
                tileBgDemove(getTileFromCoordinate(c));
            });
            if (!valid.contains(getCell(tile))) {
                selected = null;
                select(tile);
                return;
            }
            move(selected.getActor(), tile);
            valid.clear();
            selected = null;
            return;
        }

        // discard click from empty tile
        if (tile.getPiece() == null)
            return;

        // discard wrong colour click
        if (tile.getPiece().getColor() != (game.getBackend().getTurn() == 0 ? 
            Piece.Color.WHITE : Piece.Color.BLACK))
            return;

        // no tile selected
        select(tile);
    }

    private void select(Tile tile) {
        selected = getCell(tile);
        tileBgSelect(tile);
        valid.clear();
        Gdx.app.log("chessgame", coordinateToAlgebraic(selected.getActor()
                .getCoordinate()) + " select | " + selected.getActor().getPiece()
                .getClass().getCanonicalName() + " " + selected.getActor().getPiece().getColor());
        selected.getActor().getPiece().getValidMoves(realBoard.getBoard()).forEach(c -> {
            valid.add(getCell(getTileFromCoordinate(c)));
        });
        valid.forEach(t -> {
            tileBgMove(t.getActor());
        });
    }

    private void move(Tile from, Tile to) {
        Gdx.app.log("chessgame", "move from " + coordinateToAlgebraic(from.getCoordinate()) + 
            " to " + coordinateToAlgebraic(to.getCoordinate()) + " | " + from.getPiece()
            .getClass().getCanonicalName());
        // calls to backend methods
        cids.grouptwo.Board board = game.getBackend().getBoard();
        board.clearPosition(from.getCoordinate());
        Piece piece = from.getPiece();
        piece.piecePosition(to.getCoordinate());
        board.setPiece(piece);
        // now we handle the graphical move
        // this mess is to lerp the pieces while respecting the z order of the tiles
        if (((from.getCoordinate().Y > to.getCoordinate().Y) || 
            (from.getCoordinate().X > to.getCoordinate().X))) {
            from.lerpTo(to);
            Timer.schedule(new Task(){
                @Override
                public void run() {
                    to.setPiece(piece, PieceSpriteLookup.pieceToSprite(piece, piecesAtlas));
                    from.clearPiece();
                }
            }, 0.2f);
        } else {
            to.setPiece(piece, PieceSpriteLookup.pieceToSprite(piece, piecesAtlas));
            to.lerpFrom(from);
            from.clearPiece();
        }
        gameScreen.getCameraShake().shake(5, 0.15f);
        
        ((Music) game.getAsset("moveSound")).play();
        // lastly we end our turn
        game.getBackend().step();
    }

    private void tileBgSelect(Tile tile) {
        if (tile.getColour() == Colours.WHITE)
            swap(tile, Colours.WHITESELECT);
        else if (tile.getColour() == Colours.BLACK)
            swap(tile, Colours.BLACKSELECT);
    }

    private void tileBgDeselect(Tile tile) {
        if (tile.getColour() == Colours.WHITESELECT)
            swap(tile, Colours.WHITE);
        else if (tile.getColour() == Colours.BLACKSELECT)
            swap(tile, Colours.BLACK);
    }
    
    private void tileBgMove(Tile tile) {
        if (tile.getColour() == Colours.WHITE)
            swap(tile, tile.getPiece() == null ? Colours.WHITEMOVE : Colours.WHITETAKE);
        else if (tile.getColour() == Colours.BLACK)
            swap(tile, tile.getPiece() == null ? Colours.BLACKMOVE : Colours.BLACKTAKE);
    }

    private void tileBgDemove(Tile tile) {
        if (tile.getColour() == Colours.WHITEMOVE || tile.getColour() == Colours.WHITEHOVER
            || tile.getColour() == Colours.WHITETAKE)
            swap(tile, Colours.WHITE);
        else if (tile.getColour() == Colours.BLACKMOVE || tile.getColour() == Colours.BLACKHOVER
            || tile.getColour() == Colours.BLACKTAKE)
            swap(tile, Colours.BLACK);
    }

    private void tileBgHover(Tile tile) {
        if (tile.getColour() == Colours.WHITEMOVE || tile.getColour() == Colours.WHITETAKE)
            swap(tile, Colours.WHITEHOVER);
        else if (tile.getColour() == Colours.BLACKMOVE || tile.getColour() == Colours.BLACKTAKE)
            swap(tile, Colours.BLACKHOVER);
    }

    private void tileBgDehover(Tile tile) {
        if (tile.getColour() == Colours.WHITEHOVER)
            swap(tile, tile.getPiece() == null ? Colours.WHITEMOVE : Colours.WHITETAKE);
        else if (tile.getColour() == Colours.BLACKHOVER)
            swap(tile, tile.getPiece() == null ? Colours.BLACKMOVE : Colours.BLACKTAKE);
    }
    
    private void setUpTiles(int width, int height) {
        for (int y = 0; y < cellBoard.length; y++) {
            for (int x = 0; x < cellBoard[0].length; x++) {
                Tile actor = new Tile(new Coordinate(x, y), tilesAtlas);
                cellBoard[y][x] = add(actor).size(height/11);
                actor.addListener(new TileClickListener(actor, this));
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        cellBoard[y][x].getActor().setTileBg(tilesAtlas.findRegion("white"));
                    } else {
                        cellBoard[y][x].getActor().setTileBg(tilesAtlas.findRegion("black"));
                    }
                } else {
                    if (x % 2 == 0) {
                        cellBoard[y][x].getActor().setTileBg(tilesAtlas.findRegion("black"));
                    } else {
                        cellBoard[y][x].getActor().setTileBg(tilesAtlas.findRegion("white"));
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
            BLACKHOVER = 7,
            WHITETAKE = 8,
            BLACKTAKE = 9;
    }  

}
