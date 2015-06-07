package org.oa.tp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.oa.tp.data.Audio;
import org.oa.tp.data.Author;

public class AuthorDao implements AbstractDao<Author> {

    private static final String TABLE_NAME = "author";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";

    private Statement statement;
    private Connection connection;
    
       public AuthorDao(Statement statement, Connection connection) {
        this.connection = connection;
        this.statement = statement;
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + COLUMN_FIRST_NAME + " TEXT NOT NULL," 
                    + COLUMN_LAST_NAME + " TEXT NOT NULL," 
                    + COLUMN_AGE + " INT NOT NULL," 
                    + COLUMN_GENDER + " TEXT NOT NULL" + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> loadAll() {

        List<Author> authors = new ArrayList<>();

        ResultSet resultSet;
        try {
            resultSet = statement
                    .executeQuery("SELECT * FROM " + TABLE_NAME + ";");

            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String firstName = resultSet.getString(COLUMN_FIRST_NAME);
                String lastName = resultSet.getString(COLUMN_LAST_NAME);
                int age = resultSet.getInt(COLUMN_AGE);
                String gender = resultSet.getString(COLUMN_GENDER);

                authors.add(new Author(id, firstName, lastName, age, gender));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authors;
    }

    @Override
    public Author findById(long objectId) {
             Author author = null;
            
             try {
                ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM " + TABLE_NAME 
                            + " WHERE " 
                            + COLUMN_ID + "=" 
                            + objectId
                            + ";");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String firstName = resultSet.getString(COLUMN_FIRST_NAME);
                String lastName = resultSet.getString(COLUMN_LAST_NAME);
                int age = resultSet.getInt(COLUMN_AGE);
                String gender = resultSet.getString(COLUMN_GENDER);

                author = new Author(objectId, firstName, lastName, age, gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;    
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
    public boolean update(Author changed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(Author item) {
  
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " " 
                    + " (" 
                    + COLUMN_FIRST_NAME + ", " 
                    + COLUMN_LAST_NAME + ", "
                    + COLUMN_AGE + ", " + COLUMN_GENDER  + ")"
                    + " VALUES (' " 
                    + item.getFirstName() + "','" 
                    + item.getLastName()+ "','" 
                    + item.getAge()+ "','" 
                    + item.getGender()
                    + "');");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Author> collection) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
