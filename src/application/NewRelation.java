package application;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import umlResources.UMLClassDiagram;
import umlResources.Relation;
import umlResources.SimpleAssociation;
import umlResources.Aggregation;
import umlResources.Composition;
import umlResources.Dependency;
import umlResources.DoubleAssociation;
import umlResources.Inherits;

public class NewRelation {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, boolean simpleAssociation, boolean doubleAssociation, boolean composition, boolean aggregation, boolean inherits, boolean dependecy, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(250, 250);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		if (simpleAssociation)
			shell.setText(programName + " - Nueva asociacion simple");
		else if (doubleAssociation)
			shell.setText(programName + " - Nueva asociacion doble");
		else if (composition)
			shell.setText(programName + " - Nueva composicion");
		else if (aggregation)
			shell.setText(programName + " - Nueva agregacion");
		else if(inherits)
			shell.setText(programName + " - Nueva herencia");
		else
			shell.setText(programName + " - Nueva dependencia"); 
		shell.setBackground(ColorConstants.lightGray);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents(diagram, simpleAssociation, doubleAssociation, composition, aggregation, inherits, dependecy,log);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(UMLClassDiagram diagram, boolean simpleAssociation, boolean doubleAssociation, boolean composition, boolean aggregation, boolean inherits, boolean dependecy, ArrayList<String> log){
		
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Nombre de la relacion: "); 
		lblName.setBackground(ColorConstants.lightGray);
		Text txtName = new Text(shell,SWT.NONE);
        txtName.setToolTipText("Ingrese nombre de la relacion");
		Label lblFather = new Label(shell, SWT.NONE);
		if (inherits)
			lblFather.setText("Entidad hija");
		else
			lblFather.setText("Entidad inicio");			
		lblFather.setBackground(ColorConstants.lightGray);
		Combo cFather = new Combo(shell, SWT.READ_ONLY);
		Label lblSon = new Label(shell, SWT.NONE);
		if (inherits)
			lblSon.setText("Entidad padre");
		else
			lblSon.setText("Entidad Fin");
		lblSon.setBackground(ColorConstants.lightGray);
		Combo cSon = new Combo(shell, SWT.READ_ONLY);
		Label lblCard1 = new Label(shell, SWT.NONE);
		lblCard1.setText("Cardinalidad del inicio");
		lblCard1.setBackground(ColorConstants.lightGray);
		Combo ca1 = new Combo(shell,SWT.READ_ONLY);
		
		Label lblCard2 = new Label(shell, SWT.NONE);
		lblCard2.setText("Cardinalidad del fin");
		lblCard2.setBackground(ColorConstants.lightGray);
		Combo ca2 = new Combo(shell,SWT.READ_ONLY);
		if (inherits){
			txtName.setEnabled(false);
			ca1.setEnabled(false);
			ca2.setEnabled(false);
			ca1.setText("");
			ca2.setText("");
		}
		else{
			if(!composition){
				ca1.add("1..N");
				ca1.add("N..N");
				ca1.add("0..N");
			}
			ca1.add("1..1");
			ca1.add("0..1");
			
			ca2.add("1..N");
			ca2.add("N..N");
			ca2.add("1..1");
			if (!composition){
				ca2.add("0..1");
				ca2.add("0..N");
			}
		}
		
		
		for (umlResources.Class c: diagram.getClasses()){
			cFather.add(c.getTittle());
			cSon.add(c.getTittle());
		}
		
		
		
		Button btnSave = new Button(shell, SWT.PUSH);
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	umlResources.Class c1 = null,c2 = null;
            	for (umlResources.Class c: diagram.getClasses()){
    				if (c.getTittle().equals(cFather.getText()))
        				c1 = c;
        			if (c.getTittle().equals(cSon.getText()))
        				c2 = c;
        		}
            	if (inherits){
        			txtName.setText(c1.getTittle()+" is the Son of "+c2.getTittle());
        		}
            	Relation relation;
            	String salchicha; //una pequeña broma que hace de oasis entre tanto codigo <3
            	if (simpleAssociation){
            		relation = new SimpleAssociation(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "asosciacion";
            	}
            	else if (doubleAssociation){
            		relation = new DoubleAssociation(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "asosciacion doble";
            	}
            	else if (composition){
            		relation = new Composition(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "composicion";
            	}
            	else if (aggregation){
            		relation = new Aggregation(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "agregacion";
            	}
            	else if (inherits){
            		relation = new Inherits(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "herencia";
            	}
            	else {
            		relation = new Dependency(c1, c2, txtName.getText(), ca1.getText(), ca2.getText());
            		salchicha = "dependencia";
            	}
            		
            	
                if (((!txtName.getText().equals("") 
                		&& !ca1.getText().equals("") 
                		&& !ca2.getText().equals("")
                		&& !inherits)
                		|| inherits)
                		&& c1 != null 
                		&& c2 != null
                		&&(inherits && !c1.equals(c2) || !inherits) 
                		&& !diagram.containRelation(relation.getName())){
                	log.add("Agreago una " + salchicha + " llamada " + relation.getName());
                	diagram.addRelation(relation);
                }
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	
	}
	
}
