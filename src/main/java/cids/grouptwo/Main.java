package cids.grouptwo;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    
    public static void main(String[] args) {
        // uncomment this to launch the new libgdx window, it just draws a circle to the center of the screen
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		new Lwjgl3Application(new Chessgame(), config);
        
        System.out.println("hi world hello world hii!!!!!");
    }

    //This is Jack's comment for the jackh branch

}
