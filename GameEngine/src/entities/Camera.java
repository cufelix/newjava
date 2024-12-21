package entities;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.awt.event.MouseEvent;


public class Camera {

    private final Vector3f position = new Vector3f(25, 2, 50);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= 0.05f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.04f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.04f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += 0.05f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y += 0.04f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            position.y -= 0.04f;
        }

    }


    public Vector3f getPosition() {
        return position;
    }


    public float getPitch() {
        return pitch;
    }


    public float getYaw() {
        return yaw;
    }


    public float getRoll() {
        return roll;
    }


}

