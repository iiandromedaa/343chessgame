package cids.grouptwo.gdx.board;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
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
    private boolean lerpFlag;
    private final Coordinate coordinate;
    private Vector2 imagePos;
    private Vector2 imageTarget;
    
    public Tile(Coordinate coordinate, TextureAtlas textureAtlas) {
        this.coordinate = coordinate;
        tileBg = new Image(textureAtlas.findRegion("white"));
        pieceSprite = new Image();
        imagePos = new Vector2();
    }

    public Coordinate getCoord() {
        return coordinate;
    }
    
    public Image getTileBg() {
        return tileBg;
    }

    public Image getSprite() {
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
                colour = GdxBoard.Colours.WHITE;
                break;
            case "black":
                colour = GdxBoard.Colours.BLACK;
                break;
            case "whiteselect":
                colour = GdxBoard.Colours.WHITESELECT;
                break;
            case "blackselect":
                colour = GdxBoard.Colours.BLACKSELECT;
                break;
            case "whitemove":
                colour = GdxBoard.Colours.WHITEMOVE;
                break;
            case "blackmove":
                colour = GdxBoard.Colours.BLACKMOVE;
                break;
            case "whitehover":
                colour = GdxBoard.Colours.WHITEHOVER;
                break;
            case "blackhover":
                colour = GdxBoard.Colours.BLACKHOVER;
                break;
            case "whitetake":
                colour = GdxBoard.Colours.WHITETAKE;
                break;
            case "blacktake":
                colour = GdxBoard.Colours.BLACKTAKE;
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
        lerpFlag = false;
        pieceSprite = new Image();
    }

    public void lerpTo(Tile to) {
        imageTarget = new Vector2(to.getSprite().getX(), to.getSprite().getY());
        lerpFlag = true;
    }

    public void lerpFrom(Tile from) {
        imagePos = new Vector2(from.getSprite().getX(), from.getSprite().getY());
        imageTarget = new Vector2(getSprite().getX(), getSprite().getY());
        lerpFlag = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tileBg.setSize(getWidth(), getHeight());
        tileBg.setPosition(getX(), getY());
        tileBg.toBack();
        tileBg.draw(batch, parentAlpha);

        if (lerpFlag)
            imagePos.lerp(imageTarget, 0.25f);
        else
            imagePos.lerp(new Vector2(getX(), getY()), 1f);

        pieceSprite.setSize(getWidth(), getHeight());
        pieceSprite.setPosition(imagePos.x, imagePos.y);
        pieceSprite.draw(batch, parentAlpha);
    }

    @Override
    public String toString() {
        return "Tile at " + coordinate.X + " " + coordinate.Y;
    }

}
