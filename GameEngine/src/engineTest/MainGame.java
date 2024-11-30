package engineTest;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGame {
    public static void main(String[] args){

        DisplayManager.crateDisplay();
        while(!Display.isCloseRequested()){
            //game
            //render
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
}
