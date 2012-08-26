package com.pushsignal.xml;

import java.util.Date;

public class UserDeviceDTOTest extends XMLCompareTestCase {
	public void testCompareSerialization() throws Exception {
		Date currentDate = new Date();
		String jaxb = serializeWithJaxb(createUserDeviceDTOForJaxb(currentDate));
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createUserDeviceDTOForSimpleXML(currentDate));
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.UserDeviceDTO createUserDeviceDTOForJaxb(Date currentDate) {
		final com.pushsignal.xml.jaxb.UserDTO user = new com.pushsignal.xml.jaxb.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.jaxb.UserDeviceDTO userDevice = new com.pushsignal.xml.jaxb.UserDeviceDTO();
		userDevice.setUserDeviceId(1);
		userDevice.setDeviceType("C2DM");
		userDevice.setDeviceId("deviceId");
		userDevice.setRegistrationId("registrationId");
		userDevice.setServerMillisecondsSince1970(currentDate.getTime());
		userDevice.setUser(user);
		return userDevice;
	}
	
	private com.pushsignal.xml.simple.UserDeviceDTO createUserDeviceDTOForSimpleXML(Date currentDate) {
		final com.pushsignal.xml.simple.UserDTO user = new com.pushsignal.xml.simple.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.simple.UserDeviceDTO userDevice = new com.pushsignal.xml.simple.UserDeviceDTO();
		userDevice.setUserDeviceId(1);
		userDevice.setDeviceType("C2DM");
		userDevice.setDeviceId("deviceId");
		userDevice.setRegistrationId("registrationId");
		userDevice.setServerMillisecondsSince1970(currentDate.getTime());
		userDevice.setUser(user);
		return userDevice;
	}
}
