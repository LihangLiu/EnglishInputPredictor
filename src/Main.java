import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
				
		// load vocab
		String vocabFile = "myvocab.txt";
		Vocab vocab = new Vocab();
		vocab.loadVocabFile(vocabFile);		
		
		// load bigram
		String bigramFile = "gram_freqt_2.txt";
		BiGram bigramPre = new BiGram();
		bigramPre.loadBiGramFreqtFile(bigramFile);
		// load trigram
		String trigramFile = "gram_freqt_3.txt";
		TriGram trigramPre = new TriGram();
		trigramPre.loadTriGramFreqtFile(trigramFile);
				
		// load history
		String hisFile = "train.txt";
		UserHistory uh = new UserHistory();
		uh.loadFile(hisFile);

		// test user history
		System.out.println("Sample Input:");
		Scanner in = new Scanner(System.in);
		while (true) {
			if (!in.hasNext()) {
				break;
			}
			String  line = in.nextLine();
			line += " | ";
			line = line.replaceAll("<s>", "-s-");	
			String[] tmp = line.split(" ");
			if (tmp.length < 2) {
				
				continue;
			}
			String last1Word = tmp[tmp.length-2];
			String last2word = null; 
			String last3word = null;
			String pred = "";
			HashSet<String> predicts = null;
			
			
			if (tmp.length >= 3) {
				last2word = tmp[tmp.length-3];
				if (pred.compareTo("")==0) {
					predicts = uh.getPredict(last2word, last1Word);
					if (predicts != null) {
						double max_freqt = 0;
						for (String p:predicts) {
							double c_f = uh.getFreqt(last2word, last1Word, p);
							// double c_f = ED.minDistance(last1Word, p);
							if (c_f > max_freqt) {
								max_freqt = c_f;
								pred = p;
							}
						}
					}
				}
			}
			
			if (pred.compareTo("") == 0) { 
				predicts = uh.getPredict(last1Word);
				if (predicts != null) {
					double max_freqt = 0;
					for (String p:predicts) {
						double c_f = uh.getFreqt(last1Word, p);
						// double c_f = ED.minDistance(last1Word, p);
						if (c_f > max_freqt) {
							max_freqt = c_f;
							pred = p;
						}
					}
				}
			}
			
			if (true) {
				if (tmp.length >= 4) {
					if (pred.compareTo("") == 0) { 
						last3word = tmp[tmp.length-4];
						last2word = tmp[tmp.length-3]; 
						predicts = trigramPre.getPredict(last3word, last2word);
						if (predicts != null) {
							ArrayList<String> list = new ArrayList<>();
							ArrayList<Integer> freqts = new ArrayList<>();
							list.addAll(predicts);
							for (String word : list) 
								freqts.add(trigramPre.getFreqt(last3word,last2word, word));
							pred = ED.nearestWord(last1Word, list, freqts);
							if (ED.minDistance(last1Word, pred)>1) 
								pred = "";
						}
					}
				}
				
				if (tmp.length >= 3) {
					if (pred.compareTo("") == 0) {
						last2word = tmp[tmp.length-3]; 
						predicts = bigramPre.getPredict(last2word);
						if (predicts != null) {
							ArrayList<String> list = new ArrayList<>();
							ArrayList<Integer> freqts = new ArrayList<>();
							list.addAll(predicts);
							for (String word : list) 
								freqts.add(bigramPre.getFreqt(last2word, word));
							pred = ED.nearestWord(last1Word, list, freqts);
							if (ED.minDistance(last1Word, pred)>1) 
								pred = "";
						}
					}
				}
				
				if (pred.compareTo("") == 0) {
					predicts = vocab.getAllWord();
					if (predicts != null) {
						ArrayList<String> list = new ArrayList<>();
						ArrayList<Integer> freqts = new ArrayList<>();
						list.addAll(predicts);
						for (String word : list) 
							freqts.add(vocab.getFreqt(word));
						pred = ED.nearestWord(last1Word, list, freqts);
					}
				}
				// sentence start: capital
				if (tmp.length >= 3) {
					last2word = tmp[tmp.length-3];
					if (last2word.compareTo("-s-")==0) {
						pred  = pred.substring(0,1).toUpperCase()+pred.substring(1);
					}
				}
			}
		 	
			System.out.println(pred);
			
		}
		in.close();
	}

}



/* * * * * * * *  * * * * * * result * * * * * * * * * * * * *
 * 
 * UserHis:				99808 times 0.7908384097467137
 * UserHis + same:		99808 times 0.81013546008336
 * UserHis + tri + bi + vocab:					99808 times 0.821707678743187
 * UserHis (uni+bi) + tri + bi + vocab:			99808 times 0.832484488368803
 * UserHis (uni+bi+tri) + tri + bi + vocab:		99808 times 0.832484488368803
*/