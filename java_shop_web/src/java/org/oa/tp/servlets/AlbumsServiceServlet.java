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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Album;

/**
 *
 * @author nikolay
 */
public class AlbumsServiceServlet extends HttpServlet {

    private static final String PARAMETER_METHOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryMethod = request.getParameter(PARAMETER_METHOD);

        System.out.println("method " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade(getServletContext());
        if (GET_ALL_METHOD.equalsIgnoreCase(queryMethod)) {
            List<Album> albums = facade.getAlbumDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(albums, out);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
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
            }
        } else if (UPDATE_METHOD.equalsIgnoreCase(queryMethod)) {
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
            }
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
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
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Not found method\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryString = request.getQueryString();
        System.out.println("query string " + queryString);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
