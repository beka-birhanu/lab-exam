package com.onlineBookstore;

// Beka Birhanu Atomas UGR/3402/14

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/mydatabase");
    dataSource.setUsername("root");
    dataSource.setPassword("rootpassword");
    return dataSource;
  }

  @Bean
  public DBConnectionManager dbConnectionManager(DataSource dataSource) {
    return new DBConnectionManager(dataSource);
  }
}
