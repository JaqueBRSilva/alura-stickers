import java.util.List;
import java.util.Map;

public class NasaContentExtractor implements ContentExtractor {
    public List<Content> extractContents(String jsonBody){

        // EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, imagem)
        var parser = new JsonParser();
        List<Map<String, String>> attributeList = parser.parse(jsonBody);

        return attributeList.stream()
                .map(attributes -> new Content(
                        attributes.get("title"),
                        attributes.get("url")))
                .toList();
    }
}
