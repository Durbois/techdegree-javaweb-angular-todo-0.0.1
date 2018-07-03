package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.Exception.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class TodoDaoImpl implements TodoDao {

  private final Sql2o sql2o;

  public TodoDaoImpl(Sql2o sql2o) {
    this.sql2o = sql2o;
  }


  @Override
  public void add(Todo todo) throws DaoException {
    String sql = "INSERT INTO todo(name, is_completed) VALUES (:name, :is_completed)";
    try(Connection con = sql2o.open()){
      Long id = (Long)con.createQuery(sql)
          .addParameter("name", todo.getName())
          .addParameter("is_completed", todo.isCompleted())
          .executeUpdate()
          .getKey();

      todo.setId(id);
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem adding todo");
    }
  }

  @Override
  public void update(Todo todo) throws DaoException {
    String sql = "UPDATE todo SET name = :name, is_completed = :is_completed WHERE id = :id";
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
          .addParameter("name", todo.getName())
          .addParameter("is_completed", todo.isCompleted())
          .addParameter("id", todo.getId())
          .executeUpdate();
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem updating todo");
    }
  }
  
  @Override
  public void delete (Long id) throws DaoException {
    String sql = "DELETE from todo WHERE id = :id";
    try(Connection con = sql2o.open()){
      con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem deleting todo");
    }
  }


  @Override
  public List<Todo> findAll() throws DaoException {
    try(Connection con = sql2o.open()){
      String sql = "SELECT * FROM todo";
      return con.createQuery(sql)
          .addColumnMapping("is_completed", "isCompleted")
          .executeAndFetch(Todo.class);
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem finding all todos");
    }
  }

  @Override
  public Todo findById(Long id) throws DaoException {
    try(Connection con = sql2o.open()){
      String sql = "SELECT * FROM todo WHERE id = :id";
      return con.createQuery(sql)
          .addColumnMapping("is_completed", "isCompleted")
          .addParameter("id", id)
          .executeAndFetchFirst(Todo.class);
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem finding all todos");
    }
  }
}
