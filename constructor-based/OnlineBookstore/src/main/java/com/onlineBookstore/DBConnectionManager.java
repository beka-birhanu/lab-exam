package com.onlineBookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBConnectionManager implements IDBConnectionManager {

  private final DataSource dataSource;

  @Autowired
  public DBConnectionManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Gets a connection from the DataSource.
   * 
   * @return Connection object
   * @throws SQLException if a database access error occurs
   */
  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
