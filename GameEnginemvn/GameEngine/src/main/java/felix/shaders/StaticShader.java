package felix.shaders;


import felix.entities.Light;
import org.lwjgl.util.vector.Matrix4f;


import org.lwjgl.util.vector.Vector3f;
import felix.toolbox.Maths;


import felix.entities.Camera;


public class StaticShader extends ShaderProgram{

//    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
//    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
    private static final String VERTEX_FILE = "/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_needMoreFakeLight;
    private int location_skyColour;


    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2,"normal");
    }
//4:43

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity =super.getUniformLocation("reflectivity");
        location_needMoreFakeLight = super.getUniformLocation("needMoreFakeLight");
        location_skyColour = super.getUniformLocation("skyColour");

    }
    public void loadFakeLightingVariable(boolean useFake){
        super.loadBoolean(location_needMoreFakeLight, useFake);
    }


    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());;
        super.loadVector(location_lightColour, light.getColour());

    }
    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper,damper);
        super.loadFloat(location_reflectivity,reflectivity);
    }
    public void  loadSkyColour(float r,float g,float b){
        super.loadVector(location_skyColour,new Vector3f(r,g,b));
    }



}
