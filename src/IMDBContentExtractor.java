import java.util.List;
import java.util.Map;

public class IMDBContentExtractor implements ContentExtractor {
    public List<Content> extractContents(String jsonBody) throws Exception {

        // EXTRAIR SÓ OS DADOS QUE INTERESSAM (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> attributeList = parser.parse(jsonBody);

        return attributeList.stream()
                .map(attributes -> new Content(
                        attributes.get("title"),
                        attributes.get("image").replaceAll("(@?\\.)([0-9A-Z, ]+).jpg$", "$1.jpg")))
                .toList();

    }
}
