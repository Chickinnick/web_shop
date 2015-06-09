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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Genre;

@WebServlet(name = "GenreServiceServlet", urlPatterns = {"/genres"})

public class GenreServiceServlet extends ServiceServlet {

    
     private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
  
    
     @Override
    void create(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
        String nameString = request.getParameter(PARAMETER_NAME);
        Genre  genre = new Genre(nameString);

        boolean created = facade.getGenreDao().add(genre);
        try (PrintWriter out = response.getWriter()) {
            if (created) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"genre created\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed create genre\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(GenreServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     @Override
    void delete(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    String idString = request.getParameter(PARAMETER_ID);
    
        long id = Long.parseLong(idString);
        boolean deleted = facade.getGenreDao().delete(id);
        try (PrintWriter out = response.getWriter()) {
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"genre deleted\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed delete genre\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(GenreServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     @Override
    void update(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
      
        String nameString = request.getParameter(PARAMETER_NAME);

Genre  genre = new Genre(nameString);
        
        boolean updated = facade.getGenreDao().update(genre);
        try (PrintWriter out = response.getWriter()) {
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"genre updated\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed update genre \"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(GenreServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     @Override
    void getAll(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    List<Genre> genres = facade.getGenreDao().loadAll();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            gson.toJson(genres, out);
        } catch (IOException ex) {
            Logger.getLogger(GenreServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    
    }

    
}
