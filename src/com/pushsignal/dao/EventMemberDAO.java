package com.pushsignal.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.pushsignal.domain.EventMember;

public interface EventMemberDAO extends JpaDao<EventMember> {

	EventMember findEventMemberByPrimaryKey(long EventMemberId) throws DataAccessException;

	Set<EventMember> findAllMembers() throws DataAccessException;

	Set<EventMember> findAllMembers(int startResult, int maxRows) throws DataAccessException;
}