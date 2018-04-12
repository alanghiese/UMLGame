package application;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ShowClue {

	private Shell shell;
	private Shell fatherShell;
	
	public void show(Shell fatherShell_, String programName, String clue, ArrayList<String> log){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell, SWT.RESIZE | SWT.MAX | SWT.CLOSE);
		Display display = shell.getDisplay();
	    
		log.add("Vio una pista");
		
	    Label labelWrap = new Label(shell, SWT.WRAP );
	    labelWrap.setText(clue);
	    labelWrap.setBounds(0, 0, 460, 380);
		
	    Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e-> {shell.dispose();});
		btnBack.setBounds(0, 420, 50, 30);
		
		fatherShell.setEnabled(false);
		
		shell.setSize(500, 500);
		shell.setMinimized(false);
		shell.setLocation(400, 50);
		shell.setText(programName + " - Pista");
		
		
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
