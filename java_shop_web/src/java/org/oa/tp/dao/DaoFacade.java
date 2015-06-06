package org.oa.tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

import javax.xml.transform.stax.StAXSource;

import org.oa.tp.data.Album;
import org.oa.tp.data.Genre;

public class DaoFacade {

    private Connection connection;
    private AlbumDao albumDao;
    private AudioDao audioDao;
    private GenreDao genreDao = new GenreDao();
    private Statement statement;

    public DaoFacade(ServletContext context) {
        try {
            Class.forName("org.sqlite.JDBC");
            String path = context.getRealPath("test.db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (connection == null) {
            System.exit(1);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        albumDao = new AlbumDao(statement, connection);
        audioDao = new AudioDao(statement, connection);
    }

    public AbstractDao<Album> getAlbumDao() {
        return albumDao;
    }

    public AbstractDao<Genre> getGenreDao() {
        return genreDao;
    }

    public AudioDao getAudioDao() {
        return audioDao;
    }

     
    
    public void closeSqlConnection() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
