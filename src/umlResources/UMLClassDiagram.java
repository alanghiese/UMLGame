package umlResources;

import java.util.Collection;
import java.util.HashMap;
import gameEngine.Diagram;

public class UMLClassDiagram implements Diagram {

	HashMap<String,Class> myClasses = new HashMap<>();
	HashMap<String,Relation> myRelations = new HashMap<>();
	HashMap<String,String> clueList = new HashMap<>();
	
	public void addClass (Class e){
		myClasses.put(e.getTittle(), e);
	}
	
	public umlResources.Class getClass(String name){
		return myClasses.get(name);
	}	
	
	public void addRelation (Relation newAssociation){
		myRelations.put(newAssociation.getName(), newAssociation);
	}
	
	public boolean containClass(String name){
		return myClasses.containsKey(name);
	}
	
	public boolean containRelation(String name){
		return myRelations.containsKey(name);
	}
		
	public Collection<Class> getClasses() {
		
		return myClasses.values();
	}
	
	public Collection<Relation> getRelations() {
		return myRelations.values();
	}
	

	public void deleteRelation(String name){
		myRelations.remove(name);
	}
	
	public void deleteClass(String name){
		myClasses.remove(name);
	}

	
	public void addClue(String name, String clue){
		clueList.put(name, clue);
	}
	
	public void deleteClue(String name){
		clueList.remove(name);
	}
	
	public HashMap<String,String> getClues(){
		return clueList;
	}
	@Override
	public boolean equals (Object other){
		if (myRelations.size() != ((UMLClassDiagram)other).getRelations().size() || myClasses.size() != ((UMLClassDiagram)other).getClasses().size()) 
			return false;
		boolean equal = true;
		for (umlResources.Class c: myClasses.values())
			if (!((UMLClassDiagram)other).getClasses().contains(c)){
				equal = false;
				break;
			}
		
		if (equal){
			for (Relation r : myRelations.values()){
				if (!((UMLClassDiagram)other).getRelations().contains(r)){
					equal = false;
					break;
				}
			}
		}
		
		return equal;
	}
	
	public void clear(){
		myClasses.clear();
		myRelations.clear();
		clueList.clear();
	}
}