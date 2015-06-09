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
import org.oa.tp.data.Audio;

@WebServlet(name = "AudioServiceServlet", urlPatterns = {"/audios"})

public class AudioServiceServlet extends ServiceServlet {

    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DURATION = "duration";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_AUTHOR_ID = "author_id";
    private static final String PARAMETER_GENRE_ID = "genre_id";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(getServletContext());
            this.create(request, response, facade);
    }

    
    @Override
    void create(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    String nameString = request.getParameter(PARAMETER_NAME);
        String priceString = request.getParameter(PARAMETER_PRICE);
       
        String genreString = request.getParameter(PARAMETER_GENRE_ID);
        String authorString = request.getParameter(PARAMETER_AUTHOR_ID);
        String durationString = request.getParameter(PARAMETER_DURATION);
        int duration = Integer.parseInt(durationString);
        int price = Integer.parseInt(priceString);
        int author = Integer.parseInt(authorString);
        int genre = Integer.parseInt(genreString);

        Audio audio = new Audio(nameString, author, duration, price, genre);
        
        boolean created = facade.getAudioDao().add(audio);
        try (PrintWriter out = response.getWriter()) {
            if (created) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"Audio created\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed create audio\"}");
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
      
        String nameString = request.getParameter(PARAMETER_NAME);
        String priceString = request.getParameter(PARAMETER_PRICE);
        String idString = request.getParameter(PARAMETER_ID);
        String genreString = request.getParameter(PARAMETER_GENRE_ID);
        String authorString = request.getParameter(PARAMETER_AUTHOR_ID);
        String durationString = request.getParameter(PARAMETER_DURATION);
        int duration = Integer.parseInt(durationString);
        int price = Integer.parseInt(priceString);
        long author = Integer.parseInt(authorString);
        long genre = Integer.parseInt(genreString);
        long id = Integer.parseInt(idString);

        Audio audio = new Audio(id, nameString, author, duration, price, genre);
        
        boolean updated = facade.getAudioDao().update(audio);
        try (PrintWriter out = response.getWriter()) {
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"response\":\"Audio updated\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Failed update audio\"}");
            }
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void getAll(HttpServletRequest request, HttpServletResponse response, DaoFacade facade) {
    List<Audio> audios = facade.getAudioDao().loadAll();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            gson.toJson(audios, out);
        } catch (IOException ex) {
            Logger.getLogger(AlbumsServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    
    }

}