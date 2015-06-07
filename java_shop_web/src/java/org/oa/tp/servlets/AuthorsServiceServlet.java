/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oa.tp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Audio;
import org.oa.tp.data.Author;


public class AuthorsServiceServlet extends ServiceServlet {

    
    private static final String PARAMETER_ID = "id";
    
    private static final String PARAMETER_FIRST_NAME = "first_name";
    private static final String PARAMETER_LAST_NAME = "last_name";
    private static final String PARAMETER_AGE = "age";
    private static final String PARAMETER_GENDER = "gender";

    @Override
    void create(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    
        
        String firstNameString = request.getParameter(PARAMETER_FIRST_NAME);
        String lastNameString = request.getParameter(PARAMETER_LAST_NAME);
        String ageString = request.getParameter(PARAMETER_AGE);
        String genderString = request.getParameter(PARAMETER_GENDER);

        int age = Integer.parseInt(ageString);

        Author author = new Author(firstNameString, lastNameString, age, genderString);

        boolean created = facade.getAuthorDao().add(author);
        try (PrintWriter out = response.getWriter()) {
            if (created) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"Author created\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed create author\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void delete(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    String idString = request.getParameter(PARAMETER_ID);
    
        long id = Long.parseLong(idString);
        boolean deleted = facade.getAudioDao().delete(id);
        try (PrintWriter out = response.getWriter()) {
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"audio deleted\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed delete audio\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void update(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
      
        String firstNameString = request.getParameter(PARAMETER_FIRST_NAME);
        String lastNameString = request.getParameter(PARAMETER_LAST_NAME);
        String ageString = request.getParameter(PARAMETER_AGE);
        String genderString = request.getParameter(PARAMETER_GENDER);

        int age = Integer.parseInt(ageString);

        Author author = new Author(firstNameString, lastNameString, age, genderString);

        
        boolean updated = facade.getAuthorDao().update(author);
        try (PrintWriter out = response.getWriter()) {
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"Author updated\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed update author\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void getAll(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    List<Author> authors = facade.getAuthorDao().loadAll();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            gson.toJson(authors, out);
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    
    }

    
}
