package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, MouseListener, KeyListener {


	private static final long serialVersionUID = 1L;
	public static FlappyBird flappybird;
	public final int width = 900, height = 500;
	// object of how bird will look
	public Rectangle bird; // size color and posititon in render class
	public Rectangle base; // size color and posititon in render class
	public Rectangle baseTop;
	public ArrayList<Rectangle> columns;// number of columns will be generated using the array list
	public Random rand;
	public int ticks, yMotion, score;// motion of the bird along y axis
	public boolean gameOver, started = false,passedThroughColumn = false, passedThroughCurrentColumn=false;;
	

	public FlappyBird() {
		JFrame frame = new JFrame();
		Timer timer = new Timer(20, this);
		rand = new Random();

		
		//listener adding 
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		//adding flappy bird panel
		frame.add(this);
		
		this.requestFocusInWindow(); 
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
	
	// Override paintComponent in FlappyBird JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint(g);
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
			for (Rectangle column : columns)
			{
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 && bird.x + bird.width / 2 < column.x + column.width / 2 + 10)
				{
					score++;
				}

				if (column.intersects(bird))
				{
					gameOver = true;

					if (bird.x <= column.x)
					{
						bird.x = column.x - bird.width;

					}
					else
					{
						if (column.y != 0)
						{
							bird.y = column.y - bird.height;
						}
						else if (bird.y < column.height)
						{
							bird.y = column.height;
						}
					}
				}
			}

			if (bird.y > height - 120 || bird.y < 0) 
			{

				gameOver = true;
			}
			if (bird.y + yMotion >= height - 120) 
			{
				bird.y = height - 120 - bird.height; // the bird will fall to the base not out of the screen
				gameOver = true;
			}
		}
		//score++;
		this.repaint();
	}

	public void repaint(Graphics g) {
		//System.out.println("Repainting");
		ImageIcon backgroundImageIcon = new ImageIcon("D:\\Program Files\\Flappy Bird\\assets\\background.png");
	    Image backgroundImage = backgroundImageIcon.getImage();

	    // Draw background image
	    g.drawImage(backgroundImage, 0, 0, width, height, this);

	 // Load base image
	    ImageIcon baseImageIcon = new ImageIcon("D:\\Program Files\\Flappy Bird\\assets\\border.png");
	    Image baseImage = baseImageIcon.getImage();

	    // Draw base image
	    g.drawImage(baseImage, 0, height - 120, width, 120, this);

		
		ImageIcon birdIcon = new ImageIcon("D:\\Program Files\\Flappy Bird\\assets\\bird.png");
	    Image birdImage = birdIcon.getImage();
	    // Draw the bird image
	    g.drawImage(birdImage, bird.x, bird.y, this);

		for (Rectangle column : columns) {
			paintColumn(g, column);// in this column are being from the array list
		}
		
		if (!started) {

			// Load the image for the "Click to start" message
	        ImageIcon startMessageIcon = new ImageIcon("D:\\Program Files\\Flappy Bird\\assets\\start.png");
	        Image startMessageImage = startMessageIcon.getImage();
	        // Draw the "Click to start" image
	        g.drawImage(startMessageImage, 125, height / 2 - 150,250,250, this);
		}
		// when the gameOver variable is true then printing a string.
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 80));
		if (gameOver) {

			// Set the rectangle coordinates and dimensions
	        int rectX = width / 4; // Adjust as needed //225
	        int rectY = height / 4; // Adjust as needed //125
	        int rectWidth = width / 2; // Adjust as needed //450
	        int rectHeight = height / 2; // Adjust as needed //250

	        System.out.println(rectX);
	        System.out.println(rectY);
	        System.out.println(rectWidth);
	        System.out.println(rectHeight);
	        // Draw the rectangle with the specified color
	        g.setColor(new Color(206, 198, 115)); // RGB values for #CEC673
	        g.fillRect(rectX +20, rectY, rectWidth-50, rectHeight);
	        
		    ImageIcon gameOverIcon = new ImageIcon("D:\\Program Files\\Flappy Bird\\assets\\gameover.png");
	        Image gameOverImage = gameOverIcon.getImage();
	        g.drawImage(gameOverImage, rectX + 140, rectY +20, 180, 70, this);

	        // Display the score on top of the game over image
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("Arial", Font.BOLD, 40));
	        String scoreText = "Score: " + score;
	        int scoreTextWidth = g.getFontMetrics().stringWidth(scoreText);
	        g.drawString(scoreText, rectX + scoreTextWidth -10, rectY + rectHeight - 90);
	        
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("Arial", Font.PLAIN, 20));
	        String text = "Click anywhere to start";
	        g.drawString(text, rectX + scoreTextWidth -30, rectY + rectHeight - 40);
		}
		// drawing the score on the screen.
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), width / 2 - 10, 100);
		}
	}

	public static void main(String[] args) {
		flappybird = new FlappyBird();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse clicked");
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
		//System.out.println("Key pressed: " + e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
	}

}