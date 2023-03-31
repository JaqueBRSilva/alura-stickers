import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

    // FAZER UMA CONEXÃO HTTP E BUSCAR OS TOP 250 FILMES
        String imdbAuthToken = System.getenv("IMDB_API_KEY");
        String url = "https://imdb-api.com/en/API/Top250Movies/" + imdbAuthToken;
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

    //  EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

    //  EXIBIR E MANIPULAR OS DADOS
        for (int i = 0; i < 3; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);

            System.out.println("\u001b[1mTÍTULO :\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mPÔSTER :\u001b[m " + filme.get("image"));

            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            System.out.print(classificacao + " ");

            int numeroEstrelas = (int) classificacao;

            for (int n = 1; n <= numeroEstrelas; n++){
                System.out.print("⭐");
            }

            System.out.println("\n");
        }
    }
}
