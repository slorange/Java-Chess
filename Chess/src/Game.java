import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Game {
	
	JFrame frame;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game(){
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel content = new JPanel(new BorderLayout());
		frame.setContentPane(content);
		
		//Custom button text
		Object[] options = {"Multiplayer", "VS AI"};
		int n = JOptionPane.showOptionDialog(frame, "How would you like to play?", "Chess",
		    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		    null, options, options[1]);
		
		View v = new View(this);
		v.SetMode(n);
		v.SetSize();
		v.addMouseListener(v);
		v.addMouseMotionListener(v);
		content.add(v, BorderLayout.CENTER);
		
		frame.setBounds(-600, 1300, 100, 100);
		frame.pack();
		frame.setVisible(true);

		Controller.getInstance().startNewGame();
	}
	
	public void print(String s){
		JOptionPane.showMessageDialog(frame,s);
	}
}

