package customcontrolpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import java.util.Base64;

/**
 * Panel that holds all the customizable control boxes
 *
 */
@SuppressWarnings("serial")
public class ControlBoxes extends JPanel {
	ControlBox lastSelected=null;
	
	public ControlBoxes() {
		super();
		LayoutManager l= new BoxLayout(this,BoxLayout.X_AXIS);
		this.setLayout(l);
		
	}
	
	public void clear(){
		for(Component c:this.getComponents()){
			this.remove(c);
		}
		App.app.refresh();
	}
	
	/**
	 * Serialize the control boxes into an array of strings that alternates between name and command.
	 * 
	 * @return
	 */
	public ArrayList<String> serialize(){
		ArrayList<String> strings=new ArrayList<String>();
		
		for(Component c:this.getComponents()){
			ControlBox cb=(ControlBox)c;
			strings.add(cb.nameLabel.getText());
			strings.add(cb.command);
		}
		
		return strings;
	}
	
	public Component add(ControlBox controlBox){
		super.add(controlBox);
		
		controlBox.select(true);
		lastSelected=controlBox;
		
		App.app.refresh();
		
		return controlBox;
	}
	
	// deprecated
	public Component removeSelected(){
		if(lastSelected!=null){
			super.remove(lastSelected);
		}
		
		lastSelected=null;
		App.app.refresh();
		
		return lastSelected;
	}
	
	/**
	 * Save the current session into the given file, f.
	 * 
	 * @param f
	 */
	public void save(File f){
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Base64.Encoder ase64=Base64.getEncoder();
		PrintWriter pw;
		try {
			pw = new PrintWriter(f);
			Point p=App.app.mainWindow.getLocation();
			Dimension d=App.app.mainWindow.getSize();
			
			// store program name
			pw.write(ase64.encodeToString(App.programName.getBytes())+"\n");

			// store the window location data in the session file
			pw.write(""+p.x+"\n");
			pw.write(""+p.y+"\n");
			// store the window size data in the session file
			pw.write(""+d.width+"\n");
			pw.write(""+d.height+"\n");
			
			// store all the control boxes data in the session file
			// encode as base64 to ensure newlines don't mess with the format
			for(String string:this.serialize()){
				pw.write(new String(ase64.encodeToString(string.getBytes()))+"\n");
			}
			pw.flush();
			pw.close();
			App.app.sessionFile=f;
			App.app.refreshTitle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the session data from the file, f.
	 * 
	 * @param f
	 */
	public void load(File f){
		this.clear();
		Base64.Decoder ase64=Base64.getDecoder();
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			String line;
			
			// load program name
			App.programName=new String(ase64.decode(br.readLine())).trim();
			
			// load window position from the session file
			String xline=br.readLine();
			String yline=br.readLine();
			App.app.mainWindow.setLocation(
					Integer.parseInt(xline), 
					Integer.parseInt(yline)
					);
			
			// load window size from the session file
			String widthline=br.readLine();
			String heightline=br.readLine();
			App.app.mainWindow.setSize(new Dimension(
					Integer.parseInt(widthline),
					Integer.parseInt(heightline)
					));
			
			// load the control box data from the rest of the session file
			ControlBox cb;
			while((line=br.readLine())!=null){
				cb=new ControlBox(this);
				cb.nameLabel.setText(new String(ase64.decode(line)).trim());
				//prin.t(new String(ase64.decode(line)).trim());
				line=br.readLine();
				cb.command=new String(ase64.decode(line)).trim();
				//prin.t(new String((line)).trim());
				this.add(cb);
			}
			br.close();
			App.app.refreshTitle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
