import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TriGram {
	private Map<String, Integer>     trigramFreqtDict;     // {"i_o_u": 100}
	private Map<String, HashSet<String>> predDict;  		// {"i_o": ("u","me",...)
	
	
	public TriGram() {
		trigramFreqtDict = new HashMap<>();
		predDict = new HashMap<>();
	}
	
	public void addNewTriGram(String a, String b, String c, int freqt) {
		trigramFreqtDict.put(TriGramUnit(a, b, c), freqt);
	}
	public void addNewPredict(String a, String b, String c) {
		String tghead = TriGramHead(a, b);
		HashSet<String> gts = predDict.get(tghead);
		if (gts != null) {
			gts.add(c);
		} else {
			gts = new HashSet<>();
			gts.add(c);
			predDict.put(tghead, gts);
		}
	}
	
	public int getFreqt(String a, String b, String c) {
		String tgunit = TriGramUnit(a, b, c);
		return (trigramFreqtDict.get(tgunit)) != null ? trigramFreqtDict.get(tgunit) : 0;
	}
	public HashSet<String> getPredict(String a, String b) {
		return predDict.get(TriGramHead(a, b));
	}
	
	public void loadTriGramFreqtFile(String trigramFile) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File f = new File(trigramFile);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		while ((line=reader.readLine()) != null) {
			String[] tmp = line.split(" ");  		// line = "i o u : 73569"
			String a = tmp[0];
			String b = tmp[1];
			String c = tmp[2];
			int freqt = Integer.parseInt(tmp[4]);
			addNewTriGram(a, b, c, freqt);
			addNewPredict(a, b, c);
		}
		reader.close();
	}
	public static String TriGramUnit(String a, String b, String c) {
		return a+"_"+b+"_"+c;
	}
	public static String TriGramHead(String a, String b) {
		return a+"_"+b;
	}
}
