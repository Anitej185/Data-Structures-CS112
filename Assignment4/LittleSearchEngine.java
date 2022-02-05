package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages
 * in which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {

	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the
	 * associated value is an array list of all occurrences of the keyword in
	 * documents. The array list is maintained in DESCENDING order of frequencies.
	 */
	HashMap<String, ArrayList<Occurrence>> keywordsIndex;

	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;

	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String, ArrayList<Occurrence>>(1000, 2.0f);
		noiseWords = new HashSet<String>(100, 2.0f);
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword
	 * occurrences in the document. Uses the getKeyWord method to separate keywords
	 * from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an
	 *         Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String, Occurrence> loadKeywordsFromDocument(String docFile) throws FileNotFoundException {
		
		File f = new File(docFile);
		Scanner sc = new Scanner(f);

		HashMap<String, Occurrence> words = new HashMap<String, Occurrence>(1000, 2.0f);

		int num = 1;
		
		while (sc.hasNext()) 
		{

			String line = sc.next();

			if (line.length() == 0)
			{
				continue;
			}

			String keyword = getKeyword(line);
			
			if (keyword == null)
			{
				continue;
			}

			Occurrence occur = words.get(keyword);

			if (occur == null) 
			{
				Occurrence o = new Occurrence(docFile, num);
				words.put(keyword, o);			
			} 
			
			else 
			{
				occur.frequency++;
				words.put(keyword, occur);
			}
		}
		sc.close();

		return words;
	}

	/**
	 * Merges the keywords for a single document into the master keywordsIndex hash
	 * table. For each keyword, its Occurrence in the current document must be
	 * inserted in the correct place (according to descending order of frequency) in
	 * the same keyword's Occurrence list in the master hash table. This is done by
	 * calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String, Occurrence> kws) {
		
		for (String s : kws.keySet()) 
		{
			ArrayList<Occurrence> o = new ArrayList<Occurrence>();

			if (keywordsIndex.containsKey(s)) 
			{
				o = keywordsIndex.get(s);
			}
			o.add(kws.get(s));
			insertLastOccurrence(o);
			keywordsIndex.put(s, o);
		}
	}

	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of
	 * any trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!' NO
	 * OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be
	 * stripped So "word!!" will become "word", and "word?!?!" will also become
	 * "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		
		//make inputted word lower case
		word = word.toLowerCase();
		
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		
		while(!(alphabets.contains(word.substring(0,1))) 
				&& word.length() > 1) 
		{
			word = word.substring(1);
		}
		
		while(!(alphabets.contains(word.substring(word.length() - 1, word.length()))) 
				&& word.length() > 1) 
		{
			word = word.substring(0, word.length() - 1);
		}
		
		for(int x = 0; x < word.length(); x++) 
		{
			if(!(alphabets.contains(word.substring(x, x + 1)))) 
			{
				return null;
			}
		}
		
		if(noiseWords.contains(word) || word.length() == 0) 
		{
			return null;
		}
		
		return word;
	}

	/**
	 * Inserts the last occurrence in the parameter list in the correct position in
	 * the list, based on ordering occurrences on descending frequencies. The
	 * elements 0..n-2 in the list are already in the correct order. Insertion is
	 * done by first finding the correct spot using binary search, then inserting at
	 * that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary
	 *         search process, null if the size of the input list is 1. This
	 *         returned array list is only used to test your code - it is not used
	 *         elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

		if (occs == null || occs.size() == 1) 
		{
			return null;
		}

		ArrayList<Integer> visited = new ArrayList<Integer>();
		
		int lo = 0;
		int hi = occs.size() - 2;
		int mid = occs.size() - 1;

		while (lo <= hi) 
		{
			mid = (lo + hi) / 2;
			visited.add(mid);

			if (occs.get(occs.size() - 1).frequency == occs.get(mid).frequency) 
			{
				break;
			} 
			
			else 
			{
				if (occs.get(mid).frequency < occs.get(occs.size() - 1).frequency) 
				{
					hi = mid - 1;
				} 
				
				else 
				{
					lo = mid + 1;
				}
			}
		}

		if (occs.get(occs.size() - 1).frequency < occs.get(occs.size() - 2).frequency) 
		{
			return visited;
		}
		
		// terminate where mid.frequency >= oc.frequency
		for (int x = occs.size() - 1; x > mid; x--) 
		{
			Occurrence oc = occs.get(x);
			occs.set(x, occs.get(x - 1));
			occs.set(x - 1, oc);
		}

		return visited;
	}

	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all
	 * keywords, each of which is associated with an array list of Occurrence
	 * objects, arranged in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile       Name of file that has a list of all the document file
	 *                       names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise
	 *                       word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input
	 *                               files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		
		while (sc.hasNext()) 
		{
			String word = sc.next();
			noiseWords.add(word);
		}

		// index all keywords
		sc = new Scanner(new File(docsFile));
		
		while (sc.hasNext()) 
		{
			String docFile = sc.next();
			HashMap<String, Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}

	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2
	 * occurs in that document. Result set is arranged in descending order of
	 * document frequencies.
	 * 
	 * Note that a matching document will only appear once in the result.
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. That is,
	 * if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same
	 * frequency f1, then doc1 will take precedence over doc2 in the result.
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all,
	 * result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in
	 *         descending order of frequencies. The result size is limited to 5
	 *         documents. If there are no matches, returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {

		ArrayList<Occurrence> occ1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> occ2 = keywordsIndex.get(kw2);
		ArrayList<Occurrence> o = new ArrayList<Occurrence>();

		ArrayList<String> top5List = new ArrayList<String>();
		
		int a;

		if (occ1 != null || occ2 != null) 
		{

			if (occ1 != null) 
			{
				for (a = 0; a < occ1.size(); a++) 
				{
					o.add(occ1.get(a));
				}
			}

			if (occ2 != null) 
			{
				for (a = 0; a < occ2.size(); a++) 
				{
					if (o.indexOf(occ2.get(a)) == -1) 
					{
						o.add(occ2.get(a));
					}
				}
			}

		}

		else 
		{
			return null;
		}

		a = 0;

		for (int b = 1; b < o.size(); b++) 
		{
			Occurrence occ = o.get(b);

			a = b;

			while (a > 0) 
			{
				if (o.get(a - 1).frequency >= occ.frequency) 
				{
					break;
				}
				o.set(a, o.get(a - 1));

				a--;
			}
			o.set(a, occ);
		}

		for (Occurrence traverse : o) 
		{
			if (!top5List.contains(traverse.document)) 
			{
				top5List.add(traverse.document);
			}
		}

		if (top5List.size() > 5) 
		{
			ArrayList<String> arr = new ArrayList<String>();

			for (int b = 0; b < 5; b++) 
			{
				arr.add(top5List.get(b));
			}
			top5List = arr;
		}
		return top5List;
	}
}