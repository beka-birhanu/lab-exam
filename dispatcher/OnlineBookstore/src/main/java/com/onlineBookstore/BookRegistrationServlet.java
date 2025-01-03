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

@WebServlet("/register")
public class BookRegistrationServlet extends HttpServlet {
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Prepare response writer to send feedback to the client
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String title = request.getParameter("title");
    String author = request.getParameter("author");
    String priceString = request.getParameter("price");
    Double price;

    try {
      price = Double.parseDouble(priceString);
    } catch (Exception e) {
      out.println("<h2>Invalid input price must be a double.</h2>");
      return;
    }

    try {
      // Validate input
      if (title == null || title.trim().isEmpty() ||
          author == null || author.trim().isEmpty() ||
          priceString == null || priceString.trim().isEmpty()) {
        out.println("<h2>All fields are required. Please provide valid inputs.</h2>");
        return;
      }

      // Get the database connection from the singleton instance
      Connection conn = dbManager.getConnection();

      // Prepare the SQL query to insert book details
      String query = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(query);

      // Set query parameters
      stmt.setString(1, title);
      stmt.setString(2, author);
      stmt.setDouble(3, price);

      // Execute the query
      int rowsAffected = stmt.executeUpdate();

      // Respond with a success message
      if (rowsAffected > 0) {
        out.println("<h2>Book registered successfully!</h2>");
      } else {
        out.println("<h2>Book registration failed. Please try again.</h2>");
      }

      // Close the statement
      stmt.close();
    } catch (Exception e) {
      // Log the exception and respond with an error message
      e.printStackTrace();
      out.println("<h2>An error occurred while registering the Book. Please try again later.</h2>");
    }
  }
}
