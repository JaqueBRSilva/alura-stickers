import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NasaContentExtractor implements ContentExtractor {
    public List<Content> extractContents(String jsonBody){

        // EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, imagem)
        var parser = new JsonParser();
        List<Map<String, String>> attributeList = parser.parse(jsonBody);

        List<Content> contents = new ArrayList<>();

        // POPULAR A LISTA DE CONTEÚDOS
        for (Map<String, String> attributes: attributeList) {
            String title = attributes.get("title");
            String urlImage = attributes.get("url");
            var content = new Content(title, urlImage);

            contents.add(content);
        }

        return contents;
    }
}
