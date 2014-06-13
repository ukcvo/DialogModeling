package edu.kit.anthropomatik.isl.DialogModeling.Input;

import java.util.List;

public class InputChunk {

	private InputToken inputToken;
	
	private List<String> parameters;

	public InputChunk(InputToken inputToken, List<String> parameters) {
		this.inputToken = inputToken;
		this.parameters = parameters;
	}
	
	public InputToken getInputToken() {
		return inputToken;
	}

	public List<String> getParameters() {
		return parameters;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<" + inputToken + " | ");
		for (String param : parameters) {
			sb.append(param + " ");
		}
		sb.append(">");
		return sb.toString();
	}
}
