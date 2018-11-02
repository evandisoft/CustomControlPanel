package customcontrolpanel;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
class AddDelSaveLoadBox extends NonGreedyPanel{
	ControlBoxes controlBoxes;
	
	public AddDelSaveLoadBox(ControlBoxes controlBoxes){
		super();
		this.controlBoxes=controlBoxes;
		
		LayoutManager l= new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(l);
		
		this.add(new AddButton());
		this.add(new TitleButton());
		this.add(new ClearButton());
		this.add(new SaveButton());
		this.add(new SaveAsButton());
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
	
	class TitleButton extends StretchibleButton {
		public TitleButton(){
			super("Title");
		}

		public void actionPerformed(ActionEvent arg0) {
			String result=JOptionPane.showInputDialog("New Title:",App.programName);
			App.programName=result;
			App.app.refreshTitle();
		}
	}
	
	class ClearButton extends StretchibleButton {
		public ClearButton(){
			super("Clear O");
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
			if(App.app.sessionFile!=null){
				controlBoxes.save(App.app.sessionFile);
			}
			//App.app.mainWindow.dispose();
		}
	}
	
	/**
	 * Open a save dialog for the user and then save the session in the chosen file.
	 *
	 */
	class SaveAsButton extends StretchibleButton {
		public SaveAsButton(){
			super("SaveAs");
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
	
	
	
	// Open a load file dialog for the user and then load the chosen file in as the current session.
	class LoadButton extends StretchibleButton {
		public LoadButton(){
			super("Load");
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
			controlBoxes.load(f);
		}
	}
	
	
}