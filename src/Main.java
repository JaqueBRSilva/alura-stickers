import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        API api = API.IMDB_TOP_MOVIES;
//        API api = API.NASA;

        String url = api.getUrl();

        ContentExtractor extractor = api.getExtractor();

        var http = new ClientHttp();
        String jsonBody = http.searchDatas(url);

        var directory = new File("output-imgs/");
        directory.mkdir();

        // EXIBIR E MANIPULAR OS DADOS
        List<Content> contents = extractor.extractContents(jsonBody);

        var generate = new StickersGenerate();

        for (int i = 0; i < 3; i ++) {

            Content content = contents.get(i);

            String stickerText = "TOPZERA";
            InputStream approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-up.png"));

            InputStream inputStream = new URL(content.urlImage()).openStream();
            String fileName = "output-imgs/" + content.title() + ".png";

            generate.create(inputStream, fileName, stickerText, approvesMovie);

            System.out.println("\u001b[1mGERANDO :\u001b[m " + content.title());
            System.out.println("\n");
        }
    }
}
