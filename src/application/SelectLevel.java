package application;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class SelectLevel {

	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(170, 90);
		shell.setMinimized(false);
		shell.setLocation(500, 300);
		shell.setText(programName + " - Seleccion de nivel");
		GridLayout layout = new GridLayout(3, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);

		Button lvl1 = new Button(shell,SWT.NONE);
		Button lvl2 = new Button(shell,SWT.NONE);
		Button lvl3 = new Button(shell,SWT.NONE);
		lvl1.setText("Nivel 1");
		lvl2.setText("Nivel 2");
		lvl3.setText("Nivel 3");
		lvl1.setToolTipText("100% de las pistas");
		lvl2.setToolTipText("50% de las pistas (minimo 1 pista)");
		lvl3.setToolTipText("20% de las pistas (minimo 1 pista)");
		@SuppressWarnings("unused")
		Label lblEmpty = new Label(shell,SWT.NONE);
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
	    
		lvl1.addListener(SWT.Selection, e -> {
											 Play p = new Play();
											 p.open(shell,programName,1);
											 });
		
		lvl2.addListener(SWT.Selection, e -> {
											 Play p = new Play();
											 p.open(shell,programName,2);
											 });
		
		lvl3.addListener(SWT.Selection, e -> {
											 Play p = new Play();
											 p.open(shell,programName,3);
											 });
										
		
		
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	
	
	
	
}
