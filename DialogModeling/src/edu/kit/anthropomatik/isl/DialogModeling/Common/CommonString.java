package edu.kit.anthropomatik.isl.DialogModeling.Common;

import java.util.List;

public class CommonString {
	public static Boolean isIn(String sentence, String substring){
		return sentence.toLowerCase().contains(substring.toLowerCase());
	}
	
	public static Boolean isIn(String sentence, List<String> substrings){
		for (int i = 0; i < substrings.size(); i++) {
            if (isIn(sentence, substrings.get(i))){
            	return true;
            }
        }
		return false;
	}
	
	public static Boolean isIn(List<String> sentence, List<String> substrings){
		for (int i = 0; i < substrings.size(); i++) {
			for (int j = 0; j < sentence.size(); j++) {
				if (isIn(sentence.get(j), substrings.get(i))){
	            	return true;
	            }
			}
        }
		return false;
	}
	
	public static Boolean isIn(List<String> sentence, String substring){
		
		for (int j = 0; j < sentence.size(); j++) {
			if (isIn(sentence.get(j), substring)){
            	return true;
            }
		}
        
		return false;
	}
}
