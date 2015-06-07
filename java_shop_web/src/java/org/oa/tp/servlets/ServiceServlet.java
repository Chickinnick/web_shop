package org.oa.tp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.oa.tp.dao.DaoFacade;

public abstract class ServiceServlet extends HttpServlet {

    private static final String PARAMETER_METHOD = "method";
    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";

   
    abstract void create(HttpServletRequest request, HttpServletResponse response, DaoFacade facade);
    abstract void delete(HttpServletRequest request,HttpServletResponse response , DaoFacade facade );
    abstract void update(HttpServletRequest request,HttpServletResponse response , DaoFacade facade );
    abstract void getAll(HttpServletRequest request,HttpServletResponse response , DaoFacade facade );
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryMethod = request.getParameter(PARAMETER_METHOD);

        System.out.println("method " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade(getServletContext());
        if (GET_ALL_METHOD.equalsIgnoreCase(queryMethod)) {
          this.getAll(request, response, facade);
        } else if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
            this.create(request, response, facade);
        } else if (UPDATE_METHOD.equalsIgnoreCase(queryMethod)) {
          this.update(request, response, facade);
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
           this.delete(request, response, facade);
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
