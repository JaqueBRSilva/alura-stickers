import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class StickersGenerate {

    public void create(InputStream inputStream, String fileName, String ratingMediaText, InputStream inputStreamOverlapImage) throws Exception {

        // LEITURA DA IMAGEM
        BufferedImage originalImage = ImageIO.read(inputStream);

        // CRIAR NOVA IMAGEM EM MEMÓRIA COM TRANSPARÊNCIA E COM TAMANHO NOVO
        int widthValue = originalImage.getWidth();
        int heightValue = originalImage.getHeight();
        int newHeight = heightValue + 200;
        BufferedImage newImage = new BufferedImage(widthValue, newHeight, BufferedImage.TRANSLUCENT);

        // COPIAR A IMAGEM ORIGINAL PRA NOVA IMAGEM (EM MEMÓRIA)
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(originalImage, 0, 0, null);

        BufferedImage overlapImage = ImageIO.read(inputStreamOverlapImage);
        int overlapImagePositionY = newHeight - overlapImage.getHeight();
        graphics.drawImage(overlapImage, 0, overlapImagePositionY, null);

        // CONFIGURAR A FONTE
        var fontName = new Font("Impact", Font.PLAIN, 84);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fontName);

        // ESCREVER UMA FRASE NA NOVA IMAGEM
        var fontMetrics = graphics.getFontMetrics();
        var rectangle = fontMetrics.getStringBounds(ratingMediaText, graphics);
        int textWidth = (int) rectangle.getWidth();
        int textPositionX = (widthValue - textWidth) / 2;
        int textPositionY = newHeight - 100;
        graphics.drawString(ratingMediaText, textPositionX, textPositionY);

        // CONTORNO DA FONTE
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(ratingMediaText, fontName, fontRenderContext);
        Shape outlinedText = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(textPositionX, textPositionY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(widthValue * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outlinedText);

        // ESCREVER A NOVA IMAGEM EM UM ARQUIVO
        ImageIO.write(newImage, "png", new File(fileName));
    }
}