/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oa.tp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Album;
import org.oa.tp.data.Audio;

@WebServlet(name = "AudioViewServlet", urlPatterns = {"/audio-view"})

public class AudioViewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DaoFacade facade = new DaoFacade(getServletContext());
        
        Audio a = new Audio(1, "Name", 2, 344, 10000, 3);
        facade.getAudioDao().add(a);
        List<Audio> audios = facade.getAudioDao().loadAll();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Audios</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Audios</h1>");
            out.println("<table border=\"1\" style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>NAME</th>");
            out.println("<th>DURATION</th>");
            out.println("<th>PRICE</th>");
            out.println("<th>AUTHOR</th>");
            out.println("<th>GENRE</th>");
            out.println("<th>DELETE</th>");
            out.println("</tr>");
            for (Audio audio : audios) {
                out.println("<tr>");
                out.println("<td>" + audio.getId() + "</td>");
                out.println("<td>" + audio.getName() + "</td>");
                out.println("<td>" + audio.getDuration() + "</td>");
                out.println("<td>" + audio.getPrice() + "</td>");
                out.println("<td>" + audio.getAuthor() + "</td>");
                out.println("<td>" + audio.getGenre()+ "</td>");
                out.println("<td><a href=\"albums?method=delete&id=" + audio.getId() + "\">Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"create_album.html\">Create</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
        

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
