package com.onlineBookstore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/displayBooks")
public class DisplayBooksServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private IDBConnectionManager dbManager;

  @Override
  public void init() throws ServletException {
    // Initialize Spring ApplicationContext to manage DBConnectionManager
    // Load XML configuration file
    ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");

    dbManager = (IDBConnectionManager) context.getBean("dbConnectionManager");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Set response content type
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try (Connection conn = dbManager.getConnection()) {
      // Execute SQL query to retrieve books
      String query = "SELECT * FROM books";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      // Start generating the HTML table
      out.println("<html>");
      out.println("<head><title>Books List</title></head>");
      out.println("<body>");
      out.println("<h2>Books List</h2>");
      out.println("<table border='1' cellspacing='0' cellpadding='5'>");
      out.println("<tr><th>ID</th><th>Author</th><th>Title</th><th>Book</th></tr>");

      // Iterate through the result set and populate the table
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        Double price = rs.getDouble("price");

        out.println("<tr>");
        out.println("<td>" + id + "</td>");
        out.println("<td>" + author + "</td>");
        out.println("<td>" + title + "</td>");
        out.println("<td>" + price + "</td>");
        out.println("</tr>");
      }

      // End the HTML table and document
      out.println("</table>");
      out.println("</body>");
      out.println("</html>");

      // Close the result set and statement
      rs.close();
      stmt.close();
    } catch (Exception e) {
      // Handle exceptions and respond with an error message
      e.printStackTrace();
      out.println("<h2>An error occurred while retrieving the books.</h2>");
    }
  }
}
