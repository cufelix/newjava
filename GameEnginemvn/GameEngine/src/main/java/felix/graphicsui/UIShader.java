package felix.graphicsui;

import felix.shaders.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

public class UIShader extends ShaderProgram {
    private static final String VERTEX_FILE = "/shaders/guiFragmentShader.txt";
    private static final String FRAGMENT_FILE = "/shaders/guiFragmentShader.txt";

    private int location_transformationMatrix;


    public UIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }


    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
