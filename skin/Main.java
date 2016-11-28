package skin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File in = new File("skin");
        if(!in.exists()) {
            in.mkdir();
            return;
        }
        File out = new File("no-overlay");
        if(!out.exists()) {
            if(!out.mkdir()) {
                return;
            }
        }
        for(File file : in.listFiles()) {
            String name = file.getName();
            if(name.endsWith(".png")) {
                System.out.println(name);
                removeOverlay(file, new File(out, name));
            }
        }
    }

    public static BufferedImage removeOverlay(File in, File out) {
        BufferedImage src = null;
        try {
            src = ImageIO.read(in);
        } catch(Exception e) {
            e.printStackTrace();
        }

        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(src, 0, 0, null);

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        Rectangle2D.Double rect;
        rect = new Rectangle2D.Double(32, 0, 32, 16);
        graphics.fill(rect);
        rect = new Rectangle2D.Double(0, 32, 64, 16);
        graphics.fill(rect);
        rect = new Rectangle2D.Double(0, 48, 16, 16);
        graphics.fill(rect);
        rect = new Rectangle2D.Double(48, 48, 16, 16);
        graphics.fill(rect);
        graphics.setPaintMode();

        try {
            ImageIO.write(image, "png", out);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
