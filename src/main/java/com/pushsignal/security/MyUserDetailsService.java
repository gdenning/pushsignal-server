package com.pushsignal.security;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pushsignal.dao.UserDAO;
import com.pushsignal.domain.User;

public class MyUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(MyUserDetailsService.class);

	@Autowired
	private UserDAO userDAO;

	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException, DataAccessException {
		LOG.debug("Authenticating " + username + "...");
		final UserDetails userDetails = loadFromDB(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("No user with email address '"
					+ username + "' exists.");
		}
		return userDetails;
	}

	private UserDetails loadFromDB(final String username) {
		final User user = userDAO.findUserByEmail(username);
		if (user == null) {
			return null;
		}

		final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		final GrantedAuthority grantedAuthority = new GrantedAuthorityImpl("ROLE_USER");
		grantedAuthorities.add(grantedAuthority);

		final UserDetails userDetails =
			new org.springframework.security.core.userdetails.User(
					user.getEmail(), user.getPassword(),
					true, true, true, true, grantedAuthorities);

		return userDetails;
	}

}
