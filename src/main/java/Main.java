import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int SQUARE_SIZE = 5;
    private static final Random rand = new Random();
    private static int countRed = 0;
    private static int countGreen = 0;
    private static int countBlue = 0;

    public static void main(String... args) {
        BufferedImage buffer = prepareImage(WIDTH, HEIGHT);
        Graphics2D g2d = prepareGraphics(buffer);
        drawRandomSquares(g2d);
        File f = new File("target/test.png");
        try {
            toFile(buffer, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Red   : " + countRed);
        System.out.println("Green : " + countGreen);
        System.out.println("Blue  : " + countBlue);
    }

    private static void drawRandomSquares(Graphics2D g2d) {
        for (int x = 0; x < WIDTH; x += SQUARE_SIZE) {
            for (int y = 0; y < HEIGHT; y += SQUARE_SIZE) {
                g2d.setPaint(getRandomColor());
                g2d.fillRect(x,y,SQUARE_SIZE,SQUARE_SIZE);
            }
        }
    }

    private static BufferedImage prepareImage(int width, int height) {
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffer.setAccelerationPriority(1.0f);
        return buffer;
    }

    private static Graphics2D prepareGraphics(BufferedImage buffer) {
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints quality = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.addRenderingHints(antialiasing);
        g2d.addRenderingHints(quality);
        return g2d;
    }

    private static void toFile(BufferedImage buffer, File f) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("PNG").next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(f)) {
            writer.setOutput(ios);
            writer.write(buffer);
            ImageIO.write(buffer, "PNG", f);
        }
    }

    private static Color getRandomColor() {
        int i = rand.nextInt(3);
        if (i == 0) {
            ++countRed;
            return Color.RED;
        }
        if (i == 1) {
            ++countGreen;
            return Color.GREEN;
        }
        ++countBlue;
        return Color.BLUE;
    }
}
