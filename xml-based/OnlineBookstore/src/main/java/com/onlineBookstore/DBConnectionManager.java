package com.onlineBookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import lombok.Setter;

@Setter
public class DBConnectionManager implements IDBConnectionManager {

  private final String url;
  private final String username;
  private final String password;

  /**
   * Gets a connection from the DataSource.
   * 
   * @return Connection object
   * @throws SQLException if a database access error occurs
   */
  @Override
  public Connection getConnection() throws SQLException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl(this.url);
    dataSource.setUsername(this.username);
    dataSource.setPassword(this.password);
    return dataSource.getConnection();
  }
}
