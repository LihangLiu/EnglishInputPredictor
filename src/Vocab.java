import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Vocab {
	private Map<String, Integer> vocab;
	public Vocab() {
		vocab = new HashMap<>();
	}
	
	public void addNewWord(String word, int freqt) {
		vocab.put(word, freqt);
	}
	public boolean hasWord(String word) {
		return vocab.containsKey(word);
	}
	public int getFreqt(String a) {
		return (vocab.get(a)!=null) ? (vocab.get(a)) : 0;
	}
	public HashSet<String> getAllWord() {
		HashSet<String> keys = new HashSet<>(vocab.keySet());
		return keys;
	}
	
	public void loadVocabFile(String vocabFile) throws FileNotFoundException, IOException {
		File f = new File(vocabFile);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		while ((line=reader.readLine()) != null) {
			String[] tmp = line.split(" ");  		// line = "i o u : 73569"
			String a = tmp[0];
			int freqt = Integer.parseInt(tmp[1]);
			addNewWord(a, freqt);
		}
		reader.close();
	}
}
