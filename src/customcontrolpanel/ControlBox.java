package customcontrolpanel;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ControlBox extends NonGreedyPanel {
    private boolean isSelected=true;
	NameLabel nameLabel=new NameLabel();
	String command="";
	StatusLabel statusLabel=new StatusLabel();
	ControlBoxes controlBoxes;
	ControlBox ref=this;
	
	public ControlBox(ControlBoxes controlBoxes) {
		super();
		this.controlBoxes=controlBoxes;
		
		LayoutManager l= new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(l);
		this.add(nameLabel);
		this.add(new ExecuteButton());
		this.add(new SelectButton());
		this.add(statusLabel);
	}
	
	public boolean getIsSelected(){
		return isSelected;
	}
	
	public void select(boolean selected){
		isSelected=selected;
		statusLabel.update(isSelected);
		if(selected){
			if(controlBoxes.lastSelected!=null){
				controlBoxes.lastSelected.select(false);
			}
			controlBoxes.lastSelected=this;
			App.mc.refresh();
		}	
	}
	
	class NameLabel extends JLabel {
		public NameLabel(){
			super("|");
			this.setPreferredSize(new Dimension(0,this.getPreferredSize().height));
		}
	}
	
	class ExecuteButton extends StretchibleButton {
		public ExecuteButton(){
			super("Execute");
		}

		public void actionPerformed(ActionEvent arg0) {
			try {
				Process process=Runtime.getRuntime().exec("systemctl");
				process.waitFor();
				BufferedReader r=new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				// TODO! Make this asynchronous
				while((line=r.readLine())!=null){
					System.out.println(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	class StatusLabel extends JLabel {
		public StatusLabel(){
			super("|");
			this.setPreferredSize(new Dimension(0,this.getPreferredSize().height));
		}
		
		public void update(boolean isSelected){
			this.setText(isSelected?"||||||||||||||||||||||||||||||||||||||||||||||||":"");
		}
	}
}