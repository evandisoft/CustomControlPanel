package customcontrolpanel;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
