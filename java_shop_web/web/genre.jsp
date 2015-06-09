
<%@page import="org.oa.tp.data.Genre"%>
<%@page import="java.util.List"%>
<%@page import="org.oa.tp.dao.DaoFacade"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Genres</title>
    </head>
    
    <body>
    <%  DaoFacade daoFacade = new DaoFacade(getServletContext());
        List<Genre> genres = daoFacade.getGenreDao().loadAll();
       %>
        <h1>Hello World!</h1>
        <h1>Genres</h1>
        <tr>
        <th>ID</th>
        <th>NAME</th>
        </tr>
          <%  for (Genre genre : genres) { %>
          <tr>
              <td>  <%= genre.getId() %> </td>
              <td><%= genre.getName() %> </td>
              <td><a href=\"genres?method=delete&id=<%genre.getId();%>"> Delete</a></td>
              
              
          </tr>
          }
           <a href=\"create_genre.html\">Create</a>
           </body>
           </html>
        }
    </body>
</html>
