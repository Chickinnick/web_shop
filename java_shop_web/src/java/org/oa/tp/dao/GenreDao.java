package org.oa.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.oa.tp.data.Album;

import org.oa.tp.data.Genre;

class GenreDao implements AbstractDao<Genre> {

	private static final String TABLE_NAME = "genre";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private Statement statement;
	private Connection connection;

	public GenreDao(Statement statement, Connection connection) {
		this.connection = connection;
		this.statement = statement;
		try {
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
					+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
					+ COLUMN_NAME + " TEXT NOT NULL" + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Genre> loadAll() {
		List<Genre> genres = new ArrayList<>();

		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt(COLUMN_ID);
				String name = resultSet.getString(COLUMN_NAME);
				genres.add(new Genre(id, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return genres;
	}

	@Override
	public Genre findById(long objectId) {
		Genre genre = null;
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + objectId
							+ ";");
			while (resultSet.next()) {
				int id = resultSet.getInt(COLUMN_ID);
				String name = resultSet.getString(COLUMN_NAME);
				genre = new Genre(id, name);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genre;
	}

	@Override
	public boolean delete(long id) {

		try {
			statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean update(Genre changed) {
		try {
			statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + "='"
					+ changed.getName() + "'," 
					+ "' WHERE " + COLUMN_ID + "=" + changed.getId() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean add(Genre item) {
		try {
			statement.executeUpdate("INSERT INTO " + TABLE_NAME + " " + " (" + COLUMN_NAME  + ")"
					+ " VALUES ('" + item.getName() 
					+ "')");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<Genre> collection) {
        return false;
        }    
}
