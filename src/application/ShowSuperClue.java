package application;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import umlResources.UMLClassDiagram;
import umlResources.Relation;

public class ShowSuperClue {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void show(Shell fatherShell_, String programName, UMLClassDiagram diagram, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell, SWT.RESIZE | SWT.MAX | SWT.CLOSE);
		Display display = shell.getDisplay();
	    
		log.add("Vio los nombres de clases, relaciones, metodos y atributos");
		
	    Label labelWrap = new Label(shell, SWT.WRAP );
	    labelWrap.setBounds(50, 10, 250, diagram.getClasses().size()*100+diagram.getRelations().size()*100);
	    for (umlResources.Class c : diagram.getClasses()){
	    	labelWrap.setBounds(50, 10, 500, labelWrap.getSize().y+c.getAttributes().size()*20+c.getMethods().size()*20);
	    }  
	    
	    String clue = new String();

	    clue = clue +  "\n";
	    clue = "Clases:\n";
		
	    for (umlResources.Class c : diagram.getClasses()){
	    	clue = clue + c.getTittle() + "\n";
	    }

	    clue = clue + "\n";
	    clue = clue + "Metodos:\n";
	    
	    for (umlResources.Class c : diagram.getClasses()){
	    	for(String s : c.getMethods())
	    		clue = clue + s + "\n";
	    }

	    clue = clue + "\n";
	    clue = clue + "Atributos:\n";
	   
	    for (umlResources.Class c : diagram.getClasses()){
	    	for(String s : c.getAttributes())
	    		clue = clue + s + "\n";
	    }

	    clue = clue + "\n";
	    clue = clue + "Relaciones:\n";
	    
	    for (Relation r : diagram.getRelations()){
	    	clue = clue + "entre " + r.getFather().getTittle() + " y " + r.getSon().getTittle() + "\n";
	    }
	    
	    
	    labelWrap.setText(clue);
	    
	    Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e-> {shell.dispose();});
		
		fatherShell.setEnabled(false);
		
		shell.setSize(330, labelWrap.getSize().y+100);
		btnBack.setBounds(50, shell.getSize().y-70, 50, 30);
		shell.setMinimized(false);
		shell.setLocation(400, 50);
		shell.setText(programName + " - Super pista");
		
		
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		
	
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
	
	}
	
	
}
