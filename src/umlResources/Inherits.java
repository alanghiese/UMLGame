package umlResources;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.geometry.PointList;

public class Inherits extends Relation{

	public Inherits(Class father, Class son, String name, String cardFather, String cardSon) {
		super(father, son, name, cardFather, cardSon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection myConnection(Figure fig1, Figure fig2) {
		return super.myConnection(fig1, fig2);
	}
	
	@Override
	public PolygonDecoration myPolygonDecoration(){

		PolygonDecoration deco = new PolygonDecoration();
		PointList pl = new PointList();
		
		pl.addPoint( 0, 0 );
		pl.addPoint(-2, 2);
		pl.addPoint(-2, -2);
		deco.setBackgroundColor(ColorConstants.white);
		deco.setTemplate(pl);
		
		return deco;
	}
	
	@Override
	public void modStorage(ObjectOutputStream oos) throws IOException{
		type = "Inherits";
		super.modStorage(oos);
	}
	
}
