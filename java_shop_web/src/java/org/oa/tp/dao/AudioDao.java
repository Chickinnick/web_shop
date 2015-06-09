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
import org.oa.tp.data.Audio;
import org.oa.tp.data.Author;

public class AudioDao implements AbstractDao<Audio> {

    private static final String TABLE_NAME = "audio";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AUTHOR_ID = "author_id";
    private static final String COLUMN_GENRE_ID = "genre_id";

    private Statement statement;
    private Connection connection;

    public AudioDao(Statement statement, Connection connection) {
        this.connection = connection;
        this.statement = statement;
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_NAME + " TEXT NOT NULL," + COLUMN_DURATION + " INT NOT NULL,"
                    + COLUMN_PRICE + " INT NOT NULL," + COLUMN_AUTHOR_ID + " INT NOT NULL,"
                    + COLUMN_GENRE_ID + " INT NOT NULL," + " FOREIGN KEY("+COLUMN_AUTHOR_ID +") REFERENCES author (id)"
                    + " FOREIGN KEY("+COLUMN_GENRE_ID +") REFERENCES genre (id)"+
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Audio> loadAll() {
        List<Audio> audios = new ArrayList<>();

        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                long authorId = resultSet.getLong(COLUMN_AUTHOR_ID);
                int duration = resultSet.getInt(COLUMN_DURATION);
                int price = resultSet.getInt(COLUMN_PRICE);
                long genreId = resultSet.getLong(COLUMN_GENRE_ID);

                audios.add(new Audio(id, name, authorId, duration, price, genreId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return audios;
    }

    @Override
    public Audio findById(long objectId) {
        Audio audio = null;
        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + objectId
                            + ";");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                long authorId = resultSet.getLong(COLUMN_AUTHOR_ID);
                int duration = resultSet.getInt(COLUMN_DURATION);
                int price = resultSet.getInt(COLUMN_PRICE);
                long genreId = resultSet.getLong(COLUMN_GENRE_ID);

                audio = new Audio(id, name, authorId, duration, price, genreId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audio;
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
    public boolean update(Audio changed) {
        return false;
    }

    @Override
    public boolean add(Audio item) {
        
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " " + " (" + COLUMN_NAME + ", " + COLUMN_DURATION + ", "
                    + COLUMN_AUTHOR_ID + ", " + COLUMN_GENRE_ID + ", " + COLUMN_PRICE + ")"
                    + " VALUES (' " + item.getName() + "','" + item.getDuration() + "','" + item.getAuthor() + "','" + item.getGenre() +
                    "','" + item.getPrice()
                    + "');");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Audio> collection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
