package application;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class WinWindows {

	private Shell shell;
	private Shell fatherShell;
	
	
	
	public void open(Shell fatherShell_, String programName, String text){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(200, 70);
		shell.setMinimized(false);
		shell.setLocation(400, 50);
		shell.setText(programName + " - " + text);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		Label lblWin = new Label(shell, SWT.NONE);
		lblWin.setText(text);
		Button exit = new Button(shell, SWT.NONE);
		exit.setText("Salir");
		exit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	shell.dispose();
            }
        });
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	
	
}
