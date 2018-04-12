package application;



import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import umlResources.UMLClassDiagram;
import umlResources.Abstract;
import umlResources.Interface;
import umlResources.SimpleClass;

public class NewClass {

	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, boolean isInterface, boolean isAbstract, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(230, 80);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		if (isInterface)
			shell.setText(programName + " - Nueva interface");
		else if (isAbstract)
			shell.setText(programName + " - Nueva clase abstracta");
		else 
			shell.setText(programName + " - Nueva clase");
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents(diagram,isInterface,isAbstract,log);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(UMLClassDiagram diagram, boolean isInterface, boolean isAbstract, ArrayList<String> log){
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Nombre de la clase: ");
        Text txtName = new Text(shell, SWT.NONE);
        txtName.setToolTipText("Ingrese nombre de la clase");
        Button btnSave = new Button(shell, SWT.PUSH);
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	String padua;
            	umlResources.Class newClass;
            	if(isInterface){
            		newClass = new Interface(txtName.getText(), 0, 0);
        			padua = "interface";
            	}
            	else if (isAbstract){
            		newClass = new Abstract(txtName.getText(), 0, 0);
        			padua = "clase abstracta";
            	}
            	else{
            		newClass = new SimpleClass(txtName.getText(), 0, 0);
            		padua = "clase";
            	}
            	
            	if (!diagram.containClass(newClass.getTittle())){
            		log.add("Agrego una " + padua + " llamada " + newClass.getTittle());
            		diagram.addClass(newClass);
            	}
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	
	}
}
