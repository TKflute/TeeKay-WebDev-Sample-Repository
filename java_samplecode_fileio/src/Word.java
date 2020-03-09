
public class Word implements Comparable <Word> {

	private String word;
	private int usageCnt;
	
	public Word(String word) {
		setWord(word);
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public int getUsageCnt() {
		return usageCnt;
	}

	public void setUsageCnt(int usageCnt) {
		this.usageCnt = usageCnt;
	}
	
	public void incrementUsage() {
		usageCnt++;
	}

	
	@Override
	public int compareTo(Word w) {
		// TODO Auto-generated method stub
		return (w.getUsageCnt() - this.getUsageCnt());
	}
	
	public boolean hasSameWord(String wordString) {
		return this.word.contentEquals(wordString);
	}

}
