import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

public class KeyBoardDistance {
	private static  String firstLine = "qwertyuiop";
	private static String secondLine = "asdfghjkl";
	private static String thirdLine = "zxcvbnm";
	private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	private static Map<Character, String> dict;
	
	static {
		dict = new HashMap<>();
		for (int i=0;i<alphabet.length();++i) {
			Character c1 = alphabet.charAt(i);
			dict.put(c1, "");
			for (int j=0;j<alphabet.length();++j) {
				Character c2 = alphabet.charAt(j);
				if (getDistance(c1, c2) < 2) {
					dict.put(c1, dict.get(c1)+c2);
				}
			}
		}
//		System.out.print("keyboard distance: ");
//		System.out.println(dict);
	}
	
	public static boolean ifNeighbor(char a, char b) {
		if (dict.containsKey(a) && dict.containsKey(b) && dict.get(a).indexOf(b)>=0) {
			return true;
		}
		return false;
	}
	
	private static double getDistance(char a, char b) {		
		double dist = 0;
		Pair<Double, Double> cord_a = getCoordinate(a);
		Pair<Double, Double> cord_b = getCoordinate(b);
		
		dist += Math.abs(cord_a.getKey()-cord_b.getKey());
		dist += Math.abs(cord_a.getValue()-cord_b.getValue());
		
		return dist;
	}
	
	private static Pair<Double, Double> getCoordinate(char a) {
		double x = 0, y = 0;
		if (firstLine.indexOf(a)>=0) {
			x = firstLine.indexOf(a);
			y = 0;
		} else if (secondLine.indexOf(a)>=0) {
			x = secondLine.indexOf(a)+0.5;
			y = 1;
		} else if (thirdLine.indexOf(a)>=0) {
			x = thirdLine.indexOf(a)+1;
			y = 2;
		} else {
			System.out.println("error"+a);
		}
		
		return new Pair<Double, Double>(x, y);
	}
}
