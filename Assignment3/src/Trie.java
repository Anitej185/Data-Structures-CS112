//package trie;

import java.util.ArrayList;

/**
* This class implements a Trie. 
* 
* @author Sesh Venugopal
*
*/
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	//private static method to aid buildTrie
	private static int blAid(String s, String included) 
	{
		int aa = 0;
		
		while(aa < included.length() && aa < s.length() && included.charAt(aa) == s.charAt(aa))
		{
			aa++;
		}
		int b = aa - 1;
		return b;
	}
	
	//private static int clAid(String a)
	//{
	//	int n = a.length();
	//	return n;
	//}
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/

		TrieNode root = new TrieNode(null, null, null);
		
		if (allWords.length == 0)
		{
			return root;
		}
		
		if (root.firstChild == null) 
		{
			TrieNode node;
			Indexes k = new Indexes (0, (short) 0, (short) (allWords[0].length() - 1));
			node = new TrieNode (k, null, null);
			root.firstChild = node;
		}

		TrieNode location = root.firstChild;
		TrieNode lastLocation = root.firstChild;
		
		int similar = 0; 
		int beginningInd = 0;
		int lastInd = 0;
		int wordInd = 0;

		for (int i = 1; i < allWords.length; i++) 
		{
			String key = allWords[i];

			while (location != null) 
			{
				beginningInd = location.substr.startIndex;
				lastInd = location.substr.endIndex;
				wordInd = location.substr.wordIndex;

				if (beginningInd > key.length()) 
				{
					lastLocation = location;
					location = location.sibling;
					continue;
				}

				similar = blAid(allWords[wordInd].substring(beginningInd, lastInd + 1), key.substring(beginningInd));

				if (similar >= 0)
				{
					similar = similar + beginningInd;
				}
				
				if (similar < 0) 
				{
					lastLocation = location;
					location = location.sibling;
				}
			
				else 
				{
					if (similar == (lastInd)) 
					{
						lastLocation = location;
						location = location.firstChild;
					} 
					
					else if (similar < (lastInd)) 
					{ 
						lastLocation = location;
						break;
					}
				}
			}

			if (location == null) 
			{
				TrieNode node1;
				Indexes k1 = new Indexes (i, (short) beginningInd, (short) (key.length() - 1));
				node1 = new TrieNode (k1, null, null);
				lastLocation.sibling = node1;
			} 
			
			else 
			{
				TrieNode current = lastLocation.firstChild; 

				Indexes index = new Indexes(lastLocation.substr.wordIndex, (short) (similar + 1), lastLocation.substr.endIndex);
				
				lastLocation.substr.endIndex = (short) similar;
				lastLocation.firstChild = new TrieNode(index, null, null);
				lastLocation.firstChild.firstChild = current;
				
				TrieNode node2;
				Indexes k2 = new Indexes ((short) i, (short) (similar + 1), (short) (key.length() - 1));
				node2 = new TrieNode (k2, null, null);
				lastLocation.firstChild.sibling = node2;
			}
			//reset values
			location = root.firstChild;
			lastLocation = root.firstChild;
			similar = 0;
			beginningInd = 0;
			lastInd = 0;
			wordInd = 0;
		}
		return root;
	}
	
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/

		if (root == null) 
		{
			return null;
		}

		ArrayList<TrieNode> resultsList = new ArrayList<>();

		TrieNode location1 = root;

		while (location1 != null) 
		{

			if (location1.substr == null) 
			{
				location1 = location1.firstChild;
			}

			String string = allWords[location1.substr.wordIndex];
			
			if (string.startsWith(prefix) 
					|| prefix.startsWith(allWords[location1.substr.wordIndex].substring(0, location1.substr.endIndex + 1))) 
			{
				if (location1.firstChild != null) 
				{
					resultsList.addAll(completionList(location1.firstChild, allWords, prefix));
					location1 = location1.sibling;
				}
				
				else if (location1.firstChild == null)
				{
					resultsList.add(location1);
					location1 = location1.sibling;
				}
			}
			// }
			// if (prefix.startsWith(inputtedValue))
			// {
			// location1 = location1.sibling;
			// }

			// else
			// {
			// resultsList.add(location1);
			// break;
			// }
			// }
			// System.out.println("touches = " + inputtedValue);

			else 
			{
				location1 = location1.sibling;
			}
		}

		if (resultsList.isEmpty() == true || resultsList == null || resultsList.size() == 0) // assuming its true
		{
			return null;
		}
		return resultsList;
	}

	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
}