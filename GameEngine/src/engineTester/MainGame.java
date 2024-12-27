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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainGame {


    public static void main(String[] args) {


        DisplayManager.createDisplay();
        Loader loader = new Loader();


        RawModel model = null;
        try {
            model = model = OBJLoader.loadObjModel("fern",loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TexturedModel fern = new TexturedModel(model, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setNeedMoreFakeLight(true);
        TexturedModel tree = null;
        try {
            tree = new TexturedModel(OBJLoader.loadObjModel("tree",loader),new ModelTexture(loader.loadTexture("tree")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Vector3f vecpo = new Vector3f(20000,20000,2000);
        Vector3f vecco = new Vector3f(1,1,1);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(fern, new Vector3f(random.nextFloat(1599) ,0,random.nextFloat(804) ),0,0,0,3));
            if(i==1){
                for(int j=0;j<70;j++){
                    entities.add(new Entity(tree, new Vector3f(random.nextFloat(1599) ,0,random.nextFloat(804) ),0,0,0,3));
                }
            }
        }


        Light light = new Light(vecco, vecpo);

        Terain terrain1= new Terain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
        Terain terrain2= new Terain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        MasterRender Mrenderer = new MasterRender();
        while (!Display.isCloseRequested()) {

            camera.move();

            Mrenderer.processTerrain(terrain1);
            Mrenderer.processTerrain(terrain2);
            for(Entity entity:entities){
                Mrenderer.processEntity(entity);
            }

            Mrenderer.render(light, camera);
            DisplayManager.updateDisplay();

        }


        Mrenderer.CleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
