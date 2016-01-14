import java.util.ArrayList;

public class ED {

	public static final double INSERT_COST = 1;
	public static final double DELETE_COST = 1;
	public static final double REPLACE_COST = 1;
	public static final double KB_REPLACE_COST = 0.5;
	public static final double APPEND_COST = 0.1;
	
//	public static void testBalance(String inputword, String label, ArrayList<String> candidtWords, ArrayList<Integer> freqts) { 
//		HashSet<Double> lowerBound = new HashSet<>();
//		HashSet<Double> upperBound = new HashSet<>();
//		
//		int l_i = candidtWords.indexOf(label);
//		int l_freqt = freqts.get(l_i)+1;
//		double l_ed = minDistance(inputword, label);
//		for (int i = 0; i<candidtWords.size(); ++i) {
//			if (i == l_i) {
//				continue;
//			}
//			String c_word = candidtWords.get(i);
//			int c_freqt = freqts.get(i)+1;
//			double c_ed = minDistance(inputword, c_word);
//			if (c_ed>1 || c_freqt==l_freqt ) continue;
//			
//			double tmp = (l_ed-c_ed)/(Math.log(l_freqt)-Math.log(c_freqt)); 
//			if (l_freqt > c_freqt) lowerBound.add(tmp);
//			else upperBound.add(tmp);
//		}
//		if (!lowerBound.isEmpty() && !upperBound.isEmpty())
//			System.out.println(Collections.max(lowerBound) + " " + Collections.min(upperBound));
//	}
	
	public static String nearestWord(String inputword, ArrayList<String> candidtWords, ArrayList<Integer> freqts) {   // 
		String res_word = "";
		double res_ed = 1000000;
		int res_freqt = 0;
		for (int i = 0; i < candidtWords.size(); ++i) {
			String c_word = candidtWords.get(i);
			int c_freqt = freqts.get(i);
			double c_ed = minDistance(inputword, c_word);
			if (c_ed < res_ed || (c_ed==res_ed && c_freqt>res_freqt)) {
				res_ed = c_ed;
				res_word = c_word;
				res_freqt = c_freqt;
			} 
		}
	return res_word;
	}
//	public static String nearestWord(String inputword, ArrayList<String> candidtWords, ArrayList<Integer> freqts) { // // a = 0 for the best
//		String res_word = "";
//		double res_ed = 1000000;
//		int res_freqt = 0;
//		for (int i = 0; i < candidtWords.size(); ++i) {
//			String c_word = candidtWords.get(i);
//			int c_freqt = freqts.get(i);
//			double c_ed = minDistance(inputword, c_word);
//			if (res_ed > 1) {
//				if (c_ed < res_ed || (c_ed==res_ed && c_freqt>res_freqt)) {
//					res_ed = c_ed;
//					res_word = c_word;
//					res_freqt = c_freqt;
//				} 
//			} else {
//				if ((Math.log(c_freqt+1)*0.1-c_ed) > (Math.log(res_freqt+1)*0.1-res_ed)) {
//					res_ed = c_ed;
//					res_word = c_word;
//					res_freqt = c_freqt;
//				}
//			}
//		}
//		return res_word;
//	}
	
	public static double minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		// if same or appending distance
		if (word1.compareTo(word2)==0) {
			return 0;
		} else if (len1<len2 && word2.substring(0, len1).compareTo(word1)==0) {
			return 0.1*(len2-len1);
		}
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		double[][] dp = new double[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i*DELETE_COST;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j*INSERT_COST;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					
					double replace = dp[i][j];								// condiser their distance in keyboard
					if (KeyBoardDistance.ifNeighbor(c1, c2)) 
						replace += KB_REPLACE_COST;										// 4000 time: 0.5(78.45%) for the best
					else
						replace += REPLACE_COST;
					
					double insert = dp[i+1][j];
					if (i==len1-1 && j>len1-1) {
						insert += APPEND_COST;
					} else {
						insert += INSERT_COST;
					}

					double delete = dp[i][j+1] + DELETE_COST;
	 
					double min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
//	public static double minDistance(String word1, String word2) {
//		int len1 = word1.length();
//		int len2 = word2.length();
//		// if same or appending distance
//		if (word1.compareTo(word2)==0) {
//			return 0;
//		} else if (len1<len2 && word2.substring(0, len1).compareTo(word1)==0) {
//			return 0.1*(len2-len1);
//		}
//	 
//		// len1+1, len2+1, because finally return dp[len1][len2]
//		double[][] dp = new double[len1 + 1][len2 + 1];
//	 
//		for (int i = 0; i <= len1; i++) {
//			dp[i][0] = i;
//		}
//	 
//		for (int j = 0; j <= len2; j++) {
//			dp[0][j] = j;
//		}
//	 
//		//iterate though, and check last char
//		for (int i = 0; i < len1; i++) {
//			char c1 = word1.charAt(i);
//			for (int j = 0; j < len2; j++) {
//				char c2 = word2.charAt(j);
//	 
//				//if last two chars equal
//				if (c1 == c2) {
//					//update dp value for +1 length
//					dp[i + 1][j + 1] = dp[i][j];
//				} else {
//					
//					double replace = dp[i][j];								// condiser their distance in keyboard
//					if (KeyBoardDistance.ifNeighbor(c1, c2)) 
//						replace += 0.5;										// 4000 time: 0.5(78.45%) for the best
//					else
//						replace += 1;
//					
//					double insert = dp[i][j + 1] + 1;
//					double delete = dp[i + 1][j] + 1;
//	 
//					double min = replace > insert ? insert : replace;
//					min = delete > min ? min : delete;
//					dp[i + 1][j + 1] = min;
//				}
//			}
//		}
//	 
//		return dp[len1][len2];
//	}
	
//	public static double minDistance(String word1, String word2) {
//		int len1 = word1.length();
//		int len2 = word2.length();
//		if (word1.compareTo(word2)==0) {
//			return 0;
//		} else if (len1<len2 && word2.substring(0, len1).compareTo(word1)==0) {
//			return 0.1*(len2-len1);
//		}
//	 
//		// len1+1, len2+1, because finally return dp[len1][len2]
//		int[][] dp = new int[len1 + 1][len2 + 1];
//	 
//		for (int i = 0; i <= len1; i++) {
//			dp[i][0] = i;
//		}
//	 
//		for (int j = 0; j <= len2; j++) {
//			dp[0][j] = j;
//		}
//	 
//		//iterate though, and check last char
//		for (int i = 0; i < len1; i++) {
//			char c1 = word1.charAt(i);
//			for (int j = 0; j < len2; j++) {
//				char c2 = word2.charAt(j);
//	 
//				//if last two chars equal
//				if (c1 == c2) {
//					//update dp value for +1 length
//					dp[i + 1][j + 1] = dp[i][j];
//				} else {
//					int replace = dp[i][j] + 1;
//					int insert = dp[i][j + 1] + 1;
//					int delete = dp[i + 1][j] + 1;
//	 
//					int min = replace > insert ? insert : replace;
//					min = delete > min ? min : delete;
//					dp[i + 1][j + 1] = min;
//				}
//			}
//		}
//	 
//		return dp[len1][len2];
//	}
}
