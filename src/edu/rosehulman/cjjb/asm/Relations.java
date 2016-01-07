package edu.rosehulman.cjjb.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Relations {
	Set<String> includedELements = new HashSet<String>();

	Map<String, String> childParrent = new HashMap<String, String>();
	Set<Relation> interfaces = new HashSet<Relation>();

	Set<Relation> uses = new HashSet<Relation>();
	Set<Relation> association = new HashSet<Relation>();

	public void addElement(String element) {
		includedELements.add(element);
	}

	public void addChildParrentRelation(String child, String parrent) {
		childParrent.put(child, parrent);
	}

	public void addInterfaceRelation(String clazz, String interfase) {
		interfaces.add(new Relation(clazz, interfase));
	}

	public Map<String, String> getChildParentIncludedRelations() {
		Map<String, String> toReturn = new HashMap<String, String>();

		for (String child : childParrent.keySet()) {
			String parrent = childParrent.get(child);

			if (includedELements.contains(parrent))
				toReturn.put(child, parrent);
		}

		return toReturn;
	}

	public Set<Relation> getIncludedInterfaceRelations() {
		Set<Relation> toReturn = new HashSet<Relation>();

		for (Relation array : interfaces) {
			if (includedELements.contains(array.relatedTo))
				toReturn.add(array);
		}

		return toReturn;
	}

	public void addUsesRelations(String className, String classOther) {
		if(className.equals(classOther))
			return;
		uses.add(new Relation(className, classOther));
	}

	public void addAssociationRelations(String className, String classOther) {
		if(className.equals(classOther))
			return;
		association.add(new Relation(className, classOther));
	}
	
	public Set<Relation> getIncludedUsesRelations() {
		Set<Relation> toReturn = new HashSet<Relation>();

		for (Relation array : uses) {
			if (includedELements.contains(array.relatedTo))
				toReturn.add(array);
		}

		return toReturn;
	}

	public Set<Relation> getIncludedAssociationsRelations() {
		Set<Relation> toReturn = new HashSet<Relation>();

		for (Relation array : association) {
			if (includedELements.contains(array.relatedTo))
				toReturn.add(array);
		}

		return toReturn;
	}
}
