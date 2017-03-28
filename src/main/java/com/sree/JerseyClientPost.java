package com.sree;

import java.io.BufferedReader;
import java.io.FileReader;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientPost {

  private static String[] split;

public static void main(String[] args) {

	try {

		Client client = Client.create();

		

		String input = "{\"id\":1,\"name\":\"Rachana Capital\",\"prices\":[{\"price\":13.77,\"color\":\"green\",\"date\":\"0926\",\"prevPrice\":13.12},{\"price\":13.12,\"color\":\"green\",\"date\":\"0925\",\"prevPrice\":10.0}]}";
		
		BufferedReader br = new BufferedReader(new FileReader("D:\\mybusiness\\allshares"));
		StringBuilder text = new StringBuilder();
		String line = br.readLine();
		while(line!= null){
			text.append(line);
			line=br.readLine();
		}
		split = text.toString().split("\"id\"");
		/*ClientResponse response = webResource.type("application/json")
		   .post(ClientResponse.class, input);*/
		ClientResponse response = null;
		for (int i = 1; i < split.length; i++) {
			input = "{\"id\""+split[i];
			WebResource webResource = client
					   .resource("http://localhost:9200/tradedetails/trade/"+split[i].substring(1,split[i].indexOf(",")));
			 response = webResource.type("application/json")
					   .post(ClientResponse.class, input.substring(0, input.length()-2));
			 System.out.println(i+"  "+response.getClientResponseStatus().name());
		}
		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);

	  } catch (Exception e) {

		e.printStackTrace();

	  }

	}
}