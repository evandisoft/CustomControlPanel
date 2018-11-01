package customcontrolpanel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

// sets as it's maximum size the width of the widest component
@SuppressWarnings("serial")
class NonGreedyPanel extends JPanel{
	int maxWidth=0;
	
	@Override 
	public Dimension getMaximumSize(){
		Dimension size=this.getPreferredSize();
		size.width=this.maxWidth;
		return size;
	}
	
	@Override
	public Component add(Component component){
		int width=component.getPreferredSize().width;
		if(width>maxWidth){
			maxWidth=width;
		}
		super.add(component);
		return component;
	}
}