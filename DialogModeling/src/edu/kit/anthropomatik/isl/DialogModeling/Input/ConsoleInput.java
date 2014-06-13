package edu.kit.anthropomatik.isl.DialogModeling.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleInput implements IInput {

	@Override
	public InputChunk getInput() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String rawString = br.readLine();
			String[] words  = rawString.split(" ");
			InputToken token = InputToken.valueOf(words[0]);
			List<String> params = new ArrayList<String>();
			for (int i = 1; i < words.length; i++) {
				params.add(words[i]);
			}
			return new InputChunk(token, params);
		} catch (IOException e) {
			e.printStackTrace();
			return new InputChunk(InputToken.NONE, new ArrayList<String>());
		}
	}
}
