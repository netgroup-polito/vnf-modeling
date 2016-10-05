package it.polito.vnfchainverification.server;

import java.io.FileNotFoundException;

import javax.xml.ws.Endpoint;
import javax.xml.ws.http.HTTPBinding;

import it.polito.vnfchainverification.VNFChainVerificationImpl;
import it.polito.vnfchainverification.XmlFileProvider;

public class VNFChainVerificationServer {

	public static void main(String[] args) {
		Endpoint e;
		String xsdFilename = "generated/classes/META-INF/checkIsolationProperty.xsd";
		String xsdURL = "http://localhost:8081/WebServiceSample/checkIsolationProperty.xsd";
		try {
			e = Endpoint.create( HTTPBinding.HTTP_BINDING,
			        	 new XmlFileProvider(xsdFilename));
			e.publish(xsdURL);
		} catch (FileNotFoundException e1) {
			System.err.println("Unable to open xsd file");
			e1.printStackTrace();
		}
		
		Endpoint.publish(
				"http://localhost:8081/WebServiceSample/VNFChainVerificationService",
				new VNFChainVerificationImpl());
		System.out.println("Server running");
	}
}
