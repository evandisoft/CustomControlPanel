package customcontrolpanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

// sets it's maximum size as big as possible so that it can stretch
@SuppressWarnings("serial")
abstract class StretchibleButton extends JButton implements ActionListener {
	public StretchibleButton(String text){
		super(text);
		this.addActionListener(this);
	}
	
	@Override
	public Dimension getMaximumSize() {
	    Dimension size = getPreferredSize();
	    size.width = Short.MAX_VALUE;
	    return size;
	}
}