package entities;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.awt.event.MouseEvent;


public class Camera {

    private final Vector3f position = new Vector3f(800, 35, 402);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
    }

    public void move() {


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

