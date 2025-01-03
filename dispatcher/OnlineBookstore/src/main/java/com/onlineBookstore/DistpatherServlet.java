package com.onlineBookstore;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DispatcherServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getServletPath();

    switch (action) {
      case "/register":
        registerBook(request, response);
        break;
      case "/deleteBook":
        deleteBook(request, response);
        break;
      default:
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getServletPath();

    switch (action) {
      case "/displayBooks":
        displayBooks(request, response);
        break;
      case "/searchBooks":
        searchBooks(request, response);
        break;
      default:
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }
}
