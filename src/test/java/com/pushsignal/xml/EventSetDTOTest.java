package com.pushsignal.xml;

import java.util.Date;

import com.pushsignal.domain.TriggerPermissionEnum;

public class EventSetDTOTest extends XMLCompareTestCase {

	public void testCompareSerialization() throws Exception {
		Date currentDate = new Date();
		String jaxb = serializeWithJaxb(createEventSetDTOForJaxb(currentDate));
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createEventSetDTOForSimpleXML(currentDate));
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.EventSetDTO createEventSetDTOForJaxb(Date currentDate) {
		final com.pushsignal.xml.jaxb.EventSetDTO eventSet = new com.pushsignal.xml.jaxb.EventSetDTO();
		final com.pushsignal.xml.jaxb.UserDTO user = new com.pushsignal.xml.jaxb.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.jaxb.EventMemberDTO eventMember = new com.pushsignal.xml.jaxb.EventMemberDTO();
		eventMember.setUser(user);
		final com.pushsignal.xml.jaxb.EventDTO event = new com.pushsignal.xml.jaxb.EventDTO();
		event.setEventId(1);
		event.setCreateDate(currentDate);
		event.setName("test");
		event.setDescription("");
		event.setTriggerPermission(TriggerPermissionEnum.ALL_MEMBERS.name());
		event.setOwner(user);
		event.getMembers().add(eventMember);
		eventSet.getEvents().add(event);
		return eventSet;
	}
	
	private com.pushsignal.xml.simple.EventSetDTO createEventSetDTOForSimpleXML(Date currentDate) {
		final com.pushsignal.xml.simple.EventSetDTO eventSet = new com.pushsignal.xml.simple.EventSetDTO();
		final com.pushsignal.xml.simple.UserDTO user = new com.pushsignal.xml.simple.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.simple.EventMemberDTO eventMember = new com.pushsignal.xml.simple.EventMemberDTO();
		eventMember.setUser(user);
		final com.pushsignal.xml.simple.EventDTO event = new com.pushsignal.xml.simple.EventDTO();
		event.setEventId(1);
		event.setCreatedDateInMilliseconds(currentDate.getTime());
		event.setName("test");
		event.setDescription("");
		event.setTriggerPermission(TriggerPermissionEnum.ALL_MEMBERS.name());
		event.setOwner(user);
		event.getMembers().add(eventMember);
		eventSet.getEvents().add(event);
		return eventSet;
	}
}
