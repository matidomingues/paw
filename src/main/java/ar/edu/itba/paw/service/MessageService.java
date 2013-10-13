package ar.edu.itba.paw.service;

public interface MessageService {

	public String shorten(String url);
    public String prepareMessage(String context, String message);

}