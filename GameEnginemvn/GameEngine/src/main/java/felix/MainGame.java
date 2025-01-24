package felix;

import felix.entities.Camera;
import felix.entities.Entity;
import felix.entities.Light;
import felix.entities.Player;
import felix.graphicsui.UIRenderer;
import felix.graphicsui.UItexture;
import felix.models.RawModel;
import felix.models.TexturedModel;
import felix.shot.Shot;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
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
    static float killcode = 53138.46687413f;
    static int myscore,oponentscore;

    public static void main(String[] args) {
        System.out.println("                                 ____    __    ____  _______  __        ______   ______   .___  ___.  _______                                \n" +
                "                                 \\   \\  /  \\  /   / |   ____||  |      /      | /  __  \\  |   \\/   | |   ____|                               \n" +
                "                                  \\   \\/    \\/   /  |  |__   |  |     |  ,----'|  |  |  | |  \\  /  | |  |__                                  \n" +
                "                                   \\            /   |   __|  |  |     |  |     |  |  |  | |  |\\/|  | |   __|                                 \n" +
                "                                    \\    /\\    /    |  |____ |  `----.|  `----.|  `--'  | |  |  |  | |  |____                                \n" +
                "                                     \\__/  \\__/     |_______||_______| \\______| \\______/  |__|  |__| |_______|                               \n" +
                "                                                                                                                                             \n" +
                "   .___________.  ______      .___  ___. ____    ____         _______. __    __    ______     ______   .___________. _______ .______         \n" +
                "   |           | /  __  \\     |   \\/   | \\   \\  /   /        /       ||  |  |  |  /  __  \\   /  __  \\  |           ||   ____||   _  \\        \n" +
                "   `---|  |----`|  |  |  |    |  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |  |  | |  |  |  | `---|  |----`|  |__   |  |_)  |       \n" +
                "       |  |     |  |  |  |    |  |\\/|  |   \\_    _/          \\   \\    |   __   | |  |  |  | |  |  |  |     |  |     |   __|  |      /        \n" +
                "       |  |     |  `--'  |    |  |  |  |     |  |        .----)   |   |  |  |  | |  `--'  | |  `--'  |     |  |     |  |____ |  |\\  \\----.   \n" +
                "       |__|      \\______/     |__|  |__|     |__|        |_______/    |__|  |__|  \\______/   \\______/      |__|     |_______|| _| `._____|   \n" +
                "                                                                                                                                             ");

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
            RawModel dragon = OBJLoader.loadObjModelResource("man3", loader);
            dragonT = new TexturedModel(dragon, new ModelTexture(loader.loadTextureResource("man3T3")));
            enemy = OBJLoader.loadObjModelResource("man", loader);
            enemyT = new TexturedModel(dragon, new ModelTexture(loader.loadTextureResource("path")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Player player = new Player(dragonT, new Vector3f(800, 30, 200), 0, 0, 0, 6);
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
        Entity enemyE = new Entity(enemyT,new Vector3f(800,0,200),0, random.nextFloat(804), 0, 6);
        entities.add(enemyE);

        Light light = new Light(vecco, vecpo);

        // ServerAndClient server = new ServerAndClient(player);

        Shot shot = new Shot(player,enemyE);
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
                                    if(xs==killcode){
                                        player.setPosition(new Vector3f(400, 0, 400));
                                        oponentscore++;
                                        player.setRotY(90);
                                        System.out.println("died \n score  :  "+myscore+"  :  "+oponentscore);
                                    }else {
                                        enemyE.setPosition(new Vector3f(xs, ys, zs));
                                        enemyE.setRotY(ws);
                                    }
                              //      System.out.println("Received: x=" + xs + ", y=" + ys + ", z=" + zs + ", w=" + ws);
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
                        float xs,ys,zs,ws;
                        if(shot.kill){
                            xs= killcode;
                            ys =  player.getPosition().y;
                            zs =  player.getPosition().z;
                            ws =  player.getRotY() % 360;
                            shot.kill = false;
                            myscore++;
                            player.setPosition(new Vector3f(1200, 0, 400));
                            player.setRotY(270);
                            System.out.println("kill \n score  :  "+myscore+"  :  "+oponentscore);
                        }else {
                            xs = player.getPosition().x;
                            ys = player.getPosition().y;
                            zs = player.getPosition().z;
                            ws = player.getRotY() % 360;
                        }
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

        List<UItexture> graphicsui = new ArrayList<UItexture>();
        UItexture gui = new UItexture(loader.loadTextureResource("scope01"),new Vector2f(0,0.3f),new Vector2f(0.15f,0.25f));
        UItexture tecky = new UItexture(loader.loadTextureResource("tecky"),new Vector2f(0,0.82f),new Vector2f(0.06f,0.1f));
        UItexture nulova = new UItexture(loader.loadTextureResource("none"),new Vector2f(-0.115f,0.835f),new Vector2f(0.06f,0.125f));
        UItexture first = new UItexture(loader.loadTextureResource("0"),new Vector2f(-0.05f,0.835f),new Vector2f(0.06f,0.125f));
        UItexture second = new UItexture(loader.loadTextureResource("0"),new Vector2f(0.05f,0.835f),new Vector2f(0.06f,0.125f));
        UItexture third = new UItexture(loader.loadTextureResource("none"),new Vector2f(0.115f,0.835f),new Vector2f(0.06f,0.125f));


        graphicsui.add(third);
        graphicsui.add(gui);
        graphicsui.add(tecky);
        graphicsui.add(first);
        graphicsui.add(nulova);
        graphicsui.add(second);

        UIRenderer uiRenderer = new UIRenderer(loader);

        while (!Display.isCloseRequested()) {

            camera.move();
            if (player.getPosition().x > 800) {
                player.move(terrain2);
            } else {
                player.move(terrain1);
            }
            shot.isThereAShoot();
            score(loader,nulova,first,second,third);
            //player.move(terrain1);
            //     System.out.println(player.getPosition().x);
            Mrenderer.processEntity(player);
            Mrenderer.processTerrain(terrain1);
            Mrenderer.processTerrain(terrain2);
            for (Entity entity : entities) {
                Mrenderer.processEntity(entity);
            }

            Mrenderer.render(light, camera);
            uiRenderer.render(graphicsui);
            DisplayManager.updateDisplay();

        }

        uiRenderer.cleanUp();
        Mrenderer.CleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        System.exit(0);
    }
    public static void score (Loader loader,UItexture first,UItexture second,UItexture third,UItexture fourth){
        if (myscore<10){
            first.setTexture(loader.loadTextureResource("none"));
            switch (myscore){
                case 0:second.setTexture(loader.loadTextureResource("0"));break;
                case 1:second.setTexture(loader.loadTextureResource("1"));break;
                case 2:second.setTexture(loader.loadTextureResource("2"));break;
                case 3:second.setTexture(loader.loadTextureResource("3"));break;
                case 4:second.setTexture(loader.loadTextureResource("4"));break;
                case 5:second.setTexture(loader.loadTextureResource("5"));break;
                case 6:second.setTexture(loader.loadTextureResource("6"));break;
                case 7:second.setTexture(loader.loadTextureResource("7"));break;
                case 8:second.setTexture(loader.loadTextureResource("8"));break;
                case 9:second.setTexture(loader.loadTextureResource("9"));break;
            }
        }else {
            String numberString = String.valueOf(myscore);
            char firstDigit = numberString.charAt(0);
            char secondDigit = numberString.charAt(1);
            int firstNumber = Character.getNumericValue(firstDigit);
            int secondNumber = Character.getNumericValue(secondDigit);
            switch (firstNumber){
                case 1:first.setTexture(loader.loadTextureResource("1"));break;
                case 2:first.setTexture(loader.loadTextureResource("2"));break;
                case 3:first.setTexture(loader.loadTextureResource("3"));break;
                case 4:first.setTexture(loader.loadTextureResource("4"));break;
                case 5:first.setTexture(loader.loadTextureResource("5"));break;
                case 6:first.setTexture(loader.loadTextureResource("6"));break;
                case 7:first.setTexture(loader.loadTextureResource("7"));break;
                case 8:first.setTexture(loader.loadTextureResource("8"));break;
                case 9:first.setTexture(loader.loadTextureResource("9"));break;
            }
            switch (secondNumber){
                case 0:second.setTexture(loader.loadTextureResource("0"));break;
                case 1:second.setTexture(loader.loadTextureResource("1"));break;
                case 2:second.setTexture(loader.loadTextureResource("2"));break;
                case 3:second.setTexture(loader.loadTextureResource("3"));break;
                case 4:second.setTexture(loader.loadTextureResource("4"));break;
                case 5:second.setTexture(loader.loadTextureResource("5"));break;
                case 6:second.setTexture(loader.loadTextureResource("6"));break;
                case 7:second.setTexture(loader.loadTextureResource("7"));break;
                case 8:second.setTexture(loader.loadTextureResource("8"));break;
                case 9:second.setTexture(loader.loadTextureResource("9"));break;
            }

        }
        if (oponentscore<10){
            fourth.setTexture(loader.loadTextureResource("none"));
            switch (oponentscore){
                case 0:third.setTexture(loader.loadTextureResource("0"));break;
                case 1:third.setTexture(loader.loadTextureResource("1"));break;
                case 2:third.setTexture(loader.loadTextureResource("2"));break;
                case 3:third.setTexture(loader.loadTextureResource("3"));break;
                case 4:third.setTexture(loader.loadTextureResource("4"));break;
                case 5:third.setTexture(loader.loadTextureResource("5"));break;
                case 6:third.setTexture(loader.loadTextureResource("6"));break;
                case 7:third.setTexture(loader.loadTextureResource("7"));break;
                case 8:third.setTexture(loader.loadTextureResource("8"));break;
                case 9:third.setTexture(loader.loadTextureResource("9"));break;
            }
        }else {
            String numberString = String.valueOf(oponentscore);
            char firstDigit = numberString.charAt(0);
            char secondDigit = numberString.charAt(1);
            int firstNumber = Character.getNumericValue(firstDigit);
            int secondNumber = Character.getNumericValue(secondDigit);
            switch (firstNumber){
                case 1:third.setTexture(loader.loadTextureResource("1"));break;
                case 2:third.setTexture(loader.loadTextureResource("2"));break;
                case 3:third.setTexture(loader.loadTextureResource("3"));break;
                case 4:third.setTexture(loader.loadTextureResource("4"));break;
                case 5:third.setTexture(loader.loadTextureResource("5"));break;
                case 6:third.setTexture(loader.loadTextureResource("6"));break;
                case 7:third.setTexture(loader.loadTextureResource("7"));break;
                case 8:third.setTexture(loader.loadTextureResource("8"));break;
                case 9:third.setTexture(loader.loadTextureResource("9"));break;
            }
            switch (secondNumber){
                case 0:fourth.setTexture(loader.loadTextureResource("0"));break;
                case 1:fourth.setTexture(loader.loadTextureResource("1"));break;
                case 2:fourth.setTexture(loader.loadTextureResource("2"));break;
                case 3:fourth.setTexture(loader.loadTextureResource("3"));break;
                case 4:fourth.setTexture(loader.loadTextureResource("4"));break;
                case 5:fourth.setTexture(loader.loadTextureResource("5"));break;
                case 6:fourth.setTexture(loader.loadTextureResource("6"));break;
                case 7:fourth.setTexture(loader.loadTextureResource("7"));break;
                case 8:fourth.setTexture(loader.loadTextureResource("8"));break;
                case 9:fourth.setTexture(loader.loadTextureResource("9"));break;
            }
        }
    }

}