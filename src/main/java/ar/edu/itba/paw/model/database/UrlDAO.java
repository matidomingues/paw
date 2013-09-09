package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.Url;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public interface UrlDAO {
    String resolve(String base);
    void addRoute(String base, String resol);
    Url reverseUrl(String resol);
}

