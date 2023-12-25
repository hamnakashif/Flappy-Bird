package flappybird;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainScreen implements ActionListener{

	JFrame frame = new JFrame();
	JButton game = new JButton("Start Game");
	JButton help = new JButton("Help");
	MainScreen(){
		
		//start game button layout
		game.setBounds(300,270,100,40);
		game.setFocusable(false);
		game.addActionListener(this);
		//help button layout
		help.setBounds(490,270,100,40);
		help.setFocusable(false);
		help.addActionListener(this);
		
		//adding the buttons to the main screen 
		frame.add(game);
		frame.add(help);
		
		//setting the size of the console screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,500);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	//what will happen when you press the button
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== game) {
			FlappyBird flappyBird = new FlappyBird();
		}
		
	}
}
