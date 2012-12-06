package com.wordwise.client;

import org.restlet.resource.ClientResource;

public class FirstRestClient
{
	public String get() throws Exception
	{
		return new ClientResource("http://138.246.70.105:8080/WordWiseServer/hello").get().getText();  
	}

}
