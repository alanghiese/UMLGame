package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import umlResources.UMLClassDiagram;
import umlResources.Abstract;
import umlResources.Aggregation;
import umlResources.Composition;
import umlResources.Dependency;
import umlResources.DoubleAssociation;
import umlResources.Inherits;
import umlResources.Interface;
import umlResources.Relation;
import umlResources.SimpleAssociation;
import umlResources.SimpleClass;

public class StorageManager {

	
	public void save(UMLClassDiagram diagram, String path) throws IOException{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeInt(diagram.getClues().size());
		for (String s: diagram.getClues().keySet()){
			oos.writeObject(s);
			oos.writeObject(diagram.getClues().get(s));
		} 
		oos.writeInt(diagram.getClasses().size());
		for(umlResources.Class c: diagram.getClasses()){
			c.modStorage(oos);		
		}
		int amountR = diagram.getRelations().size();
		
		oos.writeInt(amountR);
		for(Relation r: diagram.getRelations()){
			r.modStorage(oos);	
		}
		
		oos.close();
	}
	

	public void load(UMLClassDiagram diagram, String path) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		int amountOfClues = ois.readInt();
		for (int i = 0; i < amountOfClues; i++){
			diagram.addClue((String)ois.readObject(), (String)ois.readObject());
		}
		int amountOfClasses = ois.readInt();
		for(int i = 0; i < amountOfClasses; i++){
			String type = (String) ois.readObject();
			String tittle = (String) ois.readObject();
			int posX = ois.readInt();
			int posY = ois.readInt();
			int amountOfAttribs = ois.readInt();
			ArrayList<String> attributes = new ArrayList<>();
			for (int k = 0; k < amountOfAttribs; k++)
				attributes.add((String)ois.readObject());
			int amountOfMethods = ois.readInt();
			ArrayList<String> methods = new ArrayList<>();
			for (int k = 0; k < amountOfMethods; k++)
				methods.add((String)ois.readObject());
			umlResources.Class newClass = null;
			if (type.equals("Abstract"))
				newClass = new Abstract(tittle, posX, posY);
			else if (type.equals("Interface"))
				newClass = new Interface(tittle, posX, posY);
			else if (type.equals("SimpleClass"))
				newClass = new SimpleClass(tittle, posX, posY);
			newClass.addAttributes(attributes);
			newClass.addMethods(methods);
			diagram.addClass(newClass);
		}
		
		int amountR = ois.readInt();
		for(int i = 0; i < amountR; i++){
			String type = (String) ois.readObject();
			String tittleFather = (String)ois.readObject();
			String tittleSon = (String)ois.readObject();
			String name = (String)ois.readObject();
			String cardFather = (String)ois.readObject();
			String cardSon = (String)ois.readObject();
			Relation newRelation = null ;
			if (type.equals("Association"))
				newRelation = new SimpleAssociation(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			else if (type.equals("DoubleAssociation"))
				newRelation = new DoubleAssociation(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			else if (type.equals("Inherits"))
				newRelation = new Inherits(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			else if (type.equals("Dependency"))
				newRelation = new Dependency(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			else if (type.equals("Composition"))
				newRelation = new Composition(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			else if (type.equals("Aggregation"))
				newRelation = new Aggregation(diagram.getClass(tittleFather), diagram.getClass(tittleSon), name, cardFather, cardSon);
			
			diagram.addRelation(newRelation);
		}
		
		ois.close();


			
			
	}
	
	
	public void createLog(ArrayList<String> log, String path, String name) throws IOException{
		FileWriter file = null;
        PrintWriter pw = null;
        try
        {
        	file = new FileWriter(path);
            pw = new PrintWriter(file);
            pw.println("El " + name + "..." );
            for(String s: log){
    			pw.println(s);
    		}

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != file)
        	   file.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	
}
