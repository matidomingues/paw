package ar.edu.itba.paw.domain.url;

public interface UrlRepo {
	public String shorten(String url);

	public String resolve(String data);
}
