package ar.edu.itba.paw.model.database.implamentations;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UrlDAOImpl implements UrlDAO {

	private static UrlDAO instance = null;

	private UrlDAOImpl(){
		
	}
	
	public static synchronized UrlDAO getInstance(){
		if(instance == null){
			instance = new UrlDAOImpl();
		}
		return instance;
	}
	
	private Url findBase(String base){
		try {
			Url url = null;
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT resol FROM short_url WHERE base = ?");
			stmt.setString(1, base);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				String resol = results.getString(1);
				url = new Url(base, resol);
			}
			return url;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
    public String resolve(String base){
		return findBase(base).getResol();
	}
	
	public Url reverseUrl(String resol){
		try {
			Url url = null;
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("SELECT base FROM short_url WHERE resol = ?");
			stmt.setString(1, resol);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				String base = results.getString(1);
				url = new Url(base, resol);
			}
			return url;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public void addRoute(String base, String resol){
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO short_url(base, resol) values(?, ?)");
			stmt.setString(1, base);
			stmt.setString(2, resol);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
