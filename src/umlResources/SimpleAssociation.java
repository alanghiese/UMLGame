package umlResources;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.PointList;

public class SimpleAssociation extends Relation{

	public SimpleAssociation(Class father, Class son, String name, String cardFather, String cardSon) {
		super(father, son, name, cardFather, cardSon);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Connection myConnection(Figure fig1, Figure fig2) {
		super.myConnection(fig1, fig2);
		
		ConnectionEndpointLocator targetEndpointLocator = new ConnectionEndpointLocator(conn, true);
		targetEndpointLocator.setVDistance(10);
		Label targetMultiplicityLabel = new Label(cardFather);
		conn.add(targetMultiplicityLabel, targetEndpointLocator);
		
		ConnectionEndpointLocator sourceEndpointLocator = new ConnectionEndpointLocator(conn, false);
		sourceEndpointLocator.setVDistance(10);
		Label sourceMultiplicityLabel = new Label(cardSon);
		conn.add(sourceMultiplicityLabel, sourceEndpointLocator);
		
		ConnectionEndpointLocator relationshipLocator =  new ConnectionEndpointLocator(conn,true);
		relationshipLocator.setUDistance(30);
		relationshipLocator.setVDistance(-20);
		Label relationshipLabel = new Label(name);
		conn.add(relationshipLabel,relationshipLocator);
		
		return conn;
	}
	
	@Override
	public PolygonDecoration myPolygonDecoration(){
		
		PolygonDecoration deco = new PolygonDecoration();
		PointList pl = new PointList();
		
		pl.addPoint( 0, 0 );
		pl.addPoint(-2, 2);
		pl.addPoint(-2, -2);
		deco.setTemplate( pl );
		
		return deco;
	}
	
	@Override
	public void modStorage(ObjectOutputStream oos) throws IOException{
		type = "SimpleAssociation";
		super.modStorage(oos);
	}
	
}
