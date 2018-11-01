package customcontrolpanel;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

// TODO! Maybe change this 
@SuppressWarnings("serial")
public class App {
	JFrame mainWindow=new JFrame();
	JTextArea outputArea=new JTextArea("");

	static App mc=null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new App();
	}	

	public App() throws HeadlessException {
		super();
		
		mainWindow.setSize(1300,800);
		mainWindow.setTitle("Custom Control Panel");
		
		mainWindow.setLayout(new BorderLayout());
		mainWindow.getContentPane().add(new ControlBoxes(),BorderLayout.NORTH);
		mainWindow.getContentPane().add(outputArea,BorderLayout.CENTER);
		
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mc=this;
	}
	
	public void refresh(){
		mainWindow.revalidate();
		mainWindow.repaint();
	}
}
