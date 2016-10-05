package it.polito.vnfchainverification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;

@WebServiceProvider
@ServiceMode(value=Service.Mode.PAYLOAD)
public class XmlFileProvider implements Provider<Source> {
	File file;
	
	public XmlFileProvider(String filename) throws FileNotFoundException {
		super();
		this.file = new File(filename);
		if (!file.canRead())
			throw new FileNotFoundException();
	}

	public Source invoke(Source source) {
		Source reply;
		try {
			reply = new StreamSource(new FileInputStream(file));
	        return reply;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new WebServiceException(e);
		}
     }
}
