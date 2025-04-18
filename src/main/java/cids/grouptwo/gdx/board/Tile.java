package cids.grouptwo.gdx.board;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cids.grouptwo.Coordinate;
// import cids.grouptwo.Piece;
import cids.grouptwo.pieces.Piece;

public class Tile extends Widget {
    
    private Image tileBg;
    // TODO add pieces to sprite sheet, create assets for shogi, courier, and chaturanga
    private Image pieceSprite;
    private int colour;
    private Piece piece;
    private final Coordinate coordinate;
    
    public Tile(Coordinate coordinate, TextureAtlas textureAtlas) {
        this.coordinate = coordinate;
        tileBg = new Image(textureAtlas.findRegion("white"));
        pieceSprite = new Image();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    
    public Image getTileBg() {
        return tileBg;
    }

    public Image getPieceSprite() {
        return pieceSprite;
    }

    public int getColour() {
        return colour;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setTileBg(AtlasRegion atlasRegion) {
        switch (atlasRegion.name) {
            case "white":
                colour = Board.Colours.WHITE;
                break;
            case "black":
                colour = Board.Colours.BLACK;
                break;
            case "whiteselect":
                colour = Board.Colours.WHITESELECT;
                break;
            case "blackselect":
                colour = Board.Colours.BLACKSELECT;
                break;
            case "whitemove":
                colour = Board.Colours.WHITEMOVE;
                break;
            case "blackmove":
                colour = Board.Colours.BLACKMOVE;
                break;
            case "whitehover":
                colour = Board.Colours.WHITEHOVER;
                break;
            case "blackhover":
                colour = Board.Colours.BLACKHOVER;
                break;
            case "whitetake":
                colour = Board.Colours.WHITETAKE;
                break;
            case "blacktake":
                colour = Board.Colours.BLACKTAKE;
                break;
        }
        tileBg.setDrawable(new TextureRegionDrawable(atlasRegion));
    }

    public void setPiece(Piece piece, AtlasRegion atlasRegion) {
        this.piece = piece;
        setPieceSprite(atlasRegion);
    }

    public void setPieceSprite(AtlasRegion atlasRegion) {
        pieceSprite.setDrawable(new TextureRegionDrawable(atlasRegion));
    }

    public void clearPiece() {
        piece = null;
        pieceSprite = new Image();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tileBg.setSize(getWidth(), getHeight());
        tileBg.setPosition(getX(), getY());
        tileBg.draw(batch, parentAlpha);

        pieceSprite.setSize(getWidth(), getHeight());
        pieceSprite.setPosition(getX(), getY());
        pieceSprite.draw(batch, parentAlpha);
    }

    @Override
    public String toString() {
        return "Tile at " + coordinate.X + " " + coordinate.Y;
    }

}
