package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainApp {

	private Shell shell = new Shell(new Display(), SWT.CLOSE | SWT.TITLE | SWT.MIN );
	private String programName;
	
	
	public void open(String programName) {
		this.programName = programName;
		shell.setSize(250, 350);
		shell.setMinimized(false);
		shell.setLocation(400, 200);
		shell.setText(programName);
		shell.setLayout(new GridLayout());

		// build diagram
		Canvas canvas = buildDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		Display display = shell.getDisplay();
		
		Image icon = new Image(display,"resources/img/icon.png");
	    shell.setImage(icon);
	    
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		display.dispose();
		
	}
	
	private Canvas buildDiagram(Composite parent) {

		// instantiate root figure
		Figure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());

		// insantiate a canvas on which to draw
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);

		// this code for drawing
		draw(root);

		return canvas;
	}
	
	private void draw(Figure root) {
		
		Rectangle bounds = new Rectangle();
		Button btnPlay = new Button("Jugar");
		Button btnCreate = new Button("Crear");
		Button btnExit = new Button("Salir");
		Button btnLog = new Button("Logs");
		Label lblTittle = new Label(programName);
		
		
		//botones
		bounds.setBounds(40,130,154,40);
        btnPlay.setBounds(bounds);
        bounds.setBounds(40,190,154,40);
        btnCreate.setBounds(bounds);
        bounds.setBounds(40,250,154,40);
        btnExit.setBounds(bounds);
        bounds.setBounds(-24, 14, 282, 104);
        lblTittle.setBounds(bounds);
        bounds.setBounds(5, 5, 30, 30);
        btnLog.setBounds(bounds);

        btnPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SelectLevel sl = new SelectLevel();
				sl.open(shell, programName);
				
			}});
        
        btnCreate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Create c = new Create();
				c.open(shell,programName);
				
			}});
        
        btnExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
				
			}});
        
        btnLog.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(".\\Logs"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});
        
        root.add(btnExit);
		root.add(btnPlay);
		root.add(btnCreate);
		root.add(lblTittle);
		root.add(btnLog);
	}

	
	
	
	
	
	public static void main(String[] args) {
		MainApp m  = new MainApp();
		m.open("UML Class Diagram Builder");

	}
	
	
}
