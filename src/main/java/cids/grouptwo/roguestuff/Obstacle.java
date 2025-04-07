package cids.grouptwo.roguestuff;
/* i am just exploring a possible modifier here, this can be changed into an interface if we want */
public class Obstacle {
    private int lifespan;

    public Obstacle(int lifespan) {
        this.lifespan = lifespan;       
    }

    public void decreaseLifespan() {
        lifespan--;
    }

    public boolean isExpired() {
        return lifespan <= 0;
    }
}
