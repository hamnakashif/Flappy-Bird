package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

	public static FlappyBird flappybird;
	public final int width = 900, height = 500;
	// JLabel label = new JLabel("Hello");
	// public static FlappyBird flappybird;
	public Renderer renderer;
	// object of how bird will look
	public Rectangle bird; // size color and posititon in render class
	public Rectangle base; // size color and posititon in render class
	public Rectangle baseTop;
	public ArrayList<Rectangle> columns;// number of columns will be generated using the array list
	public Random rand;
	public int ticks, yMotion, score;// motion of the bird along y axis
	public boolean gameOver, started = false,passedThroughColumn = false;

	public FlappyBird() {
		JFrame frame = new JFrame();
		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		rand = new Random();

		frame.add(renderer);
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		frame.setTitle("Flappy Bird");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);

		bird = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();

	}

	public void addColumn(boolean start) {
		int spaceCol = 240;
		int widthCol = 80;
		int minHeight = 80;
		int maxHeight = height - spaceCol - minHeight;
		int heightCol = minHeight + rand.nextInt(maxHeight); // min height is 50 and max height is 300

		if (start) {
			columns.add(new Rectangle(width + widthCol + columns.size() * 300, height - heightCol - 120, widthCol,
					heightCol));
			// width means it is to the right of the screen and adds widthCol to it
			// move multiple columns at once
			columns.add(new Rectangle(width + widthCol + (columns.size() - 1) * 300, 0, widthCol,
					height - heightCol - spaceCol));
		} else {
			// take the last column
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, height - heightCol - 120, widthCol,
					heightCol));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, widthCol, height - heightCol - spaceCol));
		}
	}

	public void paintColumn(Graphics g, Rectangle Column) {
		g.setColor(Color.green.darker());
		g.fillRect(Column.x, Column.y, Column.width, Column.height);
	}

	public void jump() {
		// when the gameOver is false then the bird and the columns will be added
		if (gameOver) {

			bird = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameOver = false;
		}
		if (!started) {
			started = true;
		} else if (!gameOver) // if game is started and also not game over then moving along y axis
		{
			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 10;
		ticks++;
		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}
			for (int i = 0; i < columns.size(); i++) // this makes the columns be made finite times
			{
				Rectangle column = columns.get(i);
				if (column.x + column.width < 0) {
					columns.remove(column);

					if (column.y == 0) {
						addColumn(false);
					}
				}
			}
			// bird moving
			bird.y += yMotion;
			// now checking for bird collision
			for (Rectangle column : columns) {
				// for score adding,10 for to remain within the speed,when the column y is 0
				
				if (column.intersects(bird)) 
				{
					gameOver = true;
					if (bird.x <= column.x) 
					{
						bird.x = column.x - bird.width;// if bird falls then stopped by the column
					} 
					else 
					{
						// if bird hits upper or lower column then bird dies
						if (column.y != 0) 
						{
							bird.y = column.y - bird.height;
						} else if (bird.y < column.height) 
						{
							bird.y = column.height;
						}
					}
				}
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10
						&& bird.width / 2 < column.x + column.width / 2 + 10) 
				{
					score++;
				}
			}
			
			if (bird.y > height - 120 || bird.y < 0) {

				gameOver = true;
			}
			if (bird.y + yMotion >= height - 120) {
				bird.y = height - 120 - bird.height; // the bird will fall to the base not out of the screen
				gameOver = true;
			}
		}
		//score++;
		renderer.repaint();
	}

	public void repaint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.orange);
		g.fillRect(0, height - 120, width, 120);

		g.setColor(Color.green);
		g.fillRect(0, height - 120, width, 20);

		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for (Rectangle column : columns) {
			paintColumn(g, column);// in this column are being from the array list
		}

		// if the game is started then what happens
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 30));
		if (!started) {

			g.drawString("Click to start!", 75, height / 2 - 50);
		}
		// when the gameOver variable is true then printing a string.
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 80));
		if (gameOver) {

			g.drawString("Game Over", 200, height / 2 - 50);
		}
		// drawing the score on the screen.
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), width / 2 - 20, 100);
		}
	}

	public static void main(String[] args) {
		flappybird = new FlappyBird();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
	}

}
