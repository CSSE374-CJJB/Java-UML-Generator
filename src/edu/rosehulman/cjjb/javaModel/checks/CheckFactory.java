package edu.rosehulman.cjjb.javaModel.checks;

import java.util.LinkedList;
import java.util.List;

public class CheckFactory {

	public static List<IPatternCheck> getPatternChecks() {
		List<IPatternCheck> toReturn = new LinkedList<IPatternCheck>();
		
		toReturn.add(new SingletonCheck());
		toReturn.add(new AdapterCheck());
		toReturn.add(new DecoratorCheck());
		
		return toReturn;
	}
}
