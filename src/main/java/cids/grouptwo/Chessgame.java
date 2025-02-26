package cids.grouptwo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Chessgame extends ApplicationAdapter {

    ShapeRenderer shape;

    @Override
    public void create() {
        shape = new ShapeRenderer();
    }

    @Override
    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 50);
        shape.end();
    }

}
