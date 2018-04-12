package umlResources;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;

import application.UMLClassFigure;

public class Interface extends umlResources.Class{

	public Interface(String tittle, int posX, int posY) {
		super(tittle, posX, posY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Figure draw(){
		Label classLabel;
		
		classLabel = new Label(this.tittle, getImage("resources/img/int_class_obj.gif"));
		super.me = new UMLClassFigure(classLabel);
		return super.draw();
		
	}
	
	@Override
	public void modStorage(ObjectOutputStream oos) throws IOException{
		type = "Interface";
		super.modStorage(oos);
	}
	
	
}
