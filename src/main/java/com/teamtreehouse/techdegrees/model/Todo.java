package com.teamtreehouse.techdegrees.model;

import java.util.Objects;

public class Todo {

  private int id;
  private String name;

  public Todo (String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Todo todo = (Todo) o;
    return id == todo.id &&
        Objects.equals(name, todo.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
