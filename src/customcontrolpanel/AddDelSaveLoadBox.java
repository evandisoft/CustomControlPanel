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
			if(App.app.sessionFile!=null){
				fc.setSelectedFile(App.app.sessionFile);
			}
			int retval=fc.showOpenDialog(App.app.mainWindow);
			if(retval!=JFileChooser.APPROVE_OPTION){
				return;
			}
			File f=fc.getSelectedFile();
			controlBoxes.save(f);			
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
			controlBoxes.load(f);
		}
	}
}