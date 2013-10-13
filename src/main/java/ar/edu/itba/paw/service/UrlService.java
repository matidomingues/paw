package ar.edu.itba.paw.service;

public interface UrlService {

	public String shorten(String url);
	public String resolve(String data);

}