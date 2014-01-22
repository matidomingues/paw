package ar.edu.itba.paw.domain.url;

public interface UrlRepo {

    public static final String SHORT_URL_REGEX = "/s/[a-z0-9]{5}";

	public String shorten(String url);

	public String resolve(String data);
}
