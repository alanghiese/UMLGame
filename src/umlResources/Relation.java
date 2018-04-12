package umlResources;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;

public abstract class Relation {


	protected String cardFather;
	protected String cardSon;
	protected  Class father;
	protected  Class son;
	protected  String name;
	protected String type = "Relation";
	
	protected PolylineConnection conn = new PolylineConnection();
	
	
	public Relation(Class father, Class son, String name, String cardFather, String cardSon) {
		this.father = father;
		this.son = son;
		this.name = name;
		this.cardFather = cardFather;
		this.cardSon = cardSon;
	}

	public String getName() {
		return name;
	}



	public Class getFather() {
		return father;
	}

	public Class getSon() {
		return son;
	}

	public String getCardFather() {
		return cardFather;
	}


	public String getCardSon() {
		return cardSon;
	}
	
	public Connection draw(Figure fig1, Figure fig2){
		return myConnection(fig1, fig2);
	}
	
	public Connection myConnection(Figure fig1, Figure fig2) {
		
		conn.setSourceAnchor(new ChopboxAnchor(fig2));
		conn.setTargetAnchor(new ChopboxAnchor(fig1));
		conn.setSourceDecoration( myPolygonDecoration() );
		
		
		
		return conn;
	}
	
	abstract public  PolygonDecoration myPolygonDecoration(); 

	@Override
	public boolean equals(Object other){
		return  this.father.equals(((Relation)other).getFather()) && this.son.equals(((Relation)other).getSon())
				&& this.cardFather.equalsIgnoreCase(((Relation)other).getCardFather()) 
				&& this.cardSon.equalsIgnoreCase(((Relation)other).getCardSon());
	}
	
	public void modStorage(ObjectOutputStream oos) throws IOException{
		oos.writeObject(type);
		oos.writeObject(getFather().getTittle());
		oos.writeObject(getSon().getTittle());
		oos.writeObject(getName());
		oos.writeObject(getCardFather());
		oos.writeObject(getCardSon());
	}
	
}
