import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // FAZER UMA CONEXÃO HTTP E BUSCAR OS TOP 250 FILMES
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        IMDBContentExtractor extractor = new IMDBContentExtractor();

//        String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-12-12&end_date=2022-12-14";
//        NasaContentExtractor extractor = new NasaContentExtractor();

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

            InputStream inputStream = new URL(content.getUrlImage()).openStream();
            String fileName = "output-imgs/" + content.getTitle() + ".png";

            generate.create(inputStream, fileName, stickerText, approvesMovie);

            System.out.println("\u001b[1mGERANDO :\u001b[m " + content.getTitle());
            System.out.println("\n");
        }
    }
}
