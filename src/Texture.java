import javafx.scene.image.Image;

import java.io.File;

public class Texture {
    public Rectangle region;
    public Image image;

    public Texture(){

    }

    //Parameterized Constructor
    public Texture(String imageFileName){
        image = new Image(imageFileName);
        region = new Rectangle();
        double width = image.getWidth();
        double height = image.getHeight();
        region.setValues(0,0, width, height);
    }

    /*
     * Load Method - public static
     * Loads in the image into a texture on screen
     *     @param imageFileName - String
     *     @return texture - Texture Object
     */
    public static Texture load(String imageFileName){
        Texture texture = new Texture();

        //these will help us in the long term when we make a .jar file
        //String fileName = new File(imageFileName).toURI().toString();
        //texture.image = new Image(fileName);
        texture.image = new Image(imageFileName);
        texture.region = new Rectangle();
        double width = texture.image.getWidth();
        double height = texture.image.getHeight();
        texture.region.setValues(0,0, width, height);

        return texture;
    }


}
