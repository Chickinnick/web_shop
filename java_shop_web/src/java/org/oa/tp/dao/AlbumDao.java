package org.oa.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.oa.tp.data.*;

class AlbumDao implements AbstractDao<Album> {

	private static final String TABLE_NAME = "album";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_YEAR = "year";
	private Statement statement;
	private Connection connection;

	public AlbumDao(Statement statement, Connection connection) {
		this.connection = connection;
		this.statement = statement;
		try {
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
					+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
					+ COLUMN_NAME + " TEXT NOT NULL," + COLUMN_YEAR + " INT NOT NULL" + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Album> loadAll() {
		List<Album> albums = new ArrayList<>();

		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt(COLUMN_ID);
				String name = resultSet.getString(COLUMN_NAME);
				int year = resultSet.getInt(COLUMN_YEAR);
				albums.add(new Album(id, name, year));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return albums;
	}

	@Override
	public Album findById(long objectId) {
		Album album = null;
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + objectId
							+ ";");
			while (resultSet.next()) {
				int id = resultSet.getInt(COLUMN_ID);
				String name = resultSet.getString(COLUMN_NAME);
				int year = resultSet.getInt(COLUMN_YEAR);
				album = new Album(id, name, year);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return album;
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
	public boolean update(Album changed) {
		try {
			statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + "='"
					+ changed.getName() + "'," + COLUMN_YEAR + "='" + changed.getYear()
					+ "' WHERE " + COLUMN_ID + "=" + changed.getId() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean add(Album item) {
		try {
			statement.executeUpdate("INSERT INTO " + TABLE_NAME + " " + " (" + COLUMN_NAME + ", " + COLUMN_YEAR + ")"
					+ " VALUES ('" + item.getName() + "','" + item.getYear()
					+ "')");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<Album> collection) {
		
		String sqlQuery = "INSERT INTO " + TABLE_NAME + " " + " (" + COLUMN_NAME + ", " + COLUMN_YEAR + ")"
				+ " VALUES ( ? , ? )";
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			for (Album album : collection) {
				statement.setString(1, album.getName());
				statement.setInt(2, album.getYear());
				statement.executeUpdate();
			}

			connection.commit();
			connection.setAutoCommit(true);
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	
}
