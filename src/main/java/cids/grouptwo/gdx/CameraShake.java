package cids.grouptwo.gdx;

import java.util.Random;

import com.badlogic.gdx.math.Vector3;

public class CameraShake {

    private float time;
    private float curTime;
    private float power;
    private float curPower;
    private Random random;
    private Vector3 position;
    private Vector3 rotation;
    
    CameraShake() {
        random = new Random();
        curTime = 0;
        curPower = 0;
        position = new Vector3();
    }

    void shake(float power, float time) {
        this.power = power;
        this.time = time;
        curTime = 0;
    }

    public void tick(float delta) {
        if (curTime <= time) {
            curPower = power * ((time - curTime) / time);

            position.x = (random.nextFloat() - 0.5f) * 2 * curPower;
            position.y = (random.nextFloat() - 0.5f) * 2 * curPower;

            curTime += delta;
        } else {
            time = 0;
        }
    }

    public float getTime() {
        return time;
    }

    public Vector3 getPosition() {
        return position;
    }

}
