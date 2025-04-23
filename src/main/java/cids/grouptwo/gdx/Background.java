package cids.grouptwo.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * actor which uses affine transforms to draw itself skewed
 */
public class Background extends Actor {

    private Texture texture;
    private TextureRegion textureRegion;
    private Affine2 affine;
    private Vector2 scale;
    private Vector2 shear;

    Background(Texture texture) {
        this.texture = texture;
        this.texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        textureRegion = new TextureRegion(texture, texture.getWidth()*10, texture.getHeight()*10);
        affine = new Affine2();
        scale = new Vector2(1f, 1f);
        shear = new Vector2(0f, 0f);
    }

    public void setScale(float x, float y) {
        scale.set(x, y);
    }

    public void setShear(float x, float y) {
        shear.set(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        affine.setToTranslation(getX(), getY());
        affine.scale(scale);
        affine.shear(shear);
        batch.draw(textureRegion, getWidth(), getWidth(), affine);
    }

}
