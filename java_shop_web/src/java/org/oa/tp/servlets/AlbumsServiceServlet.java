package org.oa.tp.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Album;

@WebServlet(name = "AlbumsService", urlPatterns = {"/albums_service"})

public class AlbumsServiceServlet extends ServiceServlet {

    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";

    
    void create(HttpServletRequest request,HttpServletResponse response , DaoFacade facade ) {
        String nameString = request.getParameter(PARAMETER_NAME);
            String yearString = request.getParameter(PARAMETER_YEAR);
            int year = Integer.parseInt(yearString);
            Album album = new Album(nameString, year);
            boolean created = facade.getAlbumDao().add(album);
            try (PrintWriter out = response.getWriter()) {
                if (created) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed create album\"}");
                }
            } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    void delete(HttpServletRequest request,HttpServletResponse response , DaoFacade facade) {
 String idString = request.getParameter(PARAMETER_ID);
            long id = Long.parseLong(idString);
            boolean deleted = facade.getAlbumDao().delete(id);
            try (PrintWriter out = response.getWriter()) {
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed delete album\"}");
                }
            } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    void update(HttpServletRequest request,HttpServletResponse response , DaoFacade facade) {
  String idString = request.getParameter(PARAMETER_ID);
            String nameString = request.getParameter(PARAMETER_NAME);
            String yearString = request.getParameter(PARAMETER_YEAR);
            int year = Integer.parseInt(yearString);
            long id = Long.parseLong(idString);
            Album album = new Album(id, nameString, year);
            boolean updated = facade.getAlbumDao().update(album);
            try (PrintWriter out = response.getWriter()) {
                if (updated) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Album updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Failed update album\"}");
                }
            } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    }

    void getAll(HttpServletRequest request,HttpServletResponse response , DaoFacade facade) {
  List<Album> albums = facade.getAlbumDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(albums, out);
            } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            response.setStatus(HttpServletResponse.SC_OK);
    }

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    
    

    
   
}
