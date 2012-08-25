package com.pushsignal.xml;

import java.util.Date;

public class ActivitySetDTOTest extends XMLCompareTestCase {
	public void testCompareSerialization() throws Exception {
		Date currentDate = new Date();
		String jaxb = serializeWithJaxb(createActivitySetDTOForJaxb(currentDate));
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createActivitySetDTOForSimpleXML(currentDate));
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.ActivitySetDTO createActivitySetDTOForJaxb(Date currentDate) {
		final com.pushsignal.xml.jaxb.ActivitySetDTO activitySet = new com.pushsignal.xml.jaxb.ActivitySetDTO();
		final com.pushsignal.xml.jaxb.ActivityDTO activity = new com.pushsignal.xml.jaxb.ActivityDTO();
		activity.setDescription("description");
		activity.setCreatedDateInMilliseconds(currentDate.getTime());
		activity.setPoints(10);
		activity.setDescription("");
		activitySet.getActivities().add(activity);
		return activitySet;
	}
	
	private com.pushsignal.xml.simple.ActivitySetDTO createActivitySetDTOForSimpleXML(Date currentDate) {
		final com.pushsignal.xml.simple.ActivitySetDTO activitySet = new com.pushsignal.xml.simple.ActivitySetDTO();
		final com.pushsignal.xml.simple.ActivityDTO activity = new com.pushsignal.xml.simple.ActivityDTO();
		activity.setDescription("description");
		activity.setCreatedDateInMilliseconds(currentDate.getTime());
		activity.setPoints(10);
		activity.setDescription("");
		activitySet.getActivities().add(activity);
		return activitySet;
	}

}
