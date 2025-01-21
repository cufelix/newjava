package felix.graphicsui;

import felix.models.RawModel;
import felix.renderEngine.Loader;
import felix.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.renderer.Renderer;

import java.util.List;

public class UIRenderer {
    private final RawModel quad;
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (UItexture gui : graphicsui) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,gui.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(),gui.getSize());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
    public void cleanUp() {
        shader.cleanUp();
    }
}
