package com.pushsignal.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( {
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/applicationContext.xml" })
@Transactional
public class UserLogicIntegrationTest {
	private static final String TEST_EMAIL = "bob@bob.com";
	
	@Autowired
	private UserLogic userLogic;
	
	@Test
	@Rollback(true)
	public void testCreateUser() {
		final User user = userLogic.createUser(TEST_EMAIL, "Bob Barker", "description");
		assertEquals(TEST_EMAIL, user.getEmail());
	}
}
