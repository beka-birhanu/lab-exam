package com.onlineBookstore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // IDBConnectionManager will be injected via Spring context
  private IDBConnectionManager dbManager;

  @Override
  public void init() throws ServletException {
    // Initialize Spring ApplicationContext to manage DBConnectionManager
    // Load XML configuration file
    ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");

    dbManager = (IDBConnectionManager) context.getBean("dbConnectionManager");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Prepare response writer to send feedback to the client
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Retrieve book ID from the request
    String bookIdString = request.getParameter("bookId");
    int bookId;
    try {
      bookId = Integer.parseInt(bookIdString);
    } catch (Exception e) {
      out.println("<h2>Invalid input id must be a double.</h2>");
      return;
    }

    try {
      // Open database connection
      Connection conn = dbManager.getConnection();

      // Prepare the SQL query to delete the book
      String query = "DELETE FROM books WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(query);

      // Set query parameter
      stmt.setInt(1, bookId);

      // Execute the query
      int rowsAffected = stmt.executeUpdate();

      // Respond with a success message
      if (rowsAffected > 0) {
        out.println("<h2>Book with ID " + bookIdString + " deleted successfully!</h2>");
      } else {
        out.println("<h2>No book found with ID " + bookIdString + ".</h2>");
      }

      // Close the statement
      stmt.close();
    } catch (Exception e) {
      // Handle exceptions and respond with an error message
      e.printStackTrace();
      out.println("<h2>An error occurred while deleting the book.</h2>");
    }
  }
}
