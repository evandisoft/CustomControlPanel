package customcontrolpanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class App {
	JFrame mainWindow=new JFrame();
	JTextArea outputArea=new JTextArea("");
	File sessionFile=null;
	
	static final String programName="CustomControlPanel";
	static final String version="1.1.0";
	
	static App app=null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new App(args);
	}	

	public App(String[] args) throws HeadlessException {
		app=this;
		
		
		outputArea.setFont(Font.decode("Monospaced"));
		
		mainWindow.setSize(1300,800);
		mainWindow.setTitle("Custom Control Panel");
		mainWindow.setLayout(new BorderLayout());
		final ControlBoxes cbs=new ControlBoxes();
		mainWindow.getContentPane().add(cbs,BorderLayout.NORTH);
		JScrollPane scroll=new JScrollPane(outputArea);
		mainWindow.getContentPane().add(scroll,BorderLayout.CENTER);
		
		scroll.revalidate();
		scroll.repaint();
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(args.length>0){
			sessionFile=new File(args[0]);
			if(!sessionFile.exists()){
				cbs.save(sessionFile);
			}
			cbs.load(sessionFile);
		}
		

		mainWindow.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				if(sessionFile!=null){
					cbs.save(sessionFile);
				}
			}
		});
	}
	
	public void refresh(){
		mainWindow.revalidate();
		mainWindow.repaint();
	}
	
	public void refreshTitle(){
		if(App.app.sessionFile!=null){
			App.app.mainWindow.setTitle(App.programName+" "+App.version+" ~ Session: "+App.app.sessionFile.getAbsolutePath());
		}
		else{
			App.app.mainWindow.setTitle(App.programName+" "+App.version);
		}
	}
}
