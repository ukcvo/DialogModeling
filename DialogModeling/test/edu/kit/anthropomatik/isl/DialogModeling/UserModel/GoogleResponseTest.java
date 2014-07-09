package edu.kit.anthropomatik.isl.DialogModeling.UserModel;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.GoogleResponseDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GoogleResponseTest {

	@Test
	public void testDeserialize() throws Exception {
		String json = "{'result':[{'alternative':[{'transcript':'hi there how are you today','confidence':0.95671105},{'transcript':'are there how are you today'},{'transcript':'hi there how are you to day'},{'transcript':'hi there how are is a'}],'final':true}],'result_index':0}";
		Gson gson = new GsonBuilder()
						.registerTypeAdapter(GoogleResponse.class, new GoogleResponseDeserializer())
						.create();
		GoogleResponse response = gson.fromJson(json, GoogleResponse.class);
		
		assertNotNull(response);
		assertEquals("hi there how are you today", response.getResponse());
		assertEquals("0.95671105", response.getConfidence());
		
		List<String> other = response.getOtherPossibleResponses();
		
		assertNotNull(other);
		assertEquals(3, other.size());
		assertEquals("hi there how are is a", other.get(2));
		
	}
}
