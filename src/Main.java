import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        // FAZER UMA CONEXÃO HTTP E BUSCAR OS TOP 250 FILMES
//        String imdbAuthToken = System.getenv("IMDB_API_KEY");
//        String url = "https://imdb-api.com/en/API/Top250Movies/" + imdbAuthToken;
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> moviesList = parser.parse(body);

        var directory = new File("output-imgs/");
        directory.mkdir();

        // EXIBIR E MANIPULAR OS DADOS
        var generate = new StickersGenerate();

        for (Map<String, String> movie : moviesList) {

            String urlImage = movie.get("image");
            String urlBiggerImage = urlImage.replaceFirst("(@?\\.)([0-9A-Z, ]+).jpg$","$1.jpg");
            String movieName = movie.get("title");
            double ratingMovie = Double.parseDouble(movie.get("imDbRating"));

            String stickerText;
            InputStream approvesMovie;

            if (ratingMovie >= 8.0) {
                stickerText = "T O P Z E R A";
                approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-up.png"));
            } else {
                stickerText = "PREFIRO NÃO COMENTAR...";
                approvesMovie = new FileInputStream(new File("overlap-icons/thumbs-down.png"));
            }

            InputStream inputStream = new URL(urlBiggerImage).openStream();
            String fileName = "output-imgs/" + movieName + ".png";

            generate.create(inputStream, fileName, stickerText, approvesMovie);

            System.out.println("\u001b[1mTÍTULO :\u001b[m " + movieName);
            System.out.print(ratingMovie + " ");

            int starsNumber = (int) ratingMovie;

            for (int n = 1; n <= starsNumber; n++) {
                System.out.print("⭐");
            }

            System.out.println("\n");
        }
    }
}
