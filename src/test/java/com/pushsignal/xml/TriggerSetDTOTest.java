package com.pushsignal.xml;

import java.util.Date;
import java.util.LinkedHashSet;

import com.pushsignal.domain.TriggerPermissionEnum;

public class TriggerSetDTOTest extends XMLCompareTestCase {

	public void testCompareSerialization() throws Exception {
		Date currentDate = new Date();
		String jaxb = serializeWithJaxb(createTriggerSetDTOForJaxb(currentDate));
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createTriggerSetDTOForSimpleXML(currentDate));
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.TriggerSetDTO createTriggerSetDTOForJaxb(Date currentDate) {
		final com.pushsignal.xml.jaxb.TriggerSetDTO triggerSet = new com.pushsignal.xml.jaxb.TriggerSetDTO();
		final com.pushsignal.xml.jaxb.UserDTO user = new com.pushsignal.xml.jaxb.UserDTO();
		user.setUserId(1);
		user.setName("testUser");
		user.setDescription("");
		final com.pushsignal.xml.jaxb.EventDTO event = new com.pushsignal.xml.jaxb.EventDTO();
		event.setEventId(1);
		event.setCreatedDateInMilliseconds(currentDate.getTime());
		event.setName("test");
		event.setDescription("");
		event.setTriggerPermission(TriggerPermissionEnum.ALL_MEMBERS.name());
		event.setOwner(user);
		event.setMembers(new LinkedHashSet<com.pushsignal.xml.jaxb.EventMemberDTO>());
		final com.pushsignal.xml.jaxb.TriggerDTO trigger = new com.pushsignal.xml.jaxb.TriggerDTO();
		trigger.setTriggerId(1);
		trigger.setCreatedDateInMilliseconds(currentDate.getTime());
		trigger.setEvent(event);
		trigger.setUser(user);
		trigger.setMessage("message");
		trigger.setTriggerAlerts(new LinkedHashSet<com.pushsignal.xml.jaxb.TriggerAlertDTO>());
		triggerSet.getTriggers().add(trigger);
		return triggerSet;
	}
	
	private com.pushsignal.xml.simple.TriggerSetDTO createTriggerSetDTOForSimpleXML(Date currentDate) {
		final com.pushsignal.xml.simple.TriggerSetDTO triggerSet = new com.pushsignal.xml.simple.TriggerSetDTO();
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
		final com.pushsignal.xml.simple.TriggerDTO trigger = new com.pushsignal.xml.simple.TriggerDTO();
		trigger.setTriggerId(1);
		trigger.setCreatedDateInMilliseconds(currentDate.getTime());
		trigger.setEvent(event);
		trigger.setUser(user);
		trigger.setMessage("message");
		trigger.setTriggerAlerts(new LinkedHashSet<com.pushsignal.xml.simple.TriggerAlertDTO>());
		triggerSet.getTriggers().add(trigger);
		return triggerSet;
	}
}
