package customcontrolpanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class App {
	JFrame mainWindow=new JFrame();
	JTextArea outputArea=new JTextArea("");
	
	static App app=null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new App();
	}	

	public App() throws HeadlessException {
		super();

		outputArea.setFont(Font.decode("Monospaced"));
		
		mainWindow.setSize(1300,800);
		mainWindow.setTitle("Custom Control Panel");
		mainWindow.setLayout(new BorderLayout());
		mainWindow.getContentPane().add(new ControlBoxes(),BorderLayout.NORTH);
		JScrollPane scroll=new JScrollPane(outputArea);
		mainWindow.getContentPane().add(scroll,BorderLayout.CENTER);
		
		scroll.revalidate();
		scroll.repaint();
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		app=this;
	}
	
	public void refresh(){
		mainWindow.revalidate();
		mainWindow.repaint();
	}
}
