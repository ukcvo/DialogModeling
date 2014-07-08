/**
 * 
 */
package com.darkprograms.speech.recognizer;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GoogleResponseDeserializer implements JsonDeserializer<GoogleResponse> {

	public GoogleResponse deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		GoogleResponse response = new GoogleResponse();
		
		JsonObject root = json.getAsJsonObject();
        JsonArray resultsArray = root.get("result").getAsJsonArray();
        for (JsonElement resultsElement : resultsArray) {
        	JsonArray alternativesArray = resultsElement.getAsJsonObject().getAsJsonArray("alternative");
        	boolean first = true;
        	for (JsonElement alternativesElement : alternativesArray) {
        		JsonObject alternativesObject = alternativesElement.getAsJsonObject();
				String transcript = alternativesObject.get("transcript").getAsString();
        		if (!first) {
        			response.addOtherPossibleResponse(transcript);
        			continue;
        		}
        		
        		response.setResponse(transcript);
        		
        		if (alternativesObject.has("confidence")) {
        			String confidence = alternativesObject.get("confidence").getAsString();
        			response.setConfidence(confidence);
        		}
        		
        		first = false;
			}
		}
		
		return response;
	}
}