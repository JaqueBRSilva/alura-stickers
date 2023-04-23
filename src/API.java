public enum API {
    IMDB_TOP_MOVIES(
            "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json",
            new IMDBContentExtractor()
    ),
    IMDB_TOP_SERIES(
            "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json",
            new IMDBContentExtractor()
    ),
    NASA(
            "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-12-12&end_date=2022-12-14",
            new NasaContentExtractor()
    );

    private String url;
    private ContentExtractor extractor;

    API(String url, ContentExtractor extractor) {
        this.url = url;
        this.extractor = extractor;
    }

    public String getUrl() {
        return url;
    }

    public ContentExtractor getExtractor() {
        return extractor;
    }
}
