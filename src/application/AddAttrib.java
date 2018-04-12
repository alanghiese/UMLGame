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

import umlResources.UMLClassDiagram;;

public class AddAttrib {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(250, 200);
		shell.setMinimized(false);
		shell.setLocation(200, 50);	
		shell.setText(programName + " - Nuevo atributo");
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		shell.setBackground(ColorConstants.lightGray);
		
		drawComponents(diagram,log);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(UMLClassDiagram diagram, ArrayList<String> log){
		
		Label lblName = new Label(shell, SWT.NONE);		
		lblName.setText("Nombre del atributo: ");		
		lblName.setBackground(ColorConstants.lightGray);
		
        Text txtName = new Text(shell, SWT.NONE);
        txtName.setToolTipText("Ingrese nombre del atributo");
		
        
        Label lblType = new Label(shell, SWT.NONE);		
		lblType.setText("Tipo del atributo: ");		
		lblType.setBackground(ColorConstants.lightGray);
		
        Text txtType = new Text(shell, SWT.NONE);
        txtType.setToolTipText("tipo nombre del atributo");
		        
        Label lblClass = new Label (shell,SWT.NONE);
        lblClass.setBackground(ColorConstants.lightGray);
        lblClass.setText("Seleccione clase: ");
        
        Combo cClass = new Combo(shell, SWT.READ_ONLY);
        for (umlResources.Class c: diagram.getClasses())
        	cClass.add(c.getTittle());
        
        
        Label lblScope = new Label (shell,SWT.NONE);
        lblScope.setBackground(ColorConstants.lightGray);
        lblScope.setText("Seleccione alcance: ");
        
        Combo cScope = new Combo(shell, SWT.READ_ONLY);
        cScope.add("private");
        cScope.add("protected");
        cScope.add("public");

        Label lblParameters = new Label(shell, SWT.NONE);
        Text txtParameters = new Text(shell, SWT.NONE);
        lblParameters.setVisible(false);
        txtParameters.setVisible(false);
        
        lblParameters.setText("");
           
        
        Button btnSave = new Button(shell, SWT.PUSH);
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	for (umlResources.Class c: diagram.getClasses())
    				if (((umlResources.Class)c).getTittle().equals(cClass.getText()))
        				if (!c.getAttributes().contains(getString(cScope.getText(),txtName.getText(),txtParameters.getText(),txtType.getText()))){
        					c.addAttribute(getString(cScope.getText(),txtName.getText(),txtParameters.getText(),txtType.getText()));
        					log.add("Agrego el atributo " + txtName.getText());
        				}
        				
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	
	}
	
	private String getString(String scope, String name, String parameters, String t){
		String s = new String();
		if (scope.equals("public")) s = "+";
		else if (scope.equals("private")) s = "-";
		else s = "#";
		return s + " " + name +": "+ t + ";";
	}
	
	
}