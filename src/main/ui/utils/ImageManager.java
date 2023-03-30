package ui.utils;

import javax.swing.*;
import java.awt.*;

// Represents a JPanel for displaying an image
public class ImageManager extends JPanel {
    private final Image image;

    // EFFECTS: constructs an image manager with the given image
    public ImageManager(Image imageName) {
        this.image = imageName;
    }

    // EFFECTS: draws the image to the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(),this);
    }
}
