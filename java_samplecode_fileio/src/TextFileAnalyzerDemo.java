import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public class TextFileAnalyzerDemo {

	public static void main(String[] args) {
		
		File file = new File("C:/Users/yourUserName/tmp/shakespeare.txt");

		if(!file.exists()) {
		System.out.println("No such file");
		}else if(!file.canRead()){
			System.out.println("File not readable");
		}else if(!file.canWrite()){
			System.out.println("File not writable");
		}else {
			System.out.println("File exists and is R/W");
		}
		
		System.out.println("The file is " + file.length() + " bytes in size.");
		
		//testing processInput(){}
		
		TextFileAnalyzer analyzer = new TextFileAnalyzer(file);
		List<Word> wordList = null;
		try {
			wordList = analyzer.processInput(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			analyzer.generateStatsFile(wordList);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
				
	}

}
