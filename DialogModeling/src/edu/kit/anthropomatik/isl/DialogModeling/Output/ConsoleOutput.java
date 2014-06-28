package edu.kit.anthropomatik.isl.DialogModeling.Output;

public class ConsoleOutput implements IOutput {

	private static final String OUTPUT_PREFIX = "OUT>>";
	
	@Override
	public void outputSentence(String sentence) {
		System.out.println(OUTPUT_PREFIX + sentence);
	}

}
