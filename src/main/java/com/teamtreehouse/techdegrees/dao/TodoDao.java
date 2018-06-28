package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.Exception.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {

  void add (Todo todo)  throws DaoException;

  List<Todo> findAll() throws DaoException;

  Todo findById (int id) throws DaoException;

  void update(Todo todo) throws DaoException;

  void delete (int id) throws DaoException;
}
