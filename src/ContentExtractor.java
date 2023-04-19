import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ContentExtractor {
    public List<Content> extractContents(String json) throws Exception;

}
