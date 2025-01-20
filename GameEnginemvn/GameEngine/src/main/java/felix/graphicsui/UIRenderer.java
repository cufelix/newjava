package felix.graphicsui;

import felix.models.RawModel;
import felix.renderEngine.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.renderer.Renderer;

import java.util.List;

public class UIRenderer {
    private  RawModel quad;
    private  UIShader shader;
   // Loader loader;
    public UIRenderer(Loader loader) {
       // loader = this.loader;
        float[] positions = {-1,1,-1,-1,1,1,1,-1};
        quad = loader.loadToVAO(positions);
        shader = new UIShader();
    }
    public void render(List<UItexture>graphicsui) {
        shader.start();
       GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        for (UItexture gui : graphicsui) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,gui.getTexture());
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        }
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
    public void cleanUp() {
        shader.cleanUp();
    }
}
