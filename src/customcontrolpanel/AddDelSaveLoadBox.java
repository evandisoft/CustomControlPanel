package customcontrolpanel;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;


@SuppressWarnings("serial")
class AddDelSaveLoadBox extends NonGreedyPanel{
	ControlBoxes controlBoxes;
	File currentFile=null;
	
	public AddDelSaveLoadBox(ControlBoxes controlBoxes){
		super();
		this.controlBoxes=controlBoxes;
		
		LayoutManager l= new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(l);
		
		this.add(new AddButton());
		this.add(new ClearButton());
		this.add(new SaveButton());
		this.add(new LoadButton());
	}
	
	class AddButton extends StretchibleButton {
		public AddButton(){
			super("Add");
		}

		public void actionPerformed(ActionEvent arg0) {
			controlBoxes.add(new ControlBox(controlBoxes));
		}
	}
	
	class ClearButton extends StretchibleButton {
		public ClearButton(){
			super("Clear");
		}

		public void actionPerformed(ActionEvent arg0) {
			App.app.outputArea.setText("");
		}
	}
	
	class SaveButton extends StretchibleButton {
		public SaveButton(){
			super("Save");
		}

		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc=new JFileChooser();
			if(currentFile!=null){
				fc.setSelectedFile(currentFile);
			}
			int retval=fc.showOpenDialog(App.app.mainWindow);
			if(retval!=JFileChooser.APPROVE_OPTION){
				return;
			}
			File f=fc.getSelectedFile();
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
				for(String string:controlBoxes.serialize()){
					//prin.t(string);
					pw.write(Base64.encode(string.getBytes())+"\n");
				}
				pw.flush();
				pw.close();
				currentFile=f;
				App.app.mainWindow.setTitle("Custom Control Panel ~ "+currentFile.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	class LoadButton extends StretchibleButton {
		public LoadButton(){
			super("Load");
		}

		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc=new JFileChooser();
			int retval=fc.showOpenDialog(App.app.mainWindow);
			if(retval!=JFileChooser.APPROVE_OPTION){
				return;
			}
			File f=fc.getSelectedFile();
			controlBoxes.clear();
			try {
				BufferedReader br=new BufferedReader(new FileReader(f));
				String line;
				ControlBox cb;
				while((line=br.readLine())!=null){
					cb=new ControlBox(controlBoxes);
					cb.nameLabel.setText(new String(Base64.decode(line)).trim());
					cb.command=(new String(Base64.decode(br.readLine())).trim());
					controlBoxes.add(cb);
				}
				br.close();
				currentFile=f;
				App.app.mainWindow.setTitle("Custom Control Panel ~ "+currentFile.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Base64DecodingException e) {
				e.printStackTrace();
			}
		}
	}
}