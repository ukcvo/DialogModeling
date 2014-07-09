package com.darkprograms.speech.recognizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javaFlacEncoder.FLACFileWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.darkprograms.speech.microphone.Microphone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/***************************************************************
 * Class that submits FLAC audio and retrieves recognized text
 * 
 * @author Luke Kuza, Duncan Jauncey, Aaron Gokaslan
 **************************************************************/
public class Recognizer {

	public enum Languages {
		AUTO_DETECT("auto"), // tells Google to auto-detect the language
		ARABIC_JORDAN("ar-JO"), 
		ARABIC_LEBANON("ar-LB"), 
		ARABIC_QATAR("ar-QA"), 
		ARABIC_UAE("ar-AE"), 
		ARABIC_MOROCCO("ar-MA"), 
		ARABIC_IRAQ("ar-IQ"), 
		ARABIC_ALGERIA("ar-DZ"), 
		ARABIC_BAHRAIN("ar-BH"), 
		ARABIC_LYBIA("ar-LY"), 
		ARABIC_OMAN("ar-OM"), 
		ARABIC_SAUDI_ARABIA("ar-SA"), 
		ARABIC_TUNISIA("ar-TN"), 
		ARABIC_YEMEN("ar-YE"), 
		BASQUE("eu"), 
		CATALAN("ca"), 
		CZECH("cs"), 
		DUTCH("nl-NL"), 
		ENGLISH_AUSTRALIA("en-AU"), 
		ENGLISH_CANADA("en-CA"), 
		ENGLISH_INDIA("en-IN"), 
		ENGLISH_NEW_ZEALAND("en-NZ"), 
		ENGLISH_SOUTH_AFRICA("en-ZA"), 
		ENGLISH_UK("en-GB"), 
		ENGLISH_US("en-US"), 
		FINNISH("fi"), 
		FRENCH("fr-FR"), 
		GALICIAN("gl"), 
		GERMAN("de-DE"), 
		HEBREW("he"), 
		HUNGARIAN("hu"), 
		ICELANDIC("is"), 
		ITALIAN("it-IT"), 
		INDONESIAN("id"), 
		JAPANESE("ja"), 
		KOREAN("ko"), 
		LATIN("la"), 
		CHINESE_SIMPLIFIED("zh-CN"), 
		CHINESE_TRANDITIONAL("zh-TW"), 
		CHINESE_HONGKONG("zh-HK"), 
		CHINESE_CANTONESE("zh-yue"), 
		MALAYSIAN("ms-MY"), 
		NORWEGIAN("no-NO"), 
		POLISH("pl"), 
		PIG_LATIN("xx-piglatin"), 
		PORTUGUESE("pt-PT"), 
		PORTUGUESE_BRASIL("pt-BR"), 
		ROMANIAN("ro-RO"), 
		RUSSIAN("ru"), 
		SERBIAN("sr-SP"), 
		SLOVAK("sk"), 
		SPANISH_ARGENTINA("es-AR"), 
		SPANISH_BOLIVIA("es-BO"), 
		SPANISH_CHILE("es-CL"), 
		SPANISH_COLOMBIA("es-CO"), 
		SPANISH_COSTA_RICA("es-CR"), 
		SPANISH_DOMINICAN_REPUBLIC("es-DO"), 
		SPANISH_ECUADOR("es-EC"), 
		SPANISH_EL_SALVADOR("es-SV"), 
		SPANISH_GUATEMALA("es-GT"), 
		SPANISH_HONDURAS("es-HN"), 
		SPANISH_MEXICO("es-MX"), 
		SPANISH_NICARAGUA("es-NI"), 
		SPANISH_PANAMA("es-PA"), 
		SPANISH_PARAGUAY("es-PY"), 
		SPANISH_PERU("es-PE"), 
		SPANISH_PUERTO_RICO("es-PR"), 
		SPANISH_SPAIN("es-ES"), 
		SPANISH_US("es-US"), 
		SPANISH_URUGUAY("es-UY"), 
		SPANISH_VENEZUELA("es-VE"), 
		SWEDISH("sv-SE"), 
		TURKISH("tr"), 
		ZULU("zu");

		// TODO Clean Up JavaDoc for Overloaded Methods using @link

		/**
		 *Stores the LanguageCode
		 */
		private final String languageCode;

		/**
		 *Constructor
		 */
		private Languages(final String languageCode) {
			this.languageCode = languageCode;
		}

		public String toString() {
			return languageCode;
		}

	}

	/**
	 * URL to POST audio data and retrieve results
	 */
//	private static final String GOOGLE_RECOGNIZER_URL = "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium"; // v1 doesn't work anymore
	//TODO replace with your key in the following GOOGLE_RECOGNIZER_URL
	private static final String GOOGLE_RECOGNIZER_URL = "https://www.google.com/speech-api/v2/recognize?output=json&key=AIzaSyBitKL0B5M5iEAcoI7uCK_EBYBtJkwdPjc"; // lgzhang

	private boolean profanityFilter = true;
	private String language = null;

	/**
	 * Constructor
	 */
	public Recognizer() {
		this.setLanguage(Languages.AUTO_DETECT);
	}

	/**
	 * Constructor
	 * 
	 * @param Language
	 */
	@Deprecated
	public Recognizer(String language) {
		this.language = language;
	}

	/**
	 * Constructor
	 * 
	 * @param language
	 *            The Languages class for the language you want to designate
	 */
	public Recognizer(Languages language) {
		this.language = language.languageCode;
	}

	/**
	 * Constructor
	 * 
	 * @param profanityFilter
	 */
	public Recognizer(boolean profanityFilter) {
		this.profanityFilter = profanityFilter;
	}

	/**
	 * Constructor
	 * 
	 * @param language
	 * @param profanityFilter
	 */
	@Deprecated
	public Recognizer(String language, boolean profanityFilter) {
		this.language = language;
		this.profanityFilter = profanityFilter;
	}

	/**
	 * Constructor
	 * 
	 * @param language
	 * @param profanityFilter
	 */
	public Recognizer(Languages language, boolean profanityFilter) {
		this.language = language.languageCode;
		this.profanityFilter = profanityFilter;
	}

	/**
	 * Language: Contains all supported languages for Google Speech to Text.
	 * Setting this to null will make Google use it's own language detection.
	 * This value is null by default.
	 * 
	 * @param language
	 */
	public void setLanguage(Languages language) {
		this.language = language.languageCode;
	}

	/**
	 * Language code. This language code must match the language of the speech
	 * to be recognized. ex. en-US ru-RU This value is null by default.
	 * 
	 * @param language
	 *            The language code.
	 */
	@Deprecated
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Returns the state of profanityFilter which enables/disables Google's
	 * profanity filter (on by default).
	 * 
	 * @return profanityFilter
	 */
	public boolean getProfanityFilter() {
		return profanityFilter;
	}

	/**
	 * Language code. This language code must match the language of the speech
	 * to be recognized. ex. en-US ru-RU This value is null by default.
	 * 
	 * @return language the Google language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Get recognized data from a Wave file. This method will encode the wave
	 * file to a FLAC file
	 * 
	 * @param waveFile
	 *            Wave file to recognize
	 * @param maxResults
	 *            Maximum number of results to return in response
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForWave(File waveFile, int maxResults)
			throws IOException {
		return getRecognizedDataForWave(waveFile, maxResults, 8000); // Transcodes
																	// to 8000
																	// automatically
	}
	
	/**
	 * Get recognized data from a Wave file. This method will encode the wave
	 * file to a FLAC file
	 * 
	 * @param waveFile
	 *            Wave file to recognize
	 * @param maxResults
	 *            Maximum number of results to return in response
	 * @param sampleRate
	 * 			  Sample rate of the wave file
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForWave(File waveFile, int maxResults, int sampleRate) throws IOException {
		FlacEncoder flacEncoder = new FlacEncoder();
		File flacFile = new File(waveFile + ".flac");
		
		flacEncoder.convertWaveToFlac(waveFile, flacFile, sampleRate);
		
		String response = rawRequest(flacFile, maxResults, sampleRate);
		
		// Delete converted FLAC data
		flacFile.delete();
		
		GoogleResponse googleResponse = new GoogleResponse();
		parseResponse(response, googleResponse);
		return googleResponse;
	}

	/**
	 * Get recognized data from a Wave file. This method will encode the wave
	 * file to a FLAC
	 * 
	 * @param waveFile
	 *            Wave file to recognize
	 * @param maxResults
	 *            the maximum number of results to return in the response NOTE:
	 *            Sample rate of file must be 8000 unless a custom sample rate
	 *            is specified.
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForWave(String waveFile,
			int maxResults) throws IOException {
		return getRecognizedDataForWave(new File(waveFile), maxResults);
	}

	/**
	 * Get recognized data from a FLAC file.
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @param maxResults
	 *            the maximum number of results to return in the response NOTE:
	 *            Sample rate of file must be 8000 unless a custom sample rate
	 *            is specified.
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(File flacFile, int maxResults)
			throws IOException {
		return getRecognizedDataForFlac(flacFile, maxResults, 8000);
	}

	/**
	 * Get recognized data from a FLAC file.
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @param maxResults
	 *            the maximum number of results to return in the response
	 * @param samepleRate
	 *            The sampleRate of the file. Default is 8000.
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(File flacFile,
			int maxResults, int sampleRate) throws IOException {
		String response = rawRequest(flacFile, maxResults, sampleRate);
		
		Gson gson = new GsonBuilder()
					.registerTypeAdapter(GoogleResponse.class, new GoogleResponseDeserializer())
					.create();
		return gson.fromJson(response, GoogleResponse.class);
	}

	/**
	 * Get recognized data from a FLAC file.
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @param maxResults
	 *            the maximum number of results to return in the response
	 * @param samepleRate
	 *            The sampleRate of the file. Default is 8000.
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(String flacFile,
			int maxResults, int sampleRate) throws IOException {
		return getRecognizedDataForFlac(new File(flacFile), maxResults,	sampleRate);
	}

	/**
	 * Get recognized data from a FLAC file.
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @param maxResults
	 *            the maximum number of results to return in the response
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(String flacFile,
			int maxResults) throws IOException {
		return getRecognizedDataForFlac(new File(flacFile), maxResults);
	}

	/**
	 * Get recognized data from a Wave file. This method will encode the wave
	 * file to a FLAC. This method will automatically set the language to en-US,
	 * or English
	 * 
	 * @param waveFile
	 *            Wave file to recognize
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForWave(File waveFile)
			throws IOException {
		return getRecognizedDataForWave(waveFile, 1);
	}

	/**
	 * Get recognized data from a Wave file. This method will encode the wave
	 * file to a FLAC. This method will automatically set the language to en-US,
	 * or English
	 * 
	 * @param waveFile
	 *            Wave file to recognize
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForWave(String waveFile)
			throws IOException {
		return getRecognizedDataForWave(waveFile, 1);
	}

	/**
	 * Get recognized data from a FLAC file. This method will automatically set
	 * the language to en-US, or English
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(File flacFile)
			throws IOException {
		return getRecognizedDataForFlac(flacFile, 1);
	}

	/**
	 * Get recognized data from a FLAC file. This method will automatically set
	 * the language to en-US, or English
	 * 
	 * @param flacFile
	 *            FLAC file to recognize
	 * @return Returns a GoogleResponse, with the response and confidence score
	 * @throws IOException
	 *             Throws exception if something goes wrong
	 */
	public GoogleResponse getRecognizedDataForFlac(String flacFile)
			throws IOException {
		return getRecognizedDataForFlac(flacFile, 1);
	}
	
	public static String recognize() throws Exception{
		int sampleRate = 44100;
		File file = new File("testfile1.flac"); // Name your file whatever you want
		
		Microphone mic = new Microphone(FLACFileWriter.FLAC, sampleRate);	
		try {
			mic.captureAudioToFile(file);
		} catch (Exception ex) {// Microphone not available or some other error.
			System.out.println("ERROR: Microphone is not available.");
			ex.printStackTrace();
			// TODO Add your error Handling Here
		}
		// User records the voice here. Microphone starts a separate thread so do
		// whatever you want in the mean time. Show a recording icon or whatever.
		try {
			System.out.println("Recording...");
			Thread.sleep(5000);// In our case, we'll just wait 5 seconds.
		//	mic.close();
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		mic.close();// Ends recording and frees the resources
		System.out.println("Recording stopped.");

		Recognizer recognizer = new Recognizer(Recognizer.Languages.ENGLISH_US); //Specify your language here.
		// Although auto-detect is available, it is recommended you select your region for added accuracy.
		try {
			int maxNumOfResponses = 4;
			GoogleResponse response = recognizer.getRecognizedDataForFlac(file, maxNumOfResponses, sampleRate);
			System.out.println("Google Response: " + response.getResponse());
			if (response.getConfidence() != null)
				System.out.println("Google is " + Double.parseDouble(response.getConfidence())*100 + "% confident in" + " the reply");
			String res= response.getResponse();
			if (res==null){
				res="";
			}else{
				res= response.getResponse();
			}
			return res;
		} catch (Exception ex) {
			// TODO Handle how to respond if Google cannot be contacted
			System.out.println("ERROR: Google cannot be contacted");
			ex.printStackTrace();
			throw new Exception("ERROR: Google cannot be contacted");
		}
	}

	/**
	 * Parses the raw response from Google
	 * 
	 * @param rawResponse
	 *            The raw, unparsed response from Google
	 * @return Returns the parsed response in the form of a Google Response.
	 */
	private void parseResponse(String rawResponse, GoogleResponse googleResponse) {
		if (rawResponse == null || !rawResponse.contains("utterance"))
			return;

		String array = substringBetween(rawResponse, "[", "]");
		String[] parts = array.split("}");

		boolean first = true;
		for (String s : parts) {
			if (first) {
				first = false;
				String utterancePart = s.split(",")[0];
				String confidencePart = s.split(",")[1];

				String utterance = utterancePart.split(":")[1];
				String confidence = confidencePart.split(":")[1];

				utterance = stripQuotes(utterance);
				confidence = stripQuotes(confidence);

				if (utterance.equals("null")) {
					utterance = null;
				}
				if (confidence.equals("null")) {
					confidence = null;
				}

				googleResponse.setResponse(utterance);
				googleResponse.setConfidence(confidence);
			} else {
				String utterance = s.split(":")[1];
				utterance = stripQuotes(utterance);
				if (utterance.equals("null")) {
					utterance = null;
				}
				googleResponse.getOtherPossibleResponses().add(utterance);
			}
		}
	}

	/**
	 * Performs the request to Google with a file <br>
	 * Request is buffered
	 * 
	 * @param inputFile
	 *            Input files to recognize
	 * @return Returns the raw, unparsed response from Google
	 * @throws IOException
	 *             Throws exception if something went wrong
	 */
	private String rawRequest(File inputFile, int maxResults, int sampleRate)
			throws IOException {
		BufferedReader br;

		StringBuilder sb = new StringBuilder(GOOGLE_RECOGNIZER_URL);
		if (language != null) {
			sb.append("&lang=");
			sb.append(language);
		} else {
			sb.append("&lang=auto");
		}
		if (!profanityFilter) {
			sb.append("&pfilter=0");
		}
		sb.append("&maxresults=");
		sb.append(maxResults);
		
		PostMethod req = new PostMethod(sb.toString());
		req.setRequestHeader("Content-Type", "audio/x-flac; rate=" + sampleRate);
		
		FileRequestEntity entity = new FileRequestEntity(inputFile, "audio/x-flac; rate=" + sampleRate);
		req.setRequestEntity(entity);
		
		HttpClient client = new HttpClient();
		client.getParams().setParameter(
			    HttpMethodParams.USER_AGENT,
			    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36"
			);
		
		int status = client.executeMethod(req);
		System.out.println("Status: " + status);
		
		long len = req.getResponseContentLength();
		
		if (len > 0) {
			return req.getResponseBodyAsString(); 
		}
		
		InputStream is = req.getResponseBodyAsStream();
		
		List<String> lines = new ArrayList<String>();
		String reply = null;
		br = new BufferedReader(new InputStreamReader(is/*, req.getResponseCharSet()*/));
		
		while ((reply = br.readLine()) != null) {
            if (!reply.equals("")) lines.add(reply);
        }
		
		System.out.println("Chunked response: " + lines);
		System.out.println("Taking only the last line.");
		
		return lines.get(lines.size() - 1);
	}

	private String substringBetween(String s, String part1, String part2) {
		String sub = null;

		int i = s.indexOf(part1);
		int j = s.indexOf(part2, i + part1.length());

		if (i != -1 && j != -1) {
			int nStart = i + part1.length();
			sub = s.substring(nStart, j);
		}

		return sub;
	}

	private String stripQuotes(String s) {
		int start = 0;
		if (s.startsWith("\"")) {
			start = 1;
		}
		int end = s.length();
		if (s.endsWith("\"")) {
			end = s.length() - 1;
		}
		return s.substring(start, end);
	}

}
