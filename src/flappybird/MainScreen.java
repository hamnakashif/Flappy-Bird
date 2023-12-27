package flappybird;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MainScreen extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	JButton game = new JButton("Start Game");
	
	MainScreen() throws IOException{
		try {
	    BufferedImage head = ImageIO.read(new File("D:\\Program Files\\Flappy Bird\\assets\\Heading.png"));
        BufferedImage image = ImageIO.read(new File("D:\\Program Files\\Flappy Bird\\assets\\background.png"));
        BufferedImage scaledImage = scaleImage(image, 900, 500);

        ImageIcon backgroundIcon = new ImageIcon(scaledImage);
        ImageIcon headIcon = new ImageIcon(head);

        JLabel label = new JLabel(backgroundIcon);
        JLabel labelHead = new JLabel(headIcon);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 500));

        label.setBounds(0, 0, 900, 500);
        labelHead.setBounds(0, 0, 200, 300);

        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(labelHead, JLayeredPane.PALETTE_LAYER);

        frame.setLayout(new BorderLayout());
        frame.add(layeredPane, BorderLayout.CENTER);

        // Calculate the bounds for center top placement
        int screenWidth = 900; // Adjust to your screen width
        int imageWidth = headIcon.getIconWidth();
        int imageHeight = headIcon.getIconHeight();
        int labelXHead = (screenWidth - imageWidth) / 2;
        int labelYHead = 20;
        labelHead.setBounds(labelXHead, labelYHead, imageWidth, imageHeight);

        // Optional: Adding a button to the main screen
       
        game.setBounds(380, 270, 140, 40);
        game.setFont(new Font("Arial", Font.BOLD, 18));
        Color buttonColor = new Color(0x18, 0x99, 0xA4);
        game.setBackground(buttonColor);
        game.addActionListener(this);

      //setting the size of the console screen
      		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      		frame.setSize(900,500);
      		frame.setLocationRelativeTo(null);//center of the screen
        layeredPane.add(game, JLayeredPane.PALETTE_LAYER);

        frame.setVisible(true);

    } catch (IOException e) {
        e.printStackTrace();
    }
	}
	private BufferedImage scaleImage(BufferedImage image,int targetWidth,int targetHeight) {
		BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		scaledImage.getGraphics().drawImage(image.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return scaledImage;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== game) {
			showGameScreen();
		}
	}
	private void showGameScreen() {
		frame.getContentPane().removeAll();
        frame.dispose(); // Close the main screen
        FlappyBird flappyBird = new FlappyBird();
	}
	public static void main(String[] args) {
        try {
            new MainScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
	
	//what will happen when you press the button
}