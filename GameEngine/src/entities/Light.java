package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
    private Vector3f colour;
    private Vector3f position;

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Light(Vector3f colour, Vector3f position) {
        this.colour = colour;
        this.position = position;
    }
}
