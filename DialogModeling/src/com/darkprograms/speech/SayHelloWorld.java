package com.darkprograms.speech;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.darkprograms.speech.synthesiser.Synthesiser;

public class SayHelloWorld {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JavaLayerException 
	 */
	public static void main(String[] args) throws IOException, JavaLayerException {
	//	String language = "en-us";//English (US) language code   //If you want to specify a language use the ISO code for your country. Ex: en-us
	//	Synthesiser synth = new Synthesiser(language);
		Synthesiser synth = new Synthesiser(Synthesiser.LANG_UK_ENGLISH);
		
		InputStream data = synth.getMP3Data("What type of place are you looking for?");
		//TODO Use any Java MP3 Implementation to play back the AudioFile from the InputStream.
		
		Player player = new Player(data);
		player.play();
	}

}
