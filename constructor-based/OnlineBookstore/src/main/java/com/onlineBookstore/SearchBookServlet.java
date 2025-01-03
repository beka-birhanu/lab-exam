package com.onlineBookstore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/searchBooks")
public class SearchBookServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // IDBConnectionManager will be injected via Spring context
  private IDBConnectionManager dbManager;

  @Override
  public void init() throws ServletException {
    // Initialize Spring ApplicationContext to manage DBConnectionManager
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    dbManager = context.getBean(DBConnectionManager.class);
    ((AnnotationConfigApplicationContext) context).close();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Prepare response writer to send feedback to the client
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String bookTitle = request.getParameter("title");

    try {
      // Open database connection
      Connection conn = dbManager.getConnection();

      // Prepare the SQL query to delete the book
      String query = "SELECT * FROM books WHERE title LIKE ?";
      PreparedStatement stmt = conn.prepareStatement(query);

      // Set query parameter
      stmt.setString(1, "%" + bookTitle + "%");

      // Execute the query
      ResultSet rs = stmt.executeQuery();

      // Start generating the HTML response
      out.println("<html>");
      out.println("<head><title>Search Results</title></head>");
      out.println("<body>");
      out.println("<h2>Search Results for: \"" + (bookTitle == null ? "All Books" : bookTitle) + "\"</h2>");

      // Check if there are results
      if (!rs.isBeforeFirst()) { // No data in ResultSet
        out.println("<p>No book found matching the search query.</p>");
      } else {
        // Generate an HTML table for the results
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

        out.println("</table>");
      }

      out.println("</body>");
      out.println("</html>");

      // Close the result set and statement
      rs.close();
      stmt.close();
    } catch (Exception e) {
      // Handle exceptions and respond with an error message
      e.printStackTrace();
      out.println("<h2>An error occurred while searching for books.</h2>");
    }
  }
}
