package engineTester;


import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.FileNotFoundException;


public class MainGame {


    public static void main(String[] args) {


        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        RawModel model = null;
        try {
            model = OBJLoader.loadObjModel("dragon",loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("formulaT")));
        Vector3f vecpo = new Vector3f(0,0,-20);
        Vector3f vecco = new Vector3f(0,0,0);

        Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0, 0, 0, 1);
        Light light = new Light(vecpo,vecco);
        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }


        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
