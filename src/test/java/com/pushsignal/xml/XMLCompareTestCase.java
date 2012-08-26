package com.pushsignal.xml;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public abstract class XMLCompareTestCase extends TestCase {
	private DefaultListableBeanFactory beanFactory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		ClassPathResource res = new ClassPathResource("service-context.xml");
		beanFactory = new XmlBeanFactory(res);
	}

	protected String serializeWithJaxb(final Object input) {
		Jaxb2Marshaller marshaller = (Jaxb2Marshaller) beanFactory.getBean("jaxb2Marshaller");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(input, new StreamResult(baos));
		return stripWhitespace(new String(baos.toByteArray()));
	}

	protected String serializeWithSimpleXML(final Object input) throws Exception {
		Strategy strategy = new AnnotationStrategy();
		Serializer serializer = new Persister(strategy);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		serializer.write(input, baos);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + stripWhitespace(new String(baos.toByteArray()));
	}

	private String stripWhitespace(final String input) {
		String output;
		Pattern whitespace = Pattern.compile("^[ \\t]+|[ \\t]+$", Pattern.CANON_EQ | Pattern.MULTILINE);
		Matcher matcher1 = whitespace.matcher(input);
        output = matcher1.replaceAll("");
		Pattern lineEnding = Pattern.compile("\n|\r\n", Pattern.CANON_EQ | Pattern.MULTILINE);
		Matcher matcher2 = lineEnding.matcher(output);
        return matcher2.replaceAll("");
	}
}
