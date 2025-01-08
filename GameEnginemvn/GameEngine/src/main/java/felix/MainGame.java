package felix;

import felix.entities.Camera;
import felix.entities.Entity;
import felix.entities.Light;
import felix.entities.Player;
import felix.models.RawModel;
import felix.models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import felix.renderEngine.DisplayManager;
import felix.renderEngine.Loader;
import felix.renderEngine.MasterRender;
import felix.renderEngine.OBJLoader;
import felix.renderEngine.EntityRenderer;
import felix.shaders.StaticShader;
import felix.terains.Terain;
import felix.textures.ModelTexture;
import felix.textures.TerrainTexture;
import felix.textures.TerrainTexturePack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainGame {
    private static final int PORT = 6000;

    public static void main(String[] args) {

        String runtimeNativesPath = new File("natives").getAbsolutePath();
        System.setProperty("org.lwjgl.librarypath", runtimeNativesPath);


        DisplayManager.createDisplay();
        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTextureResource("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTextureResource("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTextureResource("path"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTextureResource("grassFlowers"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, bTexture, gTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTextureResource("blendMap"));


        RawModel model = null;
        try {
            model = model = OBJLoader.loadObjModelResource("fern", loader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TexturedModel fern = new TexturedModel(model, new ModelTexture(loader.loadTextureResource("fern")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setNeedMoreFakeLight(true);
        TexturedModel tree = null;
        try {
            tree = new TexturedModel(OBJLoader.loadObjModelResource("tree", loader), new ModelTexture(loader.loadTextureResource("tree")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        TexturedModel dragonT;
        TexturedModel enemyT;
        RawModel enemy;
        try {
            RawModel dragon = OBJLoader.loadObjModelResource("man", loader);
            dragonT = new TexturedModel(dragon, new ModelTexture(loader.loadTextureResource("mud")));
            enemy = OBJLoader.loadObjModelResource("man", loader);
            enemyT = new TexturedModel(dragon, new ModelTexture(loader.loadTextureResource("path")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Player player = new Player(dragonT, new Vector3f(800, 30, 200), 0, 0, 0, 1);
        Terain terrain1 = new Terain(0, 0, loader, texturePack, blendMap, "heightmap");
        Terain terrain2 = new Terain(1, 0, loader, texturePack, blendMap, "heightmap");
        Vector3f vecpo = new Vector3f(20000, 20000, 2000);
        Vector3f vecco = new Vector3f(1, 1, 1);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        float x, y, z;
        for (int i = 0; i < 1000; i++) {
            x = random.nextFloat(1599);
            z = random.nextFloat(804);
            if (x > 800) {
                y = terrain2.getHeightTerrain(x, z);
            } else {
                y = terrain1.getHeightTerrain(x, z);
            }
            entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat(804), 0, 1));
            if (i == 1) {
                for (int j = 0; j < 150; j++) {
                    x = random.nextFloat(1599);
                    z = random.nextFloat(804);
                    if (x > 800) {
                        y = terrain2.getHeightTerrain(x, z);
                    } else {
                        y = terrain1.getHeightTerrain(x, z);
                    }
                    entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat(804), 0, 1));
                }
            }
        }
        Entity enemyE = new Entity(enemyT,new Vector3f(800,0,200),0, random.nextFloat(804), 0, 1);
        entities.add(enemyE);

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

                        float prevX1 = 0, prevY1 = 0, prevZ1 = 0, prevW1 = 0;
                        float prevX2 = 0, prevY2 = 0, prevZ2 = 0, prevW2 = 0;

                        while (true) {
                            if (input1.available() > 0) {
                                float xs = input1.readFloat();
                                float ys = input1.readFloat();
                                float zs = input1.readFloat();
                                float ws = input1.readFloat();
                                if (xs != prevX1 || ys != prevY1 || zs != prevZ1 || ws != prevW1) {
                                    prevX1 = xs;
                                    prevY1 = ys;
                                    prevZ1 = zs;
                                    prevW1 = ws;
                                    output2.writeFloat(xs);
                                    output2.writeFloat(ys);
                                    output2.writeFloat(zs);
                                    output2.writeFloat(ws);
                                }
                            }

                            if (input2.available() > 0) {
                                float xs = input2.readFloat();
                                float ys = input2.readFloat();
                                float zs = input2.readFloat();
                                float ws = input2.readFloat();
                                if (xs != prevX2 || ys != prevY2 || zs != prevZ2 || ws != prevW2) {
                                    prevX2 = xs;
                                    prevY2 = ys;
                                    prevZ2 = zs;
                                    prevW2 = ws;
                                    output1.writeFloat(xs);
                                    output1.writeFloat(ys);
                                    output1.writeFloat(zs);
                                    output1.writeFloat(ws);
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
//moje set casts
                    new Thread(() -> {
                        try {
                            while (true) {
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if (input.available() > 0) {
                                    float xs = input.readFloat();
                                  //  player.getPosition().x = xs;
                                    float ys = input.readFloat();
                                    float zs = input.readFloat();
                                    float ws = input.readFloat();
                                    enemyE.setPosition(new Vector3f(xs,ys,zs));
                                    enemyE.setRotY(ws);
                                     System.out.println("Received: x=" + xs + ", y=" + ys + ", z=" + zs + ", w=" + ws);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    float prevX = 0, prevY = 0, prevZ = 0, prevW = 0;

                    while (true) {
                        try {
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        //  System.out.print("Enter four integers (x y z w): ");
                        float xs =  player.getPosition().x;
                        float ys =  player.getPosition().y;
                        float zs =  player.getPosition().z;
                        float ws =  player.getRotY() % 360;

                        if (xs != prevX || ys != prevY || zs != prevZ || ws != prevW) {
                            output.writeFloat(xs);
                            output.writeFloat(ys);
                            output.writeFloat(zs);
                            output.writeFloat(ws);

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
            if (player.getPosition().x > 800) {
                player.move(terrain2);
            } else {
                player.move(terrain1);
            }
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