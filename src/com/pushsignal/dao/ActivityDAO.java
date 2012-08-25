package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.Activity;

public interface ActivityDAO extends JpaDao<Activity>  {

	Activity findActivityByPrimaryKey(long activityId) throws DataAccessException;

	Set<Activity> findActivitiesByUserID(long userID, int maxRows) throws DataAccessException;
}
