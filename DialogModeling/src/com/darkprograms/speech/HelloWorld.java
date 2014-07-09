package com.darkprograms.speech;

import java.io.File;

import javaFlacEncoder.FLACFileWriter;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;

/**
 * Jarvis Speech API Tutorial
 * 
 * @author Aaron Gokaslan (Skylion)
 * 
 */
public class HelloWorld {

	public static void main(String[] args) {
		String recognizedString="";
		
		try {
			recognizedString= Recognizer.recognize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Google Response: "+recognizedString);
	}

}
