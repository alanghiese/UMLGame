package umlResources;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import application.UMLClassFigure;
import utilities.listener;

public class Class{

	protected String tittle;
	int posX,posY;
	private ArrayList<String> attributes = new ArrayList<>();
	private ArrayList<String> methods = new ArrayList<>();
	protected UMLClassFigure me;
	protected String type = "Class";
	
	public Class(String tittle, int posX, int posY){
		this.tittle = tittle;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void addAttribute(String attribute){
		attributes.add(attribute);
	}
	
	public void addMethod(String method){
		methods.add(method);
	}
	
	public void addAttributes(ArrayList<String> list){
		for(String s: list)
			attributes.add(s);
	}
	
	public void deleteMethod(String method){
		methods.remove(method);
	}
	public void deleteAttribute(String attribute){
		attributes.remove(attribute);
	}
	
	public void addMethods(ArrayList<String> list){
		for(String s: list)
			methods.add(s);
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosY() {
		return posY;
	}

	public String getTittle(){
		return tittle;
	}

	public ArrayList<String> getAttributes(){
		return attributes;
	}

	public ArrayList<String> getMethods(){
		return methods;
	}
	
	public void modStorage (ObjectOutputStream oos) throws IOException{
		oos.writeObject(type);
		oos.writeObject(getTittle());
		oos.writeInt(getPosX());
		oos.writeInt(getPosY());
		oos.writeInt(getAttributes().size());
		for(String s: getAttributes())
			oos.writeObject(s);
		oos.writeInt(getMethods().size());
		for(String s: getMethods())
			oos.writeObject(s);		
	}
	
	public Figure draw(){
			
		new listener(me,this);
		me.setPreferredSize(150, 15+17*methods.size()+17*attributes.size());
		
		for(String s: attributes){
			Label att = new Label(s);
			me.getAttributesCompartment().add(att);
		}
		
		for(String s: methods){
			Label met = new Label(s);
			me.getMethodsCompartment().add(met);
		}
		
		return me;
		
	}
	
	
	
	protected static Image getImage(String path) {
		File f = new File(System.getProperty("user.dir"), path );
		return new Image( Display.getCurrent(), f.getAbsolutePath() );
	}
	
	@Override
	public boolean equals (Object other){
		boolean att = true, met = true;
		if (attributes.size() != ((Class)other).getAttributes().size())
			att = false;
		else
			for(String s : attributes){
				if (!((Class)other).getAttributes().contains(s)){
					att = false;
					break;
				}
			}
		
		if (methods.size() != ((Class)other).getMethods().size())
			met = false;
		else
			for(String m : methods){
				if (!((Class)other).getMethods().contains(m)){
					met = false;
					break;
				}
			}
		return met && att && this.tittle.equalsIgnoreCase(((Class)other).getTittle());
	}
	
}
