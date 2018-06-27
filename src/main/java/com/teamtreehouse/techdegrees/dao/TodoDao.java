package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.Exception.DaoException;
import com.teamtreehouse.techdegrees.model.Todos;

import java.util.List;

public interface TodoDao {

  List<Todos> findAll() throws DaoException;

}
