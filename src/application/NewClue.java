package application;

import java.util.ArrayList;
import java.util.HashMap;

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


public class NewClue {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, HashMap<String,String> clues, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		Display display = shell.getDisplay();
		
		shell.setSize(870, 230);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText(programName + " - Nueva pista");
		shell.setBackground(ColorConstants.lightGray);
		
		
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents(clues,log);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(HashMap<String,String> clues, ArrayList<String> log){
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Nombre de la pista: ");
		lblName.setBackground(ColorConstants.lightGray);
        Text txtName = new Text(shell, SWT.NONE);
		Label lblClue = new Label(shell, SWT.NONE);
		lblClue.setText("Pista: ");
		lblClue.setBackground(ColorConstants.lightGray);
        Text txtClue = new Text(shell, SWT.WRAP);
        Button btnSave = new Button(shell, SWT.PUSH);
		
        btnSave.setText("Guardar");
        btnSave.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	clues.put(txtName.getText(),txtClue.getText());
            	log.add("Agrego la pista " + txtName.getText());
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
        
		lblName.setBounds(3, 3, 100, 25);
		txtName.setBounds(110, 3, 700, 25);
        lblClue.setBounds(3, 38, 50, 25);
		txtClue.setBounds(110, 38, 700, 100);
		btnSave.setBounds(380, 148, 50, 35);
		btnBack.setBounds(440, 148, 50, 35);
	
	}
	
	
	
}
