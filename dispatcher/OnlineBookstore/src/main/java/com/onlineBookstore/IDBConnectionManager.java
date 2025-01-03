package com.onlineBookstore;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBConnectionManager {
  Connection getConnection() throws SQLException;
}
