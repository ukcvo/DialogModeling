package edu.kit.anthropomatik.isl.DialogModeling.Output;

public class ConsoleOutput implements IOutput {

	@Override
	public void outputSentence(String sentence) {
		System.out.println(sentence);
	}

}
