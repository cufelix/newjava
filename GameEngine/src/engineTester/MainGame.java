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
import renderEngine.MasterRender;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.FileNotFoundException;


public class MainGame {


    public static void main(String[] args) {


        DisplayManager.createDisplay();
        Loader loader = new Loader();


        RawModel model = null;
        try {
            model = OBJLoader.loadObjModel("map",loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("file")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(0.4f);
        Vector3f vecpo = new Vector3f(0,20,20);
        Vector3f vecco = new Vector3f(1,1,1);

        Entity entity = new Entity(staticModel, new Vector3f(0, 0.5f, -2), 40, 0, 0, 1);
        Light light = new Light(vecco, vecpo);
        Camera camera = new Camera();
        MasterRender Mrenderer = new MasterRender();
        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, -0.25f, 0);
            camera.move();
            Mrenderer.processEntity(entity);
            Mrenderer.render(light,camera);
            DisplayManager.updateDisplay();
        }


        Mrenderer.CleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
