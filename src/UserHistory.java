import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserHistory {
	private Map<String, Map<String, Integer>> dict;
		
	public UserHistory() {
		dict = new HashMap<>();
	}
	
	public HashSet<String> getPredict(String last1word) {
		Set<String> values = getValues(last1word);
		if (values!=null) {
			HashSet<String> res = new HashSet<>();
			res.addAll(values);
			return res;
		}
		return null;
	}
	public HashSet<String> getPredict(String last2word, String last1word) {
		Set<String> values = getValues(last2word+" "+last1word);
		if (values!=null) {
			HashSet<String> res = new HashSet<>();
			res.addAll(values);
			return res;
		}
		return null;
	}
	public HashSet<String> getPredict(String last3word, String last2word, String last1word) {
		Set<String> values = getValues(last3word+" "+last2word+" "+last1word);
		if (values!=null) {
			HashSet<String> res = new HashSet<>();
			res.addAll(values);
			return res;
		}
		return null;
	}
	
	public int getFreqt(String last1word, String pred) {
		return getSubValue(last1word, pred);
	}
	public int getFreqt(String last2word, String last1word, String pred) {
		return getSubValue(last2word+" "+last1word, pred);
	}
	public int getFreqt(String last3word, String last2word, String last1word, String pred) {
		return getSubValue(last3word+" "+last2word+" "+last1word, pred);
	}
	
	public void loadFile(String file) throws FileNotFoundException, IOException {
		File f = new File(file);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		while ((line=reader.readLine()) != null) {
//			line = line.toLowerCase().replaceAll("<s>", "-s-");
			line = line.replaceAll("<s>", "-s-");
			String[] tmp = line.split(" ");
			if (tmp.length<2) {
				System.out.println("error: "+line);
				continue;
			}
			String k = tmp[tmp.length-2];
			String v = tmp[tmp.length-1];
			addNew(k, v);
			if (tmp.length>=3) {
				String l2 = tmp[tmp.length-3];
				addNew(l2, k, v);
			}
			if (tmp.length>=4) {
				String l2 = tmp[tmp.length-3];
				String l3 = tmp[tmp.length-4];
				addNew(l3, l2, k, v);
			} 
		}
		reader.close();
	}
	
	private void addNew(String last1word, String pred) {
		add2Dict(last1word, pred);
	}
	private void addNew(String last2word, String last1word, String pred) {
		add2Dict(last2word+" "+last1word, pred);
	}
	private void addNew(String last3word, String last2word, String last1word, String pred) {
		add2Dict(last3word+" "+last2word+" "+last1word, pred);
	}
	
	private void add2Dict(String key, String value) {
		if (dict.containsKey(key)) {
			Map<String, Integer> subdict= dict.get(key);
			if (subdict.containsKey(value)) {
				subdict.put(value, subdict.get(value)+1);
			} else {
				subdict.put(value, 1);
			}
		} else {
			Map<String, Integer> subdict = new HashMap<>();
			subdict.put(value, 1);
			dict.put(key, subdict);
		}
	}
	private Set<String> getValues(String key) {
		if (dict.containsKey(key)) {
			return dict.get(key).keySet();
		} 
		return null;
	}
	private int getSubValue(String key, String value) {
		if (dict.containsKey(key)) {
			if (dict.get(key).containsKey(value)) {
				return dict.get(key).get(value);
			}
		} 
		return 0;
	}
}
