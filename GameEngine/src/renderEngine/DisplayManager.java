package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
    static final int WIDTH = 1280;
    static final int HEIGHT = 720;
    private static final int FPS_C = 120;
    public static void crateDisplay (){
        ContextAttribs attribs = new ContextAttribs(3,2);
        attribs.withForwardCompatible(true);
        attribs.withProfileCore(true);
        try{
        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
        Display.create(new PixelFormat(),attribs);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        GL11.glViewport(0,0,WIDTH,HEIGHT);
    }
    public static void  updateDisplay(){
        Display.sync(FPS_C);
        Display.update();
    }
    public static void closeDisplay(){
        Display.destroy();
    }
}
