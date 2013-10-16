package ar.edu.itba.paw.model.database.implementations;

import ar.edu.itba.paw.manager.ConnectionManager;
import ar.edu.itba.paw.manager.DatabaseException;
import ar.edu.itba.paw.model.Url;
import ar.edu.itba.paw.model.database.UrlDAO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UrlDAOImpl implements UrlDAO{

	public Url findbyBase(String base){
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

	public Url findByResol(String resol){
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
	
	public void add(Url url){
		try {
			Connection connection = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO short_url(base, resol) values(?, ?)");
			stmt.setString(1, url.getBase());
			stmt.setString(2, url.getResol());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
}
