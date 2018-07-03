package com.teamtreehouse.techdegrees.dao;

import static org.junit.Assert.*;

import com.teamtreehouse.techdegrees.Exception.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class TodoDaoImplTest {

  private TodoDaoImpl dao;
  private Connection conn;

  @Before
  public void setUp() throws Exception{
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    dao = new TodoDaoImpl(sql2o);
    // Keep connection open through entire test so that it doesn't wipe out
    conn = sql2o.open();
  }

  @After
  public void tearDown() throws Exception{
    conn.close();
  }

  private Todo newTestTodo() {
    return new Todo("Test", false);
  }

  @Test
  public void addingTodoSetsId() throws Exception{
    Todo todo = newTestTodo();
    Long originalTodoId = todo.getId();

    dao.add(todo);

    assertNotEquals(originalTodoId, todo.getId());
  }

  @Test
  public void deletingTodoSetsId() throws Exception{
    Todo todo = newTestTodo();

    dao.add(todo);

    dao.delete(todo.getId());

    dao.findAll().forEach(System.out::println);

    assertEquals(0, dao.findAll().size());
  }

  @Test
  public void updatingTodoSetsId() throws Exception{
    Todo todo = newTestTodo();

    dao.add(todo);

    todo.setName("Test changed");

    dao.update(todo);

    dao.findAll().forEach(todo1 -> {
      assertEquals("Test changed", todo1.getName());
    });
  }

  @Test
  public void addedTodoAreReturnedFromFindAll() throws Exception {
    Todo todo = newTestTodo();

    dao.add(todo);

    assertEquals(1, dao.findAll().size());
  }

  @Test
  public void noTodosReturnEmptyList() throws DaoException {
    assertEquals(0, dao.findAll().size());
  }

  @Test
  public void existingTodosCanBeFoundById() throws DaoException{
    Todo todo = newTestTodo();
    dao.add(todo);
    Todo foundTodo = dao.findById(todo.getId());
    System.out.println(foundTodo);
    System.out.println(todo);
    assertEquals(todo, foundTodo);
  }
}