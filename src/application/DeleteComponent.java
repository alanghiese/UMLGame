package application;



import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import umlResources.UMLClassDiagram;
import umlResources.Relation;

public class DeleteComponent {

	
	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, int type, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(250, 100);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		if (type == 0)
			shell.setText(programName + " - Eliminar relacion");
		else
			shell.setText(programName + " - Eliminar clase");
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents(diagram, type, log);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(UMLClassDiagram diagram, int type, ArrayList<String> log){
		Combo combo = new Combo(shell,SWT.READ_ONLY);
		if (type == 0)
			for (Relation r: diagram.getRelations())
				combo.add(r.getName());
		else
			for (umlResources.Class c: diagram.getClasses())
				combo.add(c.getTittle());
		
        Button btnDelete = new Button(shell, SWT.PUSH);
        btnDelete.setText("Eliminar");
        btnDelete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	if (type == 0){
            		diagram.deleteRelation(combo.getText());
        			log.add("elimino la relacion " + combo.getText());
            	}
            	else {
            		umlResources.Class c = diagram.getClass(combo.getText());
            		
            		ArrayList<Relation> aux = new ArrayList<>();
            		for (Relation r: diagram.getRelations()){
            			aux.add(r);
            		}
            		
            		for (int i = 0; i < aux.size(); i++)
            			if (aux.get(i).getFather().equals(c) 
            					|| aux.get(i).getSon().equals(c))
            				diagram.deleteRelation(aux.get(i).getName());
        			log.add("elimino la clase " + combo.getText());
            	
            		diagram.deleteClass(combo.getText());
            	}
            		
            	combo.remove(combo.getText());
            	
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	
	}
	
	
	
	
}
