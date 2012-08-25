package com.pushsignal.logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pushsignal.dao.ConfigC2dmDAO;
import com.pushsignal.domain.ConfigC2dm;
import com.pushsignal.http.HttpExecutor;
import com.pushsignal.http.HttpResponse;

@Scope("singleton")
@Service
public class ConfigC2dmLogic {
	private static final String CLIENTLOGIN_POST_URL = "https://www.google.com/accounts/ClientLogin";
	private static final String C2DM_SENDER = "gdenning@gmail.com";

	private static final Logger LOG = Logger.getLogger("com.pushsignal.logic.ConfigC2dmLogic");	

	private ConfigC2dm configCache;
	
	@Autowired
	private ConfigC2dmDAO configC2dmDAO;
	
	@Autowired
	private PasswordEncryptor passwordEncryptor;
	
	public String getSenderUsername() {
		return C2DM_SENDER;
	}

	public String getSenderPassword() {
		ConfigC2dm config = getConfig();
		// TODO: Decrypt password.
		return config.getPassword();
	}

	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public String getAuthToken() {
		ConfigC2dm config = getConfig();
		if (config.getAuthToken() == null) {
			LOG.info("Requesting new AuthToken from Google...");
			Map<String, String> params = new HashMap<String, String>();
			params.put("accountType", "GOOGLE");
			params.put("Email", getSenderUsername());
			params.put("Passwd", getSenderPassword());
			params.put("service", "ac2dm");
			params.put("source", "EngineOne-PushSignalServer-1.0");
			
			HttpResponse httpResponse = HttpExecutor.executePost(CLIENTLOGIN_POST_URL, null, params);
			
			if (httpResponse.getStatusCode() == HttpServletResponse.SC_OK) {
				config.setAuthToken(httpResponse.getResponseAsKeyValuePairs().get("Auth"));
				configC2dmDAO.store(config);				
				LOG.info("AuthToken retrieved successfully.");
			} else {
				LOG.error("Error while retrieving AuthToken: HttpStatusCode " + httpResponse.getStatusCode());
			}
		}
		return config.getAuthToken();
	}
	
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public void invalidateAuthToken() {
		ConfigC2dm config = getConfig();
		config.setAuthToken(null);
		configC2dmDAO.store(config);
	}
	
	private ConfigC2dm getConfig() {
		if (configCache == null) {
			configCache = configC2dmDAO.find(ConfigC2dm.class, 1L);
		}
		return configCache;
	}
}
