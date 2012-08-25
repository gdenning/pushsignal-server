package com.pushsignal.xml;


public class ErrorResultDTOTest extends XMLCompareTestCase {
	public void testCompareSerialization() throws Exception {
		String jaxb = serializeWithJaxb(createErrorResultForJaxb());
		System.out.println(jaxb);
		String simpleXml = serializeWithSimpleXML(createErrorResultForSimpleXML());
		System.out.println(simpleXml);
		assertEquals(jaxb, simpleXml);
	}
	
	private com.pushsignal.xml.jaxb.ErrorResultDTO createErrorResultForJaxb() {
		final com.pushsignal.xml.jaxb.ErrorResultDTO errorResultDTO = new com.pushsignal.xml.jaxb.ErrorResultDTO();
		errorResultDTO.setDescription("test");
		return errorResultDTO;
	}
	
	private com.pushsignal.xml.simple.ErrorResultDTO createErrorResultForSimpleXML() {
		final com.pushsignal.xml.simple.ErrorResultDTO errorResultDTO = new com.pushsignal.xml.simple.ErrorResultDTO();
		errorResultDTO.setDescription("test");
		return errorResultDTO;
	}
}
