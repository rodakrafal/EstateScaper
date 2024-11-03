package com.EstateCrawler.app.estates;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
  T get(String id) throws SQLException;

  List<T> getAll() throws SQLException;

  String save(T t) throws SQLException;

  String insert(T t) throws SQLException;

  String update(T t) throws SQLException;

  String delete(String id) throws SQLException;
}
