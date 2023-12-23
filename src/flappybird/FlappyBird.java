package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FlappyBird implements ActionListener
{

	
	public static FlappyBird flappybird;
	public final int width = 900,height=500;
	//JLabel label = new JLabel("Hello");
	//public static FlappyBird flappybird;
	public Renderer renderer;
	//object of how bird will look
	public Rectangle bird; //size color and posititon in render class
	public Rectangle base; //size color and posititon in render class
	public Rectangle baseTop;
	public ArrayList<Rectangle> columns;//number of columns will be generated using the array list
	public Random rand;
	
	public FlappyBird(){
		JFrame frame = new JFrame();
		Timer timer = new Timer(20,this);
		
		renderer = new Renderer();
		rand = new Random();
		
		frame.add(renderer);
		frame.setTitle("Flappy Bird");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		//frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		columns = new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
		
	}
	
	public void addColumn(boolean start) {
		int spaceCol = 300;
		int widthCol= 100;
		int heightCol = 50 + rand.nextInt(300); //min height is 50 and max height is 300
		
		if (start) 
		{
			columns.add(new Rectangle(width + widthCol+columns.size()*300,height-heightCol-120,widthCol,heightCol));
			//width means it is to the right of the screen and adds widthCol to it
			//move multiple columns at once
			columns.add(new Rectangle(width+widthCol+(columns.size()-1)*300,0,widthCol,height-heightCol-spaceCol));
		}
		else {
			//take the last column 
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, height-heightCol - 120, widthCol,heightCol));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, widthCol, height-heightCol-spaceCol));
		}
		
		
		
	}
	public void paintColumn(Graphics g,Rectangle Column) 
	{
		g.setColor(Color.green.darker());
		g.fillRect(Column.x, Column.y, Column.width, Column.height);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//renderer.repaint();
		
	}
	
	
	public static void main(String[]args)
	{
		flappybird = new FlappyBird();
		
	}

	
	
}
