package cids.grouptwo.gdx.board;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cids.grouptwo.Coordinate;
import cids.grouptwo.Piece;

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
        }
        tileBg.setDrawable(new TextureRegionDrawable(atlasRegion));
    }

    public void setPiece(AtlasRegion atlasRegion) {
        pieceSprite.setDrawable(new TextureRegionDrawable(atlasRegion));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tileBg.setSize(getWidth(), getHeight());
        tileBg.setPosition(getX(), getY());
        tileBg.draw(batch, parentAlpha);

        // TODO lerp piece when move is performed before handing over ownership to next tile
        pieceSprite.setSize(getWidth(), getHeight());
        pieceSprite.setPosition(getX(), getY());
        pieceSprite.draw(batch, parentAlpha);
        
    }

}
