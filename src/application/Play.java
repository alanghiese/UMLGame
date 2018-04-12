package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import umlResources.UMLClassDiagram;
import gameEngine.GameEngine;
import umlResources.Relation;
import utilities.StorageManager;

public class Play {
	
	private Shell shell;
	private Shell fatherShell;
	Canvas canvas;
	UMLClassDiagram myDiagram = new UMLClassDiagram();
	UMLClassDiagram otherDiagram = new UMLClassDiagram();
	private HashMap<String,Figure> figures = new HashMap<>();
	int numClue = 0;
	GameEngine ge = new GameEngine();
	int level;
	int maxClues;
	private int sizeX,sizeY,posSX,posSY;
	private ArrayList<String> log = new ArrayList<>();
	
	public void open(Shell fatherShell_, String programName, int level){
		this.level = level;
		this.fatherShell = fatherShell_;
		shell = new Shell(fatherShell, SWT.RESIZE | SWT.MAX | SWT.CLOSE | SWT.MIN);
		fatherShell.setEnabled(false);
		shell.setSize(1030, 630);
		shell.setMinimized(false);
		shell.setLocation(200, 50);
		shell.setText(programName + " - Modo jugar");
		shell.setLayout(new GridLayout());
		Display display = shell.getDisplay();
		Image icon = new Image(display,"resources/img/icon.png");
		shell.setImage(icon);
		log.add("Inicio una nueva partida");
		StorageManager sm = new StorageManager();
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterNames(new String[] { "Text Files", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.txt", "*.*" }); 
		dialog.setFilterPath(".\\save"); 
		dialog.setFileName("save.txt");
		String loadPath = dialog.open();
		otherDiagram.clear();
		try {
			if (loadPath != null)
				sm.load(otherDiagram, loadPath);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ge.setDiagram(otherDiagram);
		
		if (level == 3)
			this.maxClues = (int) Math.ceil((double)otherDiagram.getClues().size()/(double)5);
		else if (level == 2)
			this.maxClues = (int) Math.ceil((double)otherDiagram.getClues().size()/(double)2);
		else
			this.maxClues = otherDiagram.getClues().size();
		
		// build diagram
		canvas = buildDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.setBackground(ColorConstants.white);
		
		
		
		
		
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		
		
		//menu archivo
		MenuItem fileItemFile = new MenuItem (bar, SWT.CASCADE);
		fileItemFile.setText ("&Archivo");
		
		Menu submenuFile = new Menu (shell, SWT.DROP_DOWN);
		fileItemFile.setMenu (submenuFile);
		
		
		MenuItem itemLoad = new MenuItem (submenuFile, SWT.PUSH);
		itemLoad.setText ("&Cargar otro nivel \tCtrl+C");
		itemLoad.setAccelerator (SWT.MOD1 + 'C');
		itemLoad.addListener (SWT.Selection, e -> {
												  FileDialog dialog2 = new FileDialog(shell, SWT.SAVE);
												  dialog2.setFilterNames(new String[] { "Text Files", "All Files (*.*)" });
												  dialog2.setFilterExtensions(new String[] { "*.txt", "*.*" }); 
												  dialog2.setFilterPath(".\\save"); 
												  dialog2.setFileName("save.txt");
												  String loadPath2 = dialog2.open();
												  otherDiagram.clear();
												  try {
													  if(loadPath2 != null)
														  sm.load(otherDiagram, loadPath2);
												} catch (ClassNotFoundException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												ge.setDiagram(otherDiagram);
												log.clear();
		});
		
		MenuItem itemExit = new MenuItem (submenuFile, SWT.PUSH);
		itemExit.setText ("&Salir \tCtrl+S");
		itemExit.setAccelerator (SWT.MOD1 + 'S');
		itemExit.addListener (SWT.Selection, e -> {
												   log.add("Salio");
												   try {
													sm.createLog(log, "Logs/logUser.txt", "Jugador");
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												   shell.dispose();});
		
		
		
		
		//menu agregar
		MenuItem fileItemAdd = new MenuItem (bar, SWT.CASCADE);
		fileItemAdd.setText ("Ag&regar");

		Menu submenuAdd = new Menu (shell, SWT.DROP_DOWN);
		fileItemAdd.setMenu (submenuAdd);
		
		//clases
		MenuItem itemAddClass = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddClass.setText ("&Clase \tCtrl+C");
		itemAddClass.setAccelerator (SWT.MOD1 + 'C');
		itemAddClass.addListener(SWT.Selection, e -> {NewClass nc = new NewClass();
													  nc.open(shell,programName,myDiagram,false,false,log);
													  redraw(shell);
													  
													  });
		
		MenuItem itemAddAbstractClass = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddAbstractClass.setText ("Clase &Abstracta \tCtrl+A");
		itemAddAbstractClass.setAccelerator (SWT.MOD1 + 'A');
		itemAddAbstractClass.addListener(SWT.Selection, e -> {NewClass nc = new NewClass();
													  nc.open(shell,programName,myDiagram,false,true,log);
													  redraw(shell);
													  });

		MenuItem itemAddInterfaceClass = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddInterfaceClass.setText ("Clase &Interface \tCtrl+F");
		itemAddInterfaceClass.setAccelerator (SWT.MOD1 + 'F');
		itemAddInterfaceClass.addListener(SWT.Selection, e -> {NewClass nc = new NewClass();
										  nc.open(shell,programName,myDiagram,true,false,log);
										  redraw(shell);
										  });
		///atributos y metodos
		MenuItem itemAddAttribute = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddAttribute.setText ("Atri&buto \tCtrl+T");
		itemAddAttribute.setAccelerator (SWT.MOD1 + 'T');
		itemAddAttribute.addListener(SWT.Selection, e -> {AddAttrib na = new AddAttrib();
														 na.open(shell,programName,myDiagram,log);
														 redraw(shell);
										  				 });
		
		MenuItem itemAddMethods = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddMethods.setText ("&Metodo \tCtrl+M");
		itemAddMethods.setAccelerator (SWT.MOD1 + 'M');
		itemAddMethods.addListener(SWT.Selection, e -> {AddMethod na = new AddMethod();
													   na.open(shell,programName,myDiagram,log);
													   redraw(shell);
													   });
														
		
		//relaciones
		MenuItem itemAddDoubleAssociation = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddDoubleAssociation.setText ("Asociacion &doble\tCtrl+D");
		itemAddDoubleAssociation.setAccelerator (SWT.MOD1 + 'D');
		itemAddDoubleAssociation.addListener(SWT.Selection, e -> {NewRelation nda = new NewRelation();
																 nda.open(shell,programName,myDiagram,false,true,false,false,false,false,log);
																 redraw(shell);
																 });
		
		MenuItem itemAddSimpleAssociation = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddSimpleAssociation.setText ("Aso&ciacion &simple\tCtrl+P");
		itemAddSimpleAssociation.setAccelerator (SWT.MOD1 + 'P');
		itemAddSimpleAssociation.addListener(SWT.Selection, e -> {NewRelation nsa = new NewRelation();
							  									 nsa.open(shell,programName,myDiagram,true,false,false,false,false,false,log);
							  									 redraw(shell);
							  									 });
		
		MenuItem itemAddInherits = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddInherits.setText ("&Herencia\tCtrl+H");
		itemAddInherits.setAccelerator (SWT.MOD1 + 'H');
		itemAddInherits.addListener(SWT.Selection, e -> {NewRelation ni = new NewRelation();
														ni.open(shell,programName,myDiagram,false,false,false,false,true,false,log);
														redraw(shell);
														});
		
		MenuItem itemAddComposition = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddComposition.setText ("Composicio&n\tCtrl+O");
		itemAddComposition.setAccelerator (SWT.MOD1 + 'O');
		itemAddComposition.addListener(SWT.Selection, e -> {NewRelation nc = new NewRelation();
														   nc.open(shell,programName,myDiagram,false,false,true,false,false,false,log);
														   redraw(shell);
														   });
		
		MenuItem itemAddAggregation = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddAggregation.setText ("Agregac&ion\tCtrl+R");
		itemAddAggregation.setAccelerator (SWT.MOD1 + 'R');
		itemAddAggregation.addListener(SWT.Selection, e -> {NewRelation na = new NewRelation();
														   na.open(shell,programName,myDiagram,false,false,false,true,false,false,log);
														   redraw(shell);
														   });
		
		
		MenuItem itemAddFunctionalDependency = new MenuItem (submenuAdd, SWT.PUSH);
		itemAddFunctionalDependency.setText ("De&pendencia funcional\tCtrl+U");
		itemAddFunctionalDependency.setAccelerator (SWT.MOD1 + 'U');
		itemAddFunctionalDependency.addListener(SWT.Selection, e -> {NewRelation nd = new NewRelation();
																	nd.open(shell,programName,myDiagram,false,false,false,false,false,true,log);
																    redraw(shell);
																    });
		
		
		
		
		//menu eliminar
		MenuItem fileItemDelete = new MenuItem (bar, SWT.CASCADE);
		fileItemDelete.setText ("&Eliminar");

		Menu submenuDelete = new Menu (shell, SWT.DROP_DOWN);
		fileItemDelete.setMenu (submenuDelete);
		
		
		MenuItem deleteClass = new MenuItem (submenuDelete, SWT.PUSH);
		deleteClass.setText ("Eliminar &clase \tCtrl+X");
		deleteClass.setAccelerator (SWT.MOD1 + 'X');
		deleteClass.addListener(SWT.Selection, e -> {DeleteComponent dc = new DeleteComponent();
												    dc.open(shell, programName, myDiagram,1,log);
												    redraw(shell);
												    });
		
		MenuItem deleteRelation = new MenuItem (submenuDelete, SWT.PUSH);
		deleteRelation.setText ("Eliminar &relacion \tCtrl+Q");
		deleteRelation.setAccelerator (SWT.MOD1 + 'Q');
		deleteRelation.addListener(SWT.Selection, e -> {DeleteComponent dr = new DeleteComponent();
													   dr.open(shell, programName, myDiagram,0,log);
													   redraw(shell);
													   });
		MenuItem deleteAttribute = new MenuItem (submenuDelete, SWT.PUSH);
		deleteAttribute.setText ("Eliminar atribu&to\tCtrl+N");
		deleteAttribute.setAccelerator (SWT.MOD1 + 'N');
		deleteAttribute.addListener(SWT.Selection, e -> {DeleteAttributeOrMethod dr = new DeleteAttributeOrMethod();
													    dr.open(shell, programName, myDiagram,0,log);
													    redraw(shell);
													    });
		
		MenuItem deleteMethod = new MenuItem (submenuDelete, SWT.PUSH);
		deleteMethod.setText ("Eliminar &metodo\tCtrl+K");
		deleteMethod.setAccelerator (SWT.MOD1 + 'K');
		deleteMethod.addListener(SWT.Selection, e -> {DeleteAttributeOrMethod dr = new DeleteAttributeOrMethod();
													 dr.open(shell, programName, myDiagram,1,log);
													 redraw(shell);
													 });
		
		
		
		//menu archivo
		MenuItem fileItemClue = new MenuItem (bar, SWT.CASCADE);
		fileItemClue.setText ("&Pistas");
		
		Menu submenuClue = new Menu (shell, SWT.DROP_DOWN);
		fileItemClue.setMenu (submenuClue);
		
		
		
		MenuItem itemClue = new MenuItem (submenuClue, SWT.PUSH);
		itemClue.setText ("&Ver pista siguiente \tCtrl+V");
		itemClue.setAccelerator (SWT.MOD1 + 'V');
		itemClue.addListener (SWT.Selection, e -> {ShowClue sc = new ShowClue();
												  if (numClue < otherDiagram.getClues().values().toArray().length && numClue < maxClues){
													  sc.show(shell, programName, (String)otherDiagram.getClues().values().toArray()[numClue],log);
													  numClue++;
												  }
												  else{
													  sc.show(shell, programName, "NO HAY MAS PISTAS, SE VOLVERA A LA PRIMERA",log);
													  numClue = 0;
												  }
												  
												  });
		MenuItem itemClueR = new MenuItem (submenuClue, SWT.PUSH);
		itemClueR.setText ("V&olver a la primer pista \tCtrl+O");
		itemClueR.setAccelerator (SWT.MOD1 + 'O');
		itemClueR.addListener (SWT.Selection, e -> {numClue = 0;});
		
		MenuItem itemSuperClue = new MenuItem (submenuClue, SWT.PUSH);
		itemSuperClue.setText ("Mostrar super pista \tCtrl+Z");
		itemSuperClue.setAccelerator (SWT.MOD1 + 'Z');
		itemSuperClue.addListener (SWT.Selection, e -> {
														ShowSuperClue sc = new ShowSuperClue();
														sc.show(shell, programName, otherDiagram, log);
		});
		
		
		
		
		//menu ayuda
		MenuItem fileItemHelp = new MenuItem (bar, SWT.CASCADE);
		fileItemHelp.setText ("A&yuda");

		Menu submenuHelp = new Menu (shell, SWT.DROP_DOWN);
		fileItemHelp.setMenu (submenuHelp);
		
		MenuItem info = new MenuItem(submenuHelp,SWT.PUSH);
		info.setText("&Informacion \tCtrl+I");
		info.setAccelerator(SWT.MOD1 + 'I');
		info.addListener(SWT.Selection, e -> {Information ni =  new Information();
											 ni.open(shell, programName);
											 });
		
		
		
		//menu limpiar
		MenuItem fileClear = new MenuItem (bar, SWT.CASCADE);
		fileClear.setText ("&Limpiar");
		fileClear.addListener(SWT.Selection, e -> {myDiagram.clear();
		  									 	  redraw(shell);
		  									 	  numClue = 0;
		  									 	  log.add("Limpio toda la pantalla");
		  										  });
		
		//menu fin de juego (listo)
		MenuItem fileReady = new MenuItem (bar, SWT.CASCADE);
		fileReady.setText ("&Listo");
		fileReady.addListener(SWT.Selection, e -> {
												  WinWindows ww = new WinWindows();
												  if (ge.equals(myDiagram)){
													  ww.open(shell, programName, "¡Acertaste!");
													  log.add("Gano");
												  }
												  else{
													  ww.open(shell, programName, "Fallaste, intenta de nuevo");
												  log.add("Perdio");
												  }
												  try {
													sm.createLog(log, "Logs/logUser.txt", "Jugador");
												} catch (IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												  });
		
		
		
		
		
		
		
		// open and wait until closing
				shell.open();
				while (!shell.isDisposed())
					while (!display.readAndDispatch())
						display.sleep();
				fatherShell.setEnabled(true);
	}
	
	
	private void redraw(Composite parent){
		canvas.dispose();
		canvas = null;
		canvas = new Canvas(shell,SWT.DOUBLE_BUFFERED);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.setBackground(ColorConstants.white);
		Figure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);
		draw(root);
		sizeX = shell.getBounds().width;
		sizeY = shell.getBounds().height;
		posSX = shell.getBounds().x;
		posSY = shell.getBounds().y;
		shell.pack();
		shell.setSize(sizeX, sizeY);
		shell.setLocation(posSX, posSY);
		shell.redraw();
	}
	
	
	private Canvas buildDiagram(Composite parent) {

		// instantiate root figure
		Figure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());

		// insantiate a canvas on which to draw
		Canvas LocalCanvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		LocalCanvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(LocalCanvas);
		lws.setContents(root);

		// this code for drawing
		
		
		
		draw(root);             
		//
		return LocalCanvas;
	}
	
	private void draw(Figure root){
		
		for (umlResources.Class c: myDiagram.getClasses()){
			Figure f = c.draw();
			figures.put(c.getTittle(), f);
			root.add(f, new Rectangle(new Point(c.getPosX(), c.getPosY()), f.getPreferredSize()));
		}
			
		for(Relation r: myDiagram.getRelations()){
			root.add(r.draw(figures.get(r.getFather().getTittle()), figures.get(r.getSon().getTittle())));
		}
		
		
	}
	
	
	
}
	

