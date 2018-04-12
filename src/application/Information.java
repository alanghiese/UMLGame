package application;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class Information {

	
	
	private Shell shell;
	private Shell fatherShell;
	
	public void open(Shell fatherShell_, String programName){
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell);
		fatherShell.setEnabled(false);
		shell.setSize(250, 250);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText(programName + " - Informacion");
		shell.setBackground(ColorConstants.lightGray);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		
		drawComponents();
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		fatherShell.setEnabled(true);
		 
	}
	

	private void drawComponents(){
		Label lblAbs = new Label(shell, SWT.NONE);
		lblAbs.setText("Clase abstracta");
		lblAbs.setBackground(ColorConstants.lightGray);
		Label lblImgAbs = new Label(shell, SWT.NONE);
		lblImgAbs.setImage(new Image(null, "resources/img/abs_class_obj.gif"));

		Label lblIn = new Label(shell, SWT.NONE);
		lblIn.setText("Clase Interface");
		lblIn.setBackground(ColorConstants.lightGray);
		Label lblImgIn = new Label(shell, SWT.NONE);
		lblImgIn.setImage(new Image(null, "resources/img/int_class_obj.gif"));

		Label lbl = new Label(shell, SWT.NONE);
		lbl.setText("Clase");
		lbl.setBackground(ColorConstants.lightGray);
		Label lblImg = new Label(shell, SWT.NONE);
		lblImg.setImage(new Image(null, "resources/img/class_obj.gif"));
		
		Label lblInfo = new Label(shell, SWT.NONE);
		lblInfo.setImage(new Image(null, "resources/img/alcance.gif"));
		lblInfo.setToolTipText("Simbologia para el alcance de metodos y atributos");
		@SuppressWarnings("unused")
		Label lblEmpty = new Label(shell,SWT.NONE);
		Button btnBack = new Button(shell,SWT.NONE);
		btnBack.setText("Volver");
		btnBack.addListener(SWT.Selection, e->{shell.dispose();});
		
		
		
		
	
	}
	
	
	
	
}
