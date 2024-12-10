package renderEngine;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class OBJLoader {
    public static RawModel loadObjModel(String fileName, Loader loader) throws FileNotFoundException {
        FileReader fileReader = new FileReader(new File("res/"+fileName+".obj"));
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray= null;
        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v")){//it also may be "v "
                    Vector3f vertex =//4:47
                } else if (line.startsWith("vt ")) {

                }else if (line.startsWith("vn ")) {

                }else if (line.startsWith("f ")) {

                }
            }
        }catch(Exception e){
            System.out.println("Yo nigga your shit doesn't work fucking nigga make this shit work!!!!")
            e.printStackTrace();
        }
    }
}
