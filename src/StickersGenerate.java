import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class StickersGenerate {

    public void create(InputStream inputStream, String fileName) throws Exception {

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

        // CONFIGURAR A FONTE
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 64);
        graphics.setColor(Color.ORANGE);
        graphics.setFont(fonte);

        // ESCREVER UMA FRASE NA NOVA IMAGEM
        String text = "TOPZERA";
        var fontMetrics = graphics.getFontMetrics();
        var rectangle = fontMetrics.getStringBounds(text, graphics);
        int textWidth = (int) rectangle.getWidth();
        int textPositionX = (widthValue - textWidth) / 2;
        graphics.drawString(text, textPositionX, newHeight - 100);

        // ESCREVER A NOVA IMAGEM EM UM ARQUIVO
        ImageIO.write(newImage, "png", new File(fileName));
    }
}