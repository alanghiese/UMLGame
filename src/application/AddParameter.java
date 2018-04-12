package application;



import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class AddParameter {
	
	private Shell shell;
	private Shell fatherShell;
	private String par = "";
	
		public void open(Shell fatherShell_, ArrayList<String> parameter){
			
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		Display display = shell.getDisplay();
		shell.setSize(345, 150);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText( " - Nuevo parametro");
		shell.setBackground(ColorConstants.lightGray);
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents(parameter);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
	}
		
	private void drawComponents(ArrayList<String>  parameter){
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Nombre del parametro: ");
		lblName.setBackground(ColorConstants.lightGray);
		
	    Text txtName = new Text(shell, SWT.NONE);
	    
		Label lblType = new Label(shell, SWT.NONE);
		lblType.setText("Tipo del parametro"
				+ ": ");
		lblType.setBackground(ColorConstants.lightGray);
        Text txtType = new Text(shell, SWT.WRAP);
        
        Button btnSave = new Button(shell, SWT.PUSH);
		
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	par = txtType.getText() + " " + txtName.getText();
            	if (parameter.isEmpty()){
            		if (!txtName.getText().equals("") && !txtType.getText().equals(""))
            			parameter.add(par);
            	}
            		
            	else {
            		if (!txtName.getText().equals("") && !txtType.getText().equals(""))
            			parameter.add(", " + par);
            				
            	}
            }
        });
        
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
        
		lblName.setBounds(3, 3, 135, 25);
		txtName.setBounds(136, 3, 200, 20);
        lblType.setBounds(3, 38, 135, 25);
		txtType.setBounds(136, 38, 200, 20);
		btnSave.setBounds(175, 80, 50, 35);
		btnBack.setBounds(115, 80, 50, 35);
		
		}
	
	
		
}
