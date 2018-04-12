package application;

import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import umlResources.UMLClassDiagram;

public class AddMethod {

	
	private Shell shell;
	private Shell fatherShell;
	private ArrayList<String> parameter = new ArrayList<>();
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		
		fatherShell.setEnabled(false);
		shell.setSize(500, 270);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText(programName + " - Nuevo metodo");
		
		
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
		lblName.setText("Nombre del metodo: ");
		lblName.setBackground(ColorConstants.lightGray);
		lblName.setBounds(3, 3, 120, 20);
		
        Text txtName = new Text(shell, SWT.NONE);                
	    txtName.setToolTipText("Ingrese nombre del metodo"); 
	    txtName.setBounds(125, 3, 270, 20);
	    
        Label lblType = new Label(shell, SWT.NONE);		
		lblType.setText("Tipo del metodo: ");
		lblType.setBackground(ColorConstants.lightGray);
		lblType.setBounds(3, 30, 120, 20);
		
        Text txtType = new Text(shell, SWT.NONE);      
        txtType.setToolTipText("tipo nombre del metodo");
        txtType.setBounds(125, 30, 270, 20);
        
        Label lblClass = new Label (shell,SWT.NONE);
        lblClass.setBackground(ColorConstants.lightGray);
        lblClass.setText("Seleccione clase: ");
        lblClass.setBounds(3, 65, 120, 20);
        
        Combo cClass = new Combo(shell, SWT.READ_ONLY);
        for (umlResources.Class c: diagram.getClasses())
        	cClass.add(c.getTittle());
        
        cClass.setBounds(125, 65, 100, 20);
        
        
        Label lblScope = new Label (shell,SWT.NONE);
        lblScope.setBackground(ColorConstants.lightGray);
        lblScope.setText("Seleccione alcance: ");
        lblScope.setBounds(3, 92, 120, 20);
        
        Combo cScope = new Combo(shell, SWT.READ_ONLY);
        cScope.add("private");
        cScope.add("protected");
        cScope.add("public");
        cScope.setBounds(125, 92, 100, 20);
        
        
        Label lblParam = new Label (shell,SWT.NONE);
        lblParam.setBackground(ColorConstants.lightGray);
        lblParam.setText("Parametros: ");
        lblParam.setBounds(3, 167, 67, 20);
        
        Label lblParam2 = new Label (shell,SWT.NONE);
        lblParam2.setBackground(ColorConstants.lightGray);
        lblParam2.setText("");
        lblParam2.setBounds(69, 167, 450, 20);
        
     
        
        Button btnAddParameter = new Button(shell, SWT.PUSH);
        btnAddParameter.setText("Añadir Parametro");
        btnAddParameter.addListener(SWT.Selection, e -> {AddParameter ap = new AddParameter();        
          ap.open(shell, parameter);
          lblParam2.setText("");
          String parameters= "(";
          for (String s : parameter)
        	  parameters = parameters + s;
          parameters = parameters + ")";
          lblParam2.setText(parameters); 
          
        
        });
        
        
        btnAddParameter.setBounds(190, 127, 120,30);
       
        
        Button btnSave = new Button(shell, SWT.PUSH);
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	for (umlResources.Class c: diagram.getClasses())
    				if (((umlResources.Class)c).getTittle().equals(cClass.getText()))
        				if  (!c.getMethods().contains(getString(cScope.getText(),txtName.getText(),lblParam2.getText(),txtType.getText()))){
        					c.addMethod(getString(cScope.getText(),txtName.getText(),lblParam2.getText(),txtType.getText()));
            				log.add("Agrego el metodo " + txtName.getText());
						}
            }
        });
        
        btnSave.setBounds(438, 197, 50, 30);
        
        
        
        
        
        
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});		
		btnBack.setBounds(3,197,50,30);
		
		
		
		
	
	}
	
	private String getString(String scope, String name, String parameters, String t){
		String s = new String();
		if (scope.equals("public")) s = "+";
		else if (scope.equals("private")) s = "-";
		else s = "#";
		
		return s + " " + name +  parameters + ":" + t + ";";
	}
	
	
}
