package com.teamtreehouse.techdegrees;


import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        Gson gson = new Gson();


        get("/blah", (req, res) -> "Hello!");

        get("/api/v1/todos", (req, res) -> {
            JSONParser parser = new JSONParser();

            try {
             Object obj = parser.parse(new FileReader("todos.json"));
                JSONArray todosList = (JSONArray) obj;
                Iterator<String> iterator = todosList.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            }  catch (Exception e) {
              e.printStackTrace();
            }
            return null;
        }, gson::toJson);

    }

}
