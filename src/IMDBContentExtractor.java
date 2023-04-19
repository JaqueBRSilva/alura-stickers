import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IMDBContentExtractor implements ContentExtractor {

    public List<Content> extractContents(String jsonBody) throws Exception {

        // EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> attributeList = parser.parse(jsonBody);

        List<Content> contents = new ArrayList<>();

        // POPULAR A LISTA DE CONTEÚDOS
        var generate = new StickersGenerate();

        for (Map<String, String> attributes: attributeList) {
            String title = attributes.get("title");
            String urlImage = attributes.get("image").replaceAll("(@?\\.)([0-9A-Z, ]+).jpg$","$1.jpg");
            double ratingMovie = Double.parseDouble(attributes.get("imDbRating"));

            var content = new Content(title, urlImage);

            String stickerText;
            InputStream approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-down.png"));

            if (ratingMovie >= 8.0) {
                stickerText = "T O P Z E R A";
                approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-up.png"));
            } else {
                stickerText = "PREFIRO NÃO COMENTAR...";
                approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-down.png"));
            }

            InputStream inputStream = new URL(content.urlImage()).openStream();
            String fileName = "output-imgs/" + content.title() + ".png";

            generate.create(inputStream, fileName, stickerText, approvesMovie);

            System.out.println("\u001b[1mTÍTULO :\u001b[m " + content.title());
            System.out.print(ratingMovie + " ");

            int starsNumber = (int) ratingMovie;

            for (int n = 1; n <= starsNumber; n++) {
                System.out.print("⭐");
            }

            System.out.println("\n");

            contents.add(content);
        }

        return contents;
    }
}
