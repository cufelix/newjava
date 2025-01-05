package felix.models;


import felix.textures.ModelTexture;

public class TexturedModel {

    private final RawModel rawModel;
    private final ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.rawModel = model;
        this.texture = texture;
    }


    public RawModel getRawModel() {
        return rawModel;
    }
//working on it

    public ModelTexture getTexture() {
        return texture;
    }


}
