package gameEngine;

public class GameEngine {

	private Diagram myDiagram = null;
	
	public void setDiagram(Diagram diagram){
		this.myDiagram = diagram;
	}
	
	@Override
	public boolean equals (Object other){
		return myDiagram != null && myDiagram.equals((Diagram)other);
	}
	
	
}
