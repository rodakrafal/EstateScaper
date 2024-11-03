package com.EstateCrawler.app.estates;

import com.EstateCrawler.app.database.DataBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EstateManager implements EstateDAO {

  @Override
  public Estate get(String id) throws SQLException {
    Connection connection = DataBase.getConnection();

    return new Estate();
  }

  @Override
  public List<Estate> getAll() throws SQLException {
    return List.of();
  }

  @Override
  public String save(Estate estate) throws SQLException {
    return "";
  }

  @Override
  public String insert(Estate estate) throws SQLException {
    return "";
  }

  @Override
  public String update(Estate estate) throws SQLException {
    return "";
  }

  @Override
  public String delete(String id) throws SQLException {
    return "";
  }
}
