package com.teamtreehouse.techdegrees.api;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;

import com.teamtreehouse.techdegrees.ApiClient;
import com.teamtreehouse.techdegrees.ApiResponse;
import com.teamtreehouse.techdegrees.App;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDaoImpl;
import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;
import spark.Spark;

public class ApiTest {
  public static final String PORT = "4568";
  public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
  private Connection conn;
  private ApiClient client;
  private Gson gson;
  private TodoDao todoDao;

  @BeforeClass
  public static void startServer(){
    String[] args = {PORT, TEST_DATASOURCE};
    App.main(args);
  }

  @AfterClass
  public static void stopServer(){
    Spark.stop();
  }

  @Before
  public void setUp() throws Exception {
    String connectionString = String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", TEST_DATASOURCE);
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    todoDao = new TodoDaoImpl(sql2o);
    // Keep connection open through entire test so that it doesn't wipe out
    conn = sql2o.open();
    client = new ApiClient("http://localhost:" + PORT);
    gson = new Gson();
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void shouldFindAllTodos () throws Exception {
    Todo todo1 = newTestTodo ("todo1");
    Todo todo2 = newTestTodo ("todo2");
    Todo todo3 = newTestTodo ("todo3");

    todoDao.add(todo1);
    todoDao.add(todo2);
    todoDao.add(todo3);

    ApiResponse res = client.request("GET", "/api/v1/todos");
    Todo[] todos = gson.fromJson(res.getBody(), Todo[].class);

    assertEquals(3, todos.length);
  }

  @Test
  public void addingTodoReturnsCreatedStatus() throws Exception {
    Map<String, String> values = new HashMap<>();
    values.put("name", "Todo");
    values.put("completed", "true");
    values.put("edited", "true");

    ApiResponse res = client.request("POST", "/api/v1/todos", gson.toJson(values));
    assertEquals(201, res.getStatus());
  }

  @Test
  public void updateTodoShouldReturnOkStatus () throws Exception {
    Todo todo = newTestTodo ("todo");

    todoDao.add(todo);

    Map<String, String> values = new HashMap<>();
    values.put("name", todo.getName());
    values.put("completed", todo.isCompleted().toString());

    ApiResponse res = client.request("PUT", "/api/v1/todos/"+todo.getId(), gson.toJson(values));
    assertEquals(201, res.getStatus());
  }

  @Test
  public void deleteTodoShouldReturnOkStatus () throws Exception {
    Todo todo = newTestTodo ("todo");

    todoDao.add(todo);

    ApiResponse res = client.request("DELETE", "/api/v1/todos/"+todo.getId());
    assertEquals(201, res.getStatus());
  }

  private Todo newTestTodo(String name) {
    return new Todo(name, true);
  }


}
