package customcontrolpanel;


import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ControlBox extends NonGreedyPanel {
    private boolean isSelected=true;
	NameLabel nameLabel=new NameLabel();
	String command="";

	ControlBoxes controlBoxes;
	ControlBox ref=this;
	
	public ControlBox(ControlBoxes controlBoxes) {
		super();
		this.controlBoxes=controlBoxes;

		LayoutManager l= new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(l);
		this.add(nameLabel);
		this.add(new ExecuteButton());
		this.add(new DeleteButton());
		this.add(new EditButton());
	}
	
	
	public boolean getIsSelected(){
		return isSelected;
	}
	
	public void select(boolean selected){
		isSelected=selected;
		
		if(selected){
			if(controlBoxes.lastSelected!=null && controlBoxes.lastSelected!=ref){
				controlBoxes.lastSelected.select(false);
			}
			controlBoxes.lastSelected=this;
			App.app.refresh();
		}	
	}
	
	public void getValues(){
		final JTextField name=new JTextField(nameLabel.getText());
		// TODO! Fix focus so that it is on name field if possible.
		// 		If necessary, create a non-modal popup.
		// Tried AncestorListener and ComponentListener in conjunction with
		// 		requestFocus, and requestFocusInWindow
		JTextField command=new JTextField(this.command);
		JComponent[] components={new JLabel("Name:"),name,new JLabel("Command"),command}; 
		JOptionPane.showConfirmDialog(App.app.mainWindow, components);
		nameLabel.setText(name.getText());
		this.command=command.getText();
	}
	
	class NameLabel extends JLabel {
		public NameLabel(){
			super("");
			this.setMaximumSize(new Dimension(1000,40));
			this.setPreferredSize(new Dimension(0,25));
			this.setHorizontalAlignment(JLabel.CENTER);
		}
	}
	
	class ExecuteButton extends StretchibleButton {
		public ExecuteButton(){
			super("Execute");
		}

		public void actionPerformed(ActionEvent arg0) {
			if(command==""){
				App.app.outputArea.append("No command entered.\n");
				return;
			}
			try {
				App.app.outputArea.append("+++++++++++++++++++++++\n");
				App.app.outputArea.append("Running "+command+"\n");
				App.app.outputArea.append("+++++++++++++++++++++++\n");
				
				Process process=Runtime.getRuntime().exec(command);
				process.waitFor();
				
				BufferedReader r=new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				// TODO Maybe make this asynchronous so that it can be
				// cancelled. Probably want an outputarea for each controlbox
				// too, so we can run multiple processes at the same time.
				
				while((line=r.readLine())!=null){
					App.app.outputArea.append(line+"\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class DeleteButton extends StretchibleButton {
		public DeleteButton(){
			super("Delete");
		}

		public void actionPerformed(ActionEvent arg0) {
			controlBoxes.remove(ref);
			App.app.refresh();
		}
	}
	
	class SelectButton extends StretchibleButton {
		public SelectButton(){
			super("Select");
		}

		public void actionPerformed(ActionEvent arg0) {
			ref.select(true);
		}
	}
	
	class EditButton extends StretchibleButton {
		public EditButton(){
			super("Edit");
		}
		
		public void actionPerformed(ActionEvent arg0) {
			getValues();
			nameLabel.revalidate();
			nameLabel.repaint();
		}
	}
}