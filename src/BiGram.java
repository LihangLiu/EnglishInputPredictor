import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BiGram {
	private Map<String, Integer>     bigramFreqtDict;
	private Map<String, HashSet<String>> predDict;
	
	
	public BiGram() {
		bigramFreqtDict = new HashMap<>();
		predDict = new HashMap<>();
	}
	
	public void addNewBiGram(String a, String b, int freqt) {
		bigramFreqtDict.put(BiGramUnit(a, b), freqt);
	}
	public void addNewPredict(String a, String b) {
		HashSet<String> gts = predDict.get(a);
		if (gts != null) {
			gts.add(b);
		} else {
			gts = new HashSet<>();
			gts.add(b);
			predDict.put(a, gts);
		}
	}
	
	public int getFreqt(String a, String b) {
		String bgunit = BiGramUnit(a, b);
		return (bigramFreqtDict.get(bgunit)) != null ? bigramFreqtDict.get(bgunit) : 0;
	}
	public HashSet<String> getPredict(String a) {
		return predDict.get(a);
	}
	
	public void loadBiGramFreqtFile(String bigramFile) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File f = new File(bigramFile);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		while ((line=reader.readLine()) != null) {
			String[] tmp = line.split(" ");  		// line = "thank you : 73569"
			String a = tmp[0];
			String b = tmp[1];
			int freqt = Integer.parseInt(tmp[3]);
			addNewBiGram(a, b, freqt);
			addNewPredict(a, b);
		}
		reader.close();
	}
	public static String BiGramUnit(String a, String b) {
		return a+"_"+b;
	}
}


