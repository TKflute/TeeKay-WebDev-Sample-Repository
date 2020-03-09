import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.commons.lang3.StringUtils;

public class TextFileAnalyzer {

	private File inputFile;
	private File statsXMLFile;
	private HashMap<String, Word> wordMap;
	
	
	public TextFileAnalyzer(File inputFile) {
		setInputFile(inputFile);
		wordMap = new HashMap<String, Word>();
	}
	
	public TextFileAnalyzer(File inputFile, File statsXMLFile) {
		setInputFile(inputFile);
		setStatsXMLFile(statsXMLFile);
	}
	
	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getStatsXMLFile() {
		return statsXMLFile;
	}

	public void setStatsXMLFile(File statsXMLFile) {
		this.statsXMLFile = statsXMLFile;
	}
	
	//****METHODS****
	public List<Word> processInput(File inputFile) throws FileNotFoundException {
		//read in file, strip line of punctuation and extra spaces, split line, count, cast, add to map
		//put map values into list and sort
		//close connection and return sorted List<Word>
		
		Scanner input = new Scanner(inputFile);
		int lineCount = 0;
		
		while(input.hasNextLine()) {
			lineCount++;
			String line = input.nextLine();	
			if(line.isEmpty()) {
				continue;
			}		
			line = line.replaceAll("[^\\w\\s]", " ").toLowerCase();
			line = StringUtils.normalizeSpace(line);
			
			String[] lineArray = line.split(" ");
		
			for(int i = 0; i < lineArray.length; i++) {
				if(lineCount == 1 && i == 0) {
					Word word = new Word(lineArray[i]);
					word.setUsageCnt(1);	
					wordMap.put(lineArray[i], word);
				}else {	
					if(wordMap.containsKey(lineArray[i])) {
						wordMap.get(lineArray[i]).incrementUsage();	
						continue;
					}else {
						Word word = new Word(lineArray[i]);
						word.setUsageCnt(1);
						wordMap.put(lineArray[i], word);
					}
					
				}
				
			}
		}
		
		Collection<Word> wordCollection = wordMap.values();
		List<Word> wordList = new ArrayList<Word>(wordCollection);
		
		Collections.sort(wordList);
		input.close();

		return wordList;
	}
	
	public void generateStatsFile(List<Word> wordList) throws ParserConfigurationException,
				TransformerConfigurationException, TransformerException
	{
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
		// root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("text_analyzer");
		doc.appendChild(rootElement);
		
		for(Word word : wordList) {	
			// word elements
			Element wordElement = doc.createElement("word");
			rootElement.appendChild(wordElement);
			
			// word_text elements
			Element wordTextElement = doc.createElement("word_text");
			//this is how to put the content of element
			wordTextElement.appendChild(doc.createTextNode(word.getWord()));
			wordElement.appendChild(wordTextElement);
			
			// usage_cnt elements
			Element usageCntElement = doc.createElement("usage_cnt");
			usageCntElement.appendChild(doc.createTextNode(Integer.toString(word.getUsageCnt())));
			wordElement.appendChild(usageCntElement);
		
		}
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();	
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("C:/Users/yourUserName/tmp/lab05Test.xml"));
		transformer.transform(source, result);
			
		System.out.println("File saved!");
		
	}


}
