package com.darkprograms.speech;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.darkprograms.speech.synthesizer.Synthesizer;

public class SayHelloWorld {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws IOException, JavaLayerException {
	Synthesizer.synthesize("hello how are you?");
	}
}
