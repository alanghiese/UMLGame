package application;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DeleteClue {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName, HashMap<String,String> clues, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(500, 100);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText(programName + " - Eliminar pista");
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
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
		Combo cClue = new Combo(shell,SWT.READ_ONLY);
		for (String s: clues.keySet()){
			cClue.add(s);
		}
        Button btnDelete = new Button(shell, SWT.PUSH);
        btnDelete.setText("Eliminar");
        btnDelete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	clues.remove(cClue.getText());
            	cClue.remove(cClue.getText());
            	log.add("Elimino la pista " + cClue.getText());
            }
        });
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	
	}
	
	
}
