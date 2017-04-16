/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symboltable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * IDs must fit RegEx: ^[_a-zA-Z][_a-zA-Z0-9]*$
 *
 * @author Benji
 */
public class UniqueIdentifierGenerator {

	public UniqueIdentifierGenerator(String... used) {
		givenIds.addAll(Arrays.asList(used));
	}

	HashSet<String> givenIds = new HashSet<>();
	int guess = 0;

	public String getId() {
		while (givenIds.contains("_" + guess)) {
			guess++;
		}
		givenIds.add("_" + guess);
		return "_" + guess;
	}

	public String getId(String prefix) {
		if (prefix == "") {
			return getId();
		} else if (givenIds.contains(prefix)) {
			int postfix = 0;
			while (givenIds.contains(prefix + postfix)) {
				postfix++;
			}
			givenIds.add(prefix + postfix);
			return prefix + postfix;
		} else {
			givenIds.add(prefix);
			return prefix;
		}
	}
}
