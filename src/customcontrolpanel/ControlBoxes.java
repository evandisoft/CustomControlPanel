package customcontrolpanel;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	
	public Component removeSelected(){
		if(lastSelected!=null){
			super.remove(lastSelected);
		}
		
		lastSelected=null;
		App.app.refresh();
		
		return lastSelected;
	}
}
