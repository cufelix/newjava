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
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terains.Terain;
import textures.ModelTexture;

import java.io.FileNotFoundException;


public class MainGame {


    public static void main(String[] args) {


        DisplayManager.createDisplay();
        Loader loader = new Loader();


        RawModel model = null;
        try {
            model = OBJLoader.loadObjModel("stall",loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(0.4f);
        Vector3f vecpo = new Vector3f(6000,4000,4000);
        Vector3f vecco = new Vector3f(1,1,1);

        Entity entity = new Entity(staticModel, new Vector3f(50, 0, 15), 0, 0, 0, 1);
        Light light = new Light(vecco, vecpo);

        Terain terrain1= new Terain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
        Terain terrain2= new Terain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MasterRender Mrenderer = new MasterRender();
        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            Mrenderer.processTerrain(terrain1);
            Mrenderer.processTerrain(terrain2);
            Mrenderer.processEntity(entity);
            Mrenderer.render(light,camera);
            DisplayManager.updateDisplay();
        }


        Mrenderer.CleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
