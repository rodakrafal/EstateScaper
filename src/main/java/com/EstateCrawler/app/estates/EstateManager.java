package com.EstateCrawler.app.estates;

import com.EstateCrawler.app.database.DataBase;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EstateManager implements EstateDAO {

  @Override
  public EstateDTO get(String estateId) {
    return executeWithSession(session -> session.get(EstateDTO.class, estateId));
  }

  @Override
  public List<EstateDTO> getAll() {
    return executeWithSession(
        session -> session.createQuery("from EstateDTO", EstateDTO.class).list());
  }

  public void insertOrUpdate(EstateDTO estate) throws SQLException {
    EstateDTO foundEstate = get(estate.getId());
    if (Objects.isNull(foundEstate)) {
      insert(estate);
    } else if (!foundEstate.equals(estate)) {
      update(estate);
    }
  }

  @Override
  public void insert(EstateDTO estate) throws SQLException {
    executeWithTransaction(session -> session.persist(estate));
  }

  @Override
  public void update(EstateDTO estate) throws SQLException {
    executeWithTransaction(session -> session.merge(estate));
  }

  @Override
  public void delete(String id) throws SQLException {
    executeWithTransaction(
        session -> {
          EstateDTO estate = session.get(EstateDTO.class, id);
          if (estate != null) {
            session.remove(estate);
          }
        });
  }

  private void executeWithTransaction(SessionConsumer consumer) throws SQLException {
    Transaction transaction = null;
    try (Session session = DataBase.getFactory().openSession()) {
      transaction = session.beginTransaction();
      consumer.accept(session);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
      throw new SQLException("Transaction failed has failed:\n", e);
    }
  }

  private <R> R executeWithSession(SessionFunction<R> function) {
    try (Session session = DataBase.getFactory().openSession()) {
      return function.apply(session);
    }
  }

  @FunctionalInterface
  interface SessionFunction<R> {
    R apply(Session session);
  }

  @FunctionalInterface
  interface SessionConsumer {
    void accept(Session session);
  }
}
