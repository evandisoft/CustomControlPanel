package customcontrolpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@SuppressWarnings("serial")
public class ControlBoxes extends JPanel {
	AddDelSaveLoadBox adslBox;
	ControlBox lastSelected=null;
	
	public ControlBoxes() {
		super();
		LayoutManager l= new BoxLayout(this,BoxLayout.X_AXIS);
		this.setLayout(l);
		adslBox=new AddDelSaveLoadBox(this);
		this.add(adslBox);
	}
	
	public void clear(){
		for(Component c:this.getComponents()){
			if(c!=adslBox){
				this.remove(c);
			}
		}
		App.app.refresh();
	}
	
	public ArrayList<String> serialize(){
		ArrayList<String> strings=new ArrayList<String>();
		
		for(Component c:this.getComponents()){
			if(c!=adslBox){
				ControlBox cb=(ControlBox)c;
				strings.add(cb.nameLabel.getText());
				strings.add(cb.command);
			}
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
	
	public void save(File f){
		
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PrintWriter pw;
		try {
			pw = new PrintWriter(f);
			Point p=App.app.mainWindow.getLocation();
			Dimension d=App.app.mainWindow.getSize();
			pw.write(""+p.x+"\n");
			pw.write(""+p.y+"\n");
			pw.write(""+d.width+"\n");
			pw.write(""+d.height+"\n");
			
			for(String string:this.serialize()){
				//prin.t(string);
				pw.write(Base64.encode(string.getBytes())+"\n");
			}
			pw.flush();
			pw.close();
			App.app.refreshTitle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load(File f){
		this.clear();
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			String line;
			String xline=br.readLine();
			String yline=br.readLine();
			String widthline=br.readLine();
			String heightline=br.readLine();
			App.app.mainWindow.setLocation(
					Integer.parseInt(xline), 
					Integer.parseInt(yline)
					);
			
			App.app.mainWindow.setSize(new Dimension(
					Integer.parseInt(widthline),
					Integer.parseInt(heightline)
					));
			
			ControlBox cb;
			while((line=br.readLine())!=null){
				cb=new ControlBox(this);
				cb.nameLabel.setText(new String(Base64.decode(line)).trim());
				cb.command=(new String(Base64.decode(br.readLine())).trim());
				this.add(cb);
			}
			br.close();
			App.app.refreshTitle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
	}
}
