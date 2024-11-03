package felix.components;

import javax.swing.ImageIcon;
import java.net.URL;

public class Utils {

    public static ImageIcon loadImage(String imageName) {
        String resourcePath = "/" + imageName;
        URL resourceURL = Utils.class.getResource(resourcePath);
        return new ImageIcon(resourceURL);
    }
}
