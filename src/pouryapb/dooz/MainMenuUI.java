package pouryapb.dooz;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuUI {
	
	private Color[] color = {
			Color.blue,
			Color.cyan,
			Color.darkGray,
			Color.gray,
			Color.green,
			Color.orange,
			Color.pink,
			Color.red,
			Color.yellow,
			Color.magenta
	};
	private boolean[] makeVisible = {true, false};
	private JButton[][] boxArray = new JButton[25][5];
	
	
	// those boxes in the beginning of the game are made with this class
	// random colors an random visibility and random showing with a Timer
	
	
	public MainMenuUI() {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 5; j++) {
				boxArray[i][j] = new JButton();
				boxArray[i][j].setSize(30, 30);
				boxArray[i][j].setBackground(color[new Random().nextInt(10)]);
				boxArray[i][j].setVisible(makeVisible[new Random().nextInt(2)]);
			}
		}
	}
	
	public void paintOn(JPanel panel, int x, int y) throws ArrayIndexOutOfBoundsException{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 0, j = 0;
			int count = 0;
			@Override
			public void run() {
				count++;
				if (count == 300) {
					timer.cancel();
					timer.purge();
					return;
				}
				boxArray[i][j].setLocation(x + (30 * i), y + (30 * j));
				if (boxArray[i][j].isVisible())
					panel.add(boxArray[i][j]);
				panel.repaint();
				
				i = new Random().nextInt(25);
				j = new Random().nextInt(5);
			}
		}, 0, 10);
	}
}
