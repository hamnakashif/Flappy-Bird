package flappybird;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FlappyBird {

	JFrame frame = new JFrame();
	JLabel label = new JLabel("Hello");
	public static FlappyBird flappybird;
	public Renderer renderer;
	public FlappyBird(){
		
		label.setBounds(0,0,100,50);
		label.setFont(new Font(null,Font.PLAIN,25));
		
		renderer=new Renderer();
		frame.add(renderer);
		frame.add(label);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,500);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}
	
	public static void main(String[]args)
	{
		flappybird=new FlappyBird();
	}
	
}
