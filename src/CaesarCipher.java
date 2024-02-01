import java.util.*;

public class CaesarCipher {
	
	Map<Character, Integer> count = new TreeMap<Character, Integer>();
	
	
	//sort tree sets by value instead of keys from: https://www.geeksforgeeks.org/how-to-sort-a-treemap-by-value-in-java/
	public static <K, V extends Comparable<V> > Map<K, V> valueSort(final Map<K, V> map, boolean reverse) {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<K>() {
            
                  // return comparison results of values of
                  // two keys
                  public int compare(K k1, K k2)
                  {
                      int comp = map.get(k1).compareTo(
                          map.get(k2));
                      if (comp == 0)
                          return 1;
                      else
                    	  if (reverse) {
                    		  return -1 * comp;
                    	  } else {
                    		  return comp;
                    	  }
                  }
            
              };
        
        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        
        sorted.putAll(map);
        
        return sorted;
    }
	
	//replace letter in text to mapped value 
	String replaceLetter(String text, Map<Character, Character> userMap) {
		String newText = text;
		for (Map.Entry<Character, Character> entry : userMap.entrySet()) {
			if (entry.getValue() != null) {
				newText = newText.replace(entry.getKey(), entry.getValue());
			}
		}
		return newText;
	}
	
	//calculate frequency of letters in text and insert into map
	Map<Character, Integer> SimpleSub(String text) {
		
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for(int i = 0; i < alpha.length(); i ++) {
			count.put(alpha.charAt(i), 0);
		}
		
		for(int i = 0; i < text.length(); i++) {
			if (!count.containsKey(text.charAt(i))) {
				count.put(text.charAt(i), 1);
			} else {
				int newValue = count.get(text.charAt(i));
				newValue++;
				count.put(text.charAt(i), newValue);
			}
		}
		Map<Character, Integer> sortedCount = valueSort(count, true);
		return sortedCount;
	}
	
	
	public static void main(String[] args) {
		//getting input cipher text from user
		Scanner s = new Scanner(System.in);
		System.out.print("Enter ciphertext: ");
		String input = s.nextLine();
		input = input.replaceAll("\\s", "");	//get rid of all spaces
		
		
		CaesarCipher caesar = new CaesarCipher();
		Map<Character, Integer> sortedCipherMap = caesar.SimpleSub(input);
		//Printing cipher text, frequency, and suggested replacement
		System.out.print("Ciphertext: ");
		for (Map.Entry<Character, Integer> entry : sortedCipherMap.entrySet()) {
			if (entry.getValue() >= 10) {
				System.out.print(entry.getKey() + "  |");
			} else {
				System.out.print(entry.getKey() + " |");
			}
		}
		System.out.println();
		System.out.print("Frequency:  ");
		for (Map.Entry<Character, Integer> entry : sortedCipherMap.entrySet()) {
			System.out.print(entry.getValue() + " |");
		}
		System.out.println();
		System.out.print("Suggested:  ");
		String mostCommon = "etainosrldhcumfpygwvbkxjqz";	//most common letters taken from slides
		Map.Entry<Character, Integer>[] commonArray = sortedCipherMap.entrySet().toArray(new Map.Entry[sortedCipherMap.entrySet().size()]);
		Map<Character, Character> suggestedMap = new HashMap<Character, Character>();
		for (int i = 0; i < sortedCipherMap.entrySet().size(); i++) {
			if (commonArray[i].getValue() >= 10) {
				suggestedMap.put(commonArray[i].getKey(), mostCommon.charAt(i));
				System.out.print(mostCommon.charAt(i) + "  |");
			} else {
				suggestedMap.put(commonArray[i].getKey(), mostCommon.charAt(i));
				System.out.print(mostCommon.charAt(i) + " |");
			}
		}
		System.out.println();
		
		//start user replacement sequence
		boolean done = false;
		int i = 0;
		Map<Character, Character> userMap = new TreeMap<Character, Character>();
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int j  = 0; j < alpha.length(); j++) {
			userMap.put(alpha.charAt(j), null);
		}
		System.out.println("Type 'done' when you have deciphered the text and want the key.");
		while(!done) {
			System.out.print("Enter a character to replace the first ciphertext " + commonArray[i].getKey() + " (Suggested replacement: " + suggestedMap.get(commonArray[i].getKey()) + "): ");
			String userChar = s.nextLine();
			if (userChar.equals("done")) {
				break;
			}
			//ignores empty input from user
			if (userChar.length() == 0) {
				i++;
				if (i == sortedCipherMap.size()) {
					i = 0;
				}
				continue;
			}
			//creating new string from user input to display
			char c = userChar.toLowerCase().charAt(0);
			if (userMap.containsValue(c)) {
				userMap.replace(commonArray[i].getKey(), c);
			} else {
				userMap.put(commonArray[i].getKey(), c);
			}
			String newString = caesar.replaceLetter(input, userMap);
			System.out.println(newString);
			
			//loops back through the input sequence if user wants to replace old characters
			i++;
			if (i == sortedCipherMap.size()) {
				i = 0;
			}
		}
		
		//print the mapped values and the key
		Map<Character, Character> keyMap = valueSort(userMap, false);
		System.out.println(keyMap.entrySet());
		System.out.print("Key: ");
		for (Map.Entry<Character, Character> entry : keyMap.entrySet()) {
			System.out.print(entry.getKey());
		}
		System.out.println();
	}
}
