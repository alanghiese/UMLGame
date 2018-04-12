package application;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import umlResources.UMLClassDiagram;

public class DeleteAttributeOrMethod {
	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, UMLClassDiagram diagram, int type, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(220, 140);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		if (type == 0)
			shell.setText(programName + " - Eliminar atributo");
		else
			shell.setText(programName + " - Eliminar metodo");
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
		Label lblComboTittle = new Label(shell,SWT.NONE);
		lblComboTittle.setText("Clase: ");
		Combo combo = new Combo(shell,SWT.READ_ONLY);
		for (umlResources.Class c: diagram.getClasses())
			combo.add(c.getTittle());
		
		Label optionDesc = new Label(shell,SWT.NONE);
		if (type == 0)
			optionDesc.setText("Atributo");
		else
			optionDesc.setText("Metodo");
		Combo option = new Combo(shell,SWT.READ_ONLY);
		ModifyListener ml = new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				option.removeAll();
				if(type==0)
					for(String s: diagram.getClass(combo.getText()).getAttributes())
						option.add(s);
				else
					for(String s: diagram.getClass(combo.getText()).getMethods())
						option.add(s);
				
			}};
		combo.addModifyListener(ml);
		
		
			
        Button btnDelete = new Button(shell, SWT.PUSH);
        btnDelete.setText("Eliminar");
        
        btnDelete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	if (type == 0){
            		diagram.getClass(combo.getText()).deleteAttribute(option.getText());
            		log.add("Elimino la clase " + combo.getText());
            	}
            	else {
            		diagram.getClass(combo.getText()).deleteMethod(option.getText());
            		log.add("Elimino la clase " + combo.getText());
            	}
            	
            	combo.removeModifyListener(ml);
            	combo.removeAll();
            	for (umlResources.Class c: diagram.getClasses())
        			combo.add(c.getTittle());
            	combo.addModifyListener(ml);
            	option.removeAll();
            	
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
		
	
	}
	
	
	
}
