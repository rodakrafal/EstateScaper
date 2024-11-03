package com.EstateCrawler.app.generics;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
  T get(String id) throws SQLException;

  List<T> getAll() throws SQLException;

  void insert(T t) throws SQLException;

  void update(T t) throws SQLException;

  void delete(String id) throws SQLException;
}
