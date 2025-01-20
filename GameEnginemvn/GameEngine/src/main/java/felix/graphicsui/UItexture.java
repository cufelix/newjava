package felix.graphicsui;

import org.lwjgl.util.vector.Vector2f;

public class UItexture {
    private int texture;
    private Vector2f position;
    private Vector2f size;

    public UItexture(int texture, Vector2f position, Vector2f size) {
        this.texture = texture;
        this.position = position;
        this.size = size;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getSize() {
        return size;
    }
}
