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
import org.oa.tp.data.Audio;
import org.oa.tp.data.Author;

@WebServlet(name = "AuthorsViewServlet", urlPatterns = {"/authors-view"})

public class AuthorsViewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         DaoFacade facade = new DaoFacade(getServletContext());
        
        List<Author> authors = facade.getAuthorDao().loadAll();
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Authors</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Authors</h1>");
            out.println("<table border=\"1\" style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>FIRST NAME</th>");
            out.println("<th>LAST NAME</th>");
            out.println("<th>AGE</th>");
            out.println("<th>GENDER</th>");
            out.println("</tr>");
            for (Author author : authors) {
                out.println("<tr>");
                out.println("<td>" + author.getId() + "</td>");
                out.println("<td>" + author.getFirstName()+ "</td>");
                out.println("<td>" + author.getLastName()+ "</td>");
                out.println("<td>" + author.getAge()+ "</td>");
                out.println("<td>" + author.getGender()+ "</td>");
               
                out.println("<td><a href=\"authors?method=delete&id=" + author.getId() + "\">Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"create_author.html\">Create</a>");
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