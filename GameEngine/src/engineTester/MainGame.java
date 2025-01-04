package engineTester;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class MainGame {
    private static final int PORT = 6000;

    public static void main(String[] args) {



        DisplayManager.createDisplay();
        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, bTexture, gTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        RawModel model = null;
        try {
            model = model = OBJLoader.loadObjModel("fern", loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TexturedModel fern = new TexturedModel(model, new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setNeedMoreFakeLight(true);
        TexturedModel tree = null;
        try {
            tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TexturedModel dragonT;
        try {
            RawModel dragon = OBJLoader.loadObjModel("man", loader);
            dragonT = new TexturedModel(dragon, new ModelTexture(loader.loadTexture("mud")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Player player = new Player(dragonT, new Vector3f(800, 30, 200), 0, 0, 0, 1);
        Terain terrain1 = new Terain(0, 0, loader, texturePack, blendMap,"heightmap");
        Terain terrain2 = new Terain(1, 0, loader, texturePack, blendMap,"heightmap");
        Vector3f vecpo = new Vector3f(20000, 20000, 2000);
        Vector3f vecco = new Vector3f(1, 1, 1);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        float x,y,z;
        for (int i = 0; i < 1000; i++) {
            x= random.nextFloat(1599);
            z=random.nextFloat(804);
            if(x>800){
                y = terrain2.getHeightTerrain(x,z);
            }else{
                y = terrain1.getHeightTerrain(x,z);}
            entities.add(new Entity(fern, new Vector3f(x,y,z), 0, random.nextFloat(804), 0, 1));
            if (i == 1) {
                for (int j = 0; j < 150; j++) {
                    x= random.nextFloat(1599);
                    z=random.nextFloat(804);
                    if(x>800){
                        y = terrain2.getHeightTerrain(x,z);
                    }else{
                        y = terrain1.getHeightTerrain(x,z);}
                    entities.add(new Entity(tree,  new Vector3f(x,y,z), 0, random.nextFloat(804), 0, 1));
                }
            }
        }


        Light light = new Light(vecco, vecpo);

        // ServerAndClient server = new ServerAndClient(player);


        MasterRender Mrenderer = new MasterRender();




        Camera camera = new Camera(player);
        Thread multiplayer = new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Do you want to start the server? (Y/N): ");
            String response = scanner.nextLine().trim().toUpperCase();

            boolean isServer = response.equals("Y");

            if (isServer) {
                Thread serverThread = new Thread(() -> {
                    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                        System.out.println("Server started and listening on port " + PORT);

                        Socket client1 = serverSocket.accept();
                        System.out.println("Client 1 connected");

                        Socket client2 = serverSocket.accept();
                        System.out.println("Client 2 connected");

                        DataInputStream input1 = new DataInputStream(client1.getInputStream());
                        DataOutputStream output1 = new DataOutputStream(client1.getOutputStream());

                        DataInputStream input2 = new DataInputStream(client2.getInputStream());
                        DataOutputStream output2 = new DataOutputStream(client2.getOutputStream());

                        int prevX1 = 0, prevY1 = 0, prevZ1 = 0, prevW1 = 0;
                        int prevX2 = 0, prevY2 = 0, prevZ2 = 0, prevW2 = 0;

                        while (true) {
                            if (input1.available() > 0) {
                                int xs = input1.readInt();
                                int ys = input1.readInt();
                                int zs = input1.readInt();
                                int ws = input1.readInt();
                                if (xs != prevX1 || ys != prevY1 || zs != prevZ1 || ws != prevW1) {
                                    prevX1 = xs;
                                    prevY1 = ys;
                                    prevZ1 = zs;
                                    prevW1 = ws;
                                    output2.writeInt(xs);
                                    output2.writeInt(ys);
                                    output2.writeInt(zs);
                                    output2.writeInt(ws);
                                }
                            }

                            if (input2.available() > 0) {
                                int xs = input2.readInt();
                                int ys = input2.readInt();
                                int zs = input2.readInt();
                                int ws = input2.readInt();
                                if (xs != prevX2 || ys != prevY2 || zs != prevZ2 || ws != prevW2) {
                                    prevX2 = xs;
                                    prevY2 = ys;
                                    prevZ2 = zs;
                                    prevW2 = ws;
                                    output1.writeInt(xs);
                                    output1.writeInt(ys);
                                    output1.writeInt(zs);
                                    output1.writeInt(ws);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                serverThread.start();
            }

            Thread clientThread = new Thread(() -> {
                String ip;
                if (isServer) {
                    ip = "localhost"; // Pokud je uživatel server, klient se připojí na localhost
                    System.out.println("Client will connect to localhost.");
                } else {
                    System.out.print("Enter server IP: ");
                    ip = scanner.nextLine();
                }

                try (Socket socket = new Socket(ip, PORT);
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    new Thread(() -> {
                        try {
                            while (true) {
                                if (input.available() > 0) {
                                    int xs = input.readInt();
                                    player.getPosition().x = xs;
                                    int ys = input.readInt();
                                    int zs = input.readInt();
                                    int ws = input.readInt();
                                    System.out.println("Received: x=" + xs + ", y=" + ys + ", z=" + zs + ", w=" + ws);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    int prevX = 0, prevY = 0, prevZ = 0, prevW = 0;

                    while (true) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        //  System.out.print("Enter four integers (x y z w): ");
                        int xs =(int) player.getPosition().x;
                        int ys = (int) player.getPosition().y;
                        int zs = (int) player.getPosition().z;
                        int ws = (int) player.getRotY()%360;

                        if (xs != prevX || ys != prevY || zs != prevZ || ws != prevW) {
                            output.writeInt(xs);
                            output.writeInt(ys);
                            output.writeInt(zs);
                            output.writeInt(ws);

                            prevX = xs;
                            prevY = ys;
                            prevZ = zs;
                            prevW = ws;
                        } else {
                            // System.out.println("Values unchanged. Not sending to server.");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            clientThread.start();
        });
        multiplayer.start();
        while (!Display.isCloseRequested()) {

            camera.move();
            if(player.getPosition().x>800){
                player.move(terrain2);
            }else{
                player.move(terrain1);}
            //player.move(terrain1);
            //     System.out.println(player.getPosition().x);
            Mrenderer.processEntity(player);
            Mrenderer.processTerrain(terrain1);
            Mrenderer.processTerrain(terrain2);
            for (Entity entity : entities) {
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