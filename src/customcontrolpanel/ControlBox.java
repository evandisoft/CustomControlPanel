package customcontrolpanel;


import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ControlBox extends NonGreedyPanel {
	//deprecated
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
	
	// deprecated
	public boolean getIsSelected(){
		return isSelected;
	}
	
	// deprecated
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
	
	
	/**
	 * Create a popup that asks the user for a name and command for this controlbox.
	 * Then save that information in this controlbox.
	 */
	public void getValues(){
		final JTextField name=new JTextField(nameLabel.getText());
		
		JTextField command=new JTextField(this.command);
		JComponent[] components={new JLabel("Name:"),name,new JLabel("Command"),command}; 
		
		// I don't think one can change the focus in a JOptionPane dialog programmatically without 
		// starting a thread prior to the dialog being open and then delaying the focus request
		// until after it will have opened. Tried a few other ways and they did not work.
		Thread t=new Thread(){
			public void run(){
				try {
					Thread.sleep(100);
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							name.requestFocusInWindow();		
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		
		JOptionPane.showConfirmDialog(App.app.mainWindow, components);
		nameLabel.setText(name.getText());
		this.command=command.getText();
	}
	
	
	/**
	 * A type of JLabel that allowed stretching by setting large maximum size.
	 * Sets preferred width to 0 to prevent it from making parent wider.
	 * Sets preferred height to 25 to ensure that it takes as much space as the buttons.
	 */
	class NameLabel extends JLabel {
		public NameLabel(){
			super("No Action");
			this.setMaximumSize(new Dimension(1000,40));
			this.setPreferredSize(new Dimension(0,25));
			this.setHorizontalAlignment(JLabel.CENTER);
		}
	}
	
	Thread thread;
	Process process;
	class ExecuteButton extends StretchibleButton {
		public ExecuteButton(){
			super("Execute");
		}

		public void actionPerformed(ActionEvent arg0) {
			if(command.equals("")){
				App.app.outputArea.append("No command entered.\n");
				return;
			}
			App.app.outputArea.append("+++++++++++++++++++++++\n");
			App.app.outputArea.append("Running "+command+"\n");
			App.app.outputArea.append("+++++++++++++++++++++++\n");
			
			// Asynchronously start a process, and pass its input to the app's outputArea.
			thread=new Thread(){
				public void run(){
					try {
						process=Runtime.getRuntime().exec(command);
						
						InputStream inputStream=process.getInputStream();
						
						while(process.isAlive() || inputStream.available()>0){
							
							try {
								StringBuffer sb=new StringBuffer();
								while(inputStream.available()>0){
									sb.append((char)inputStream.read());
								}
								
								final String finalbuffer=new String(sb);
								
								SwingUtilities.invokeLater(new Runnable(){

									public void run() {
										App.app.outputArea.append(finalbuffer);
									}
									
								});
								Thread.sleep(100);
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
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
	
	// deprecated
	class SelectButton extends StretchibleButton {
		public SelectButton(){
			super("Select");
		}

		public void actionPerformed(ActionEvent arg0) {
			ref.select(true);
		}
	}
	
	// make a popup appear that allows the user to edit this controlbox
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