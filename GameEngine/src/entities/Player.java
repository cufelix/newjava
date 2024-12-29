package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
//10:35
import static renderEngine.DisplayManager.getFrameTimeSeconds;

public class Player extends Entity{

    private  static final  float RUN =20;
    private  static final  float TURN=159;
    private float currentSpeed = 0;
    private  float currentTurnSpeed =0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    public void move(){
        checkInputs();
        super.increaseRotation(0,currentTurnSpeed* getFrameTimeSeconds(),0);
        float distance = currentSpeed* getFrameTimeSeconds();
        float dx =(float)( distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz =(float)( distance * Math.cos(Math.toRadians(super.getRotY())));
          //  System.out.println(getFrameTimeSeconds());
       // System.out.println("dx : "+dx+"  dz: "+dz);
        super.increasePosition(dx,0,dz);
    }
    private void checkInputs(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN;
         //   System.out.println(currentSpeed);
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                this.currentSpeed = -RUN;
        }
        else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN;
          //  System.out.println(currentTurnSpeed);
        }
        else {
            this.currentTurnSpeed = 0;
        }

    }

}
