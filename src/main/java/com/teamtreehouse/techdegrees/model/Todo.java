package com.teamtreehouse.techdegrees.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Todo {

  private Long id;

  @SerializedName("name")
  private String name;

  @SerializedName("completed")
  private boolean isCompleted;

  public Todo (String name, boolean isCompleted) {
    this.name = name;
    this.isCompleted = isCompleted;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "name='" + name + '\'' +
        ", completed=" + isCompleted +
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

  public Boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }
}
