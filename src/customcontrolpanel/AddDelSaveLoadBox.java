package customcontrolpanel;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;


@SuppressWarnings("serial")
class AddDelSaveLoadBox extends NonGreedyPanel{
	ControlBoxes controlBoxes;
	
	public AddDelSaveLoadBox(ControlBoxes controlBoxes){
		super();
		this.controlBoxes=controlBoxes;
		
		LayoutManager l= new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(l);
		
		this.add(new AddButton());
		this.add(new DeleteButton());
		this.add(new SaveButton());
		this.add(new LoadButton());
	}
	
	class AddButton extends StretchibleButton {
		public AddButton(){
			super("Add");
		}

		public void actionPerformed(ActionEvent arg0) {
			controlBoxes.add(new ControlBox(controlBoxes));
			String[] a={"Blah","blah2"};
		}
	}
	
	class DeleteButton extends StretchibleButton {
		public DeleteButton(){
			super("Delete");
		}

		public void actionPerformed(ActionEvent arg0) {
			controlBoxes.removeSelected();
		}
	}
	
	class SaveButton extends StretchibleButton {
		public SaveButton(){
			super("Save");
		}

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class LoadButton extends StretchibleButton {
		public LoadButton(){
			super("Load");
		}

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}