package flappybird;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Renderer extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawBackground(g);
		drawBird(g);
		drawGround(g);
		drawBaseTop(g);
	}

	private void drawBaseTop(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.green);
		g.fillRect(0, FlappyBird.flappybird.height - 120, FlappyBird.flappybird.width, 20);
	}

	private void drawGround(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.orange);
		g.fillRect(0, FlappyBird.flappybird.height - 110, FlappyBird.flappybird.width, 120);
	}

	private void drawBird(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		FlappyBird.flappybird.bird = new Rectangle(FlappyBird.flappybird.width / 2 - 10,
                FlappyBird.flappybird.height / 2 - 10, 20, 20);
		g.fillRect(FlappyBird.flappybird.bird.x, FlappyBird.flappybird.bird.y, 
				FlappyBird.flappybird.bird.width, FlappyBird.flappybird.bird.height);
	}

	private void drawBackground(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
        g.fillRect(0, 0, getWidth(), getHeight());
	}

}
