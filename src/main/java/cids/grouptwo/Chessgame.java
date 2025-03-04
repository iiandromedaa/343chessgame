package cids.grouptwo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Chessgame implements ApplicationListener {

    ShapeRenderer shape;

    public void create() {
        shape = new ShapeRenderer();
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 50);
        shape.end();
    }

    public void dispose() {
        // TODO Auto-generated method stub
    }

    public void pause() {
        // TODO Auto-generated method stub
    }

    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    public void resume() {
        // TODO Auto-generated method stub
    }

}
