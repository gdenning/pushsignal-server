package com.pushsignal.xml;

import java.util.Date;
import java.util.LinkedHashSet;

import com.pushsignal.domain.TriggerPermissionEnum;

public class EventInviteSetDTOTest extends XMLCompareTestCase {

	public void testCompareSerialization() throws Exception {
		Date currentDate = new Date();
		String jaxb = serializeWithJaxb(createEventInviteSetDTOForJaxb(currentDate));
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createEventInviteSetDTOForSimpleXML(currentDate));
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.EventInviteSetDTO createEventInviteSetDTOForJaxb(Date currentDate) {
		final com.pushsignal.xml.jaxb.EventInviteSetDTO eventInviteSet = new com.pushsignal.xml.jaxb.EventInviteSetDTO();
		final com.pushsignal.xml.jaxb.UserDTO user = new com.pushsignal.xml.jaxb.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.jaxb.EventDTO event = new com.pushsignal.xml.jaxb.EventDTO();
		event.setEventId(1);
		event.setCreateDate(currentDate);
		event.setName("test");
		event.setDescription("");
		event.setTriggerPermission(TriggerPermissionEnum.ALL_MEMBERS.name());
		event.setOwner(user);
		event.setMembers(new LinkedHashSet<com.pushsignal.xml.jaxb.EventMemberDTO>());
		final com.pushsignal.xml.jaxb.EventInviteDTO eventInvite = new com.pushsignal.xml.jaxb.EventInviteDTO();
		eventInvite.setEventInviteId(1);
		eventInvite.setCreateDate(currentDate);
		eventInvite.setEvent(event);
		eventInvite.setEmail("newuser@whatever.com");
		eventInviteSet.getEventInvites().add(eventInvite);
		return eventInviteSet;
	}
	
	private com.pushsignal.xml.simple.EventInviteSetDTO createEventInviteSetDTOForSimpleXML(Date currentDate) {
		final com.pushsignal.xml.simple.EventInviteSetDTO eventInviteSet = new com.pushsignal.xml.simple.EventInviteSetDTO();
		final com.pushsignal.xml.simple.UserDTO user = new com.pushsignal.xml.simple.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.simple.EventDTO event = new com.pushsignal.xml.simple.EventDTO();
		event.setEventId(1);
		event.setCreatedDateInMilliseconds(currentDate.getTime());
		event.setName("test");
		event.setDescription("");
		event.setTriggerPermission(TriggerPermissionEnum.ALL_MEMBERS.name());
		event.setOwner(user);
		event.setMembers(new LinkedHashSet<com.pushsignal.xml.simple.EventMemberDTO>());
		final com.pushsignal.xml.simple.EventInviteDTO eventInvite = new com.pushsignal.xml.simple.EventInviteDTO();
		eventInvite.setEventInviteId(1);
		eventInvite.setCreatedDateInMilliseconds(currentDate.getTime());
		eventInvite.setEvent(event);
		eventInvite.setEmail("newuser@whatever.com");
		eventInviteSet.getEventInvites().add(eventInvite);
		return eventInviteSet;
	}
}
