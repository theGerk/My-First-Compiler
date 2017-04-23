/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Generates IDs based on given valid characters
 *
 * valid characters can be given directly using addValid or when a string is
 * added or requested, all characters in it are added to the list of valid
 * characters.
 *
 *
 * IDs generated without prefix given have an underscore as the first character
 * always.
 *
 * @author Benji
 */
public class UniqueIdentifierGenerator {
	//makes sense to use a hashset as well as the sorted array list for valid characters, the array list can be used for iterating and the hashset will be used for checking existance.

	/**
	 * Constructs class
	 *
	 * @param used already used strings, characters are used to instatiate valid
	 * characters
	 */
	public UniqueIdentifierGenerator(String... used) {
		if (used.length != 0) {
			givenIds.addAll(Arrays.asList(used));
			givenIds.removeIf(p -> p.isEmpty());
			if (givenIds.isEmpty()) {
				addValid(_DEFAULT_ID_CHARS);
			} else {
				givenIds.forEach(s -> addValid(s));
			}
		} else {
			addValid(_DEFAULT_ID_CHARS);
		}
	}

	/**
	 * adds all characters from a string as valid characters for ID generatation
	 *
	 * @param valid a string of valid characters
	 */
	public final void addValid(String valid) {
		for (char c : valid.toCharArray()) {
			addValid(c);
		}
	}

	/**
	 * adds all characters given as valid characters for ID generation
	 *
	 * @param valid valid characters
	 */
	public final void addValid(char... valid) {
		for (char c : valid) {
			addValid(c);
		}
	}

	/**
	 * adds a character given as valid for ID generation
	 *
	 * @param valid adds the character as valid
	 */
	public final void addValid(char valid) {
		//use binary search to input valid character
		int start = 0, end = validChars.size();
		while (start != end) {
			int guessIndex = (start + end) / 2;
			char c = validChars.get(guessIndex);
			if (c < valid) {
				end = guessIndex;
			} else if (c > valid) {
				start = guessIndex + 1;
			} else {
				return;	// already in the list
			}
		}
		if (validChars.get(start) != valid) {
			validChars.add(start, valid);
			guess.clear();
		}
	}

	private static final char[] _DEFAULT_ID_CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	final HashSet<String> givenIds = new HashSet<>();
	final ArrayList<Character> validChars = new ArrayList<>();
	final ArrayList<Integer> guess = new ArrayList<>();

	private void increment(ArrayList<Integer> indicies) {
		for (int i = 0; i < indicies.size(); i++) {
			indicies.set(i, indicies.get(i) + 1);
			if (indicies.get(i) == validChars.size()) {
				indicies.set(i, 0);
			} else {
				return;
			}
		}
		indicies.add(0);
	}

	private String guessToString(ArrayList<Integer> indicies) {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < indicies.size(); i++) {
			build.append(validChars.get(indicies.get(i)));
		}
		return build.toString();
	}

	/**
	 * Gets a valid and unused ID
	 *
	 * @return valid and unused ID
	 */
	public String getId() {
		return getId("_", guess);
	}

	/**
	 * gets a valid and unsued ID, that starts with a requested string
	 *
	 * @param request will prefix the string, any characters in this string are
	 * now considered valid and will be added to valid character list.
	 * @return a valid ID
	 */
	public String getId(String request) {
		if (request == null || "".equals(request)) {
			return getId();
		} else {
			addValid(request);
			String output = getId(request, new ArrayList<>());
			givenIds.add(output);
			return output;
		}
	}

	private String getId(String prefix, ArrayList<Integer> guess) {
		String current = prefix + guessToString(guess);
		while (givenIds.contains(current)) {
			increment(guess);
			current = prefix + guessToString(guess);
		}
		return current;
	}
}
