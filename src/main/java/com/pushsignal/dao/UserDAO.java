package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.User;

public interface UserDAO extends JpaDao<User> {

	User findUserByPrimaryKey(long userId) throws DataAccessException;

	User findUserByEmail(String email) throws DataAccessException;

	Set<User> findUserByName(String name) throws DataAccessException;

	Set<User> findUserByName(String name, int startResult, int maxRows) throws DataAccessException;

	Set<User> findUserByNameContaining(String name_1) throws DataAccessException;

	Set<User> findUserByNameContaining(String name_1, int startResult, int maxRows) throws DataAccessException;

	Set<User> findAllUsers() throws DataAccessException;

	Set<User> findAllUsers(int startResult, int maxRows) throws DataAccessException;
	
	long getPoints(long userId) throws DataAccessException;
}