package cids.grouptwo.gdx.board;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import cids.grouptwo.pieces.*;
import cids.grouptwo.pieces.Piece.Color;

/**
 * class just to store big ugly static method to get sprites from piece references
 */
public class PieceSpriteLookup {
    
    /**
     * the big ugly static method in question, i really wanted to use pattern matching
     * but that would require us to switch to java 21+ which is a hastle for users tbh
     * @param piece
     * @param atlases
     * @return atlasregion corresponding to piece
     */
    public static AtlasRegion pieceToSprite(Piece piece, TextureAtlas textureAtlas) {
        // here comes the big ugly ifelse
        /* TODO use Shogi interface to categorize shogi variants of chess pieces
           for example, inside if (piece instanceof Pawn) we also check if (piece instanceof Shogi)
           and from there we can return specifically a shogi pawn
        */
        String regionName = "";
        if (piece instanceof Pawn) {
            regionName += "pawn";
        } else if (piece instanceof Bishop) {
            regionName += "bishop";
        } else if (piece instanceof King) {
            regionName += "king";
        } else if (piece instanceof Knight) {
            regionName += "knight";
        } else if (piece instanceof Queen) {
            regionName += "queen";
        } else if (piece instanceof Rook) {
            regionName += "rook";
        }

        if (piece.getColor() == Color.WHITE) {
            regionName += "w";
        } else if (piece.getColor() == Color.BLACK) {
            regionName += "b";
        }

        return textureAtlas.findRegion(regionName);
    }

}
