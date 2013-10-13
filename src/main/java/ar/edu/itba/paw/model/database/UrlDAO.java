package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Url;

public interface UrlDAO {

	public Url findbyBase(String base);

	public Url findByResol(String resol);

	public void add(Url url);

}