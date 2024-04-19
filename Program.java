import java.io.File;
import javax.imageio.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String args[]) {
        try {
            BufferedImage image = ImageIO.read(new File("example.png"));
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            List<Triple<Integer, Integer, Integer>> tripleList = new ArrayList<>();
            String asciiChars = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

            
            // Iterate over the image and collect pixel data
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    int pixels = image.getRGB(x, y);
                    Color color = new Color(pixels, true);

                    if (color.getAlpha() == 0)
                        continue;
                    int red = color.getRed();
                    int blue = color.getBlue();
                    int green = color.getGreen();
                    Triple<Integer, Integer, Integer> RGB = new Triple<Integer, Integer, Integer>(red, green, blue);
                    tripleList.add(RGB);
                }
            }
            
            // Print ASCII art with adjusted aspect ratio
            for (int i = 0; i < tripleList.size(); i += imageWidth) {
                for (int j = i; j < i + imageWidth; j++) {
                    Triple<Integer, Integer, Integer> triple = tripleList.get(j);
                    // Calculate luminosity using ITU-R BT.709 formula (cast for clarity)
                    double luminosity = 0.2126 * triple.getFirst() + 0.7152 * triple.getSecond()
                            + 0.0722 * triple.getThird();
                    // Ensure index is within the valid range
                    int index = (int) (luminosity / 255.0 * (asciiChars.length() - 1));
                    index = Math.max(0, Math.min(index, asciiChars.length() - 1));
                    System.out.print(asciiChars.charAt(index));
                }
                System.out.println(); // Move to the next line after printing each row
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
