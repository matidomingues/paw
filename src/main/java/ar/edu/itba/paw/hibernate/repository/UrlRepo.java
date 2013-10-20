package ar.edu.itba.paw.hibernate.repository;

public interface UrlRepo {
	public String shorten(String url);

	public String resolve(String data);
}
