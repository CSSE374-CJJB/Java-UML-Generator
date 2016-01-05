package edu.rosehulman.cjjb.asm;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Relations {
	List<String> includedELements = new ArrayList<String>();
	
	Map<String,String> childParrent = new HashMap<String,String>();
	List<String[]> interfaces = new ArrayList<String[]>();
	
	public void addElement(String element) {
		includedELements.add(element);
	}
	
	public void addChildParrentRelation(String child, String parrent) {
		childParrent.put(child, parrent);
	}
	
	public void addInterfaceRelation(String clazz, String interfase) {
		interfaces.add(new String[] {clazz, interfase});
	}
	
	public Map<String,String> getChildParentIncludedRelations() {
		Map<String,String> toReturn = new HashMap<String,String>();
		
		for(String child: childParrent.keySet()) {
			String parrent = childParrent.get(child);
			
			if(includedELements.contains(parrent)) 
				toReturn.put(child, parrent);
		}
		
		return toReturn;
	}
	
	public List<String[]> getIncludedInterfaceRelations() {
		List<String[]> toReturn = new ArrayList<String[]>();
		
		for(String[] array: interfaces) {
			if(includedELements.contains(array[1]))
				toReturn.add(array);
		}
		
		return toReturn;
	}
}
