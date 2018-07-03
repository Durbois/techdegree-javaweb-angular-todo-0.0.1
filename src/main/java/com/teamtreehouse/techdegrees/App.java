package com.teamtreehouse.techdegrees;


import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import com.google.gson.Gson;

import com.teamtreehouse.techdegrees.Exception.ApiError;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDaoImpl;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String datasource = "jdbc:h2:~/todos.db";
        if(args.length > 0) {
            if(args.length != 2){
                System.out.println("Java Api <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }

        Sql2o sql2o = new Sql2o(
            String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource), "", "");
        TodoDao todoDao = new TodoDaoImpl(sql2o);
        Gson gson = new Gson();


        get("/blah", (req, res) -> "Hello!");

        get("/api/v1/todos", (req, res) -> todoDao.findAll(), gson::toJson);

        post("/api/v1/todos", "application/json", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);

            System.out.println("TODO: " + todo);

            todoDao.add(todo);
            res.status(201);
            return todo;
        }, gson::toJson);

        // TODO: continue
        put("/api/v1/todos/:id", "application/json", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));

            Todo todo = gson.fromJson(req.body(), Todo.class);
            todo.setId(id);

            todoDao.update(todo);

            res.status(201);
            return todo;
        }, gson::toJson);

        delete("/api/v1/todos/:id", "application/json", (req, res) -> {
            Long id = Long.parseLong(req.params("id"));

            todoDao.delete(id);

            res.status(201);
            return "";
        }, gson::toJson);

        exception(ApiError.class, (exc, req, res) -> {
            ApiError err = (ApiError) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) -> {
            res.type("application/json");
        });

    }
}
