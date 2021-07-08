package pouryapb.dooz;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Boxes extends Main{

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
	private JButton[][] boxArray = new JButton[10][10];
	private boolean[][] checker = new boolean[10][10];
	private int removedBoxes = 0;
	private int moves, movesNotChanged;
	private JPanel p, e;
	private Records rec = new Records();
	private String playerNameLocal;
	private JLabel avatarLocal;
	public Boxes(int m) {
		// setting moves for checking the end of the game
		moves = m; // this one changes
		movesNotChanged = m; // this one doesn't
		
		// building an array of JButtons with random colors and setting their locations correctly
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boxArray[i][j] = new JButton();
				boxArray[i][j].setSize(30, 30);
				boxArray[i][j].setBackground(color[new Random().nextInt(10)]);
				boxArray[i][j].putClientProperty("X", i);
				boxArray[i][j].putClientProperty("Y", j);
				boxArray[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// finding neighbors with the same color
						findNeighbors((int)((JButton) arg0.getSource()).getClientProperty("X"), (int)((JButton) arg0.getSource()).getClientProperty("Y"));
						
						// counting neighbors with the same color
						int t = 0;
						for (int i = 0; i < 10; i++) {
							for (int j = 0; j < 10; j++) {
								if (checker[i][j]) {
									t++;
								}
							}
						}
						
						// making JButtons invisible if neighbors are more than 2
						if (t >= 3) {
							removedBoxes += t;
							moves--;
							for (int i = 0; i < 10; i++) {
								for (int j = 0; j < 10; j++) {
									if (checker[i][j]) {
										boxArray[i][j].setVisible(false);
									}
								}
							}
						}
						
						// applying gravities
						gravityDown();
						gravityLeft();
						
						// reseting neighbor checker
						for (int i = 0; i < 10; i++) {
							for (int j = 0; j < 10; j++) {
								checker[i][j] = false;
							}
						}
						
						// checking the end :|
						checkEnd();
					}
				});
				
				// setting neighbor checker with JButtons to false (default value)
				checker[i][j] = false;
			}
		}
	}
	
	// paints boxes on a panel in a specified location
	public void painOn(JPanel panel, int x, int y) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				boxArray[i][j].setLocation(x + (30 * i), y + (30 * j));
				panel.add(boxArray[i][j]);
			}
		}
		// saves the panel's recurse for later use
		p = panel;
	}
	
	// takes the end panel for later use
	public void giveEndPanel(JPanel p) {
		e = p;
	}
	
	// finds neighbors with the same color
	private void findNeighbors(int x, int y) {
		int i = x - 1, j = y - 1;
		// the box that is clicked
		checker[x][y] = true;
		
		// turns around the box like this ↓
		// 
		//start → → →
		// 	  ↑ ■ ■ ■ ↓
		// 	  ↑ ■ ■ ■ ↓
		//	  ↑ ■ ■ ■ ↓
		//		← ← ←
		//
		for (int k = 1; k <= 8; k++) {
			if (k <= 3) {
				try {
					if (boxArray[x][y].getBackground().equals(boxArray[i][j].getBackground()) && !checker[i][j] && boxArray[i][j].isVisible()) {
						checker[i][j] = true;
						findNeighbors(i, j);
					}
				} catch (Exception e) {}
				i++;
				continue;
			}
			if (k >= 4 && k <= 5) {
				if (k == 4)
					i--;
				j++;
				try {
					if (boxArray[x][y].getBackground().equals(boxArray[i][j].getBackground())  && !checker[i][j] && boxArray[i][j].isVisible()) {
						checker[i][j] = true;
						findNeighbors(i, j);
					}
				} catch (Exception e) {}
				continue;
			}
			if (k >= 6 && k <= 7) {
				i--;
				try {
					if (boxArray[x][y].getBackground().equals(boxArray[i][j].getBackground())  && !checker[i][j] && boxArray[i][j].isVisible()) {
						checker[i][j] = true;
						findNeighbors(i, j);
					}
				} catch (Exception e) {}
				continue;
			}
			if (k == 8) {
				j--;
				try {
					if (boxArray[x][y].getBackground().equals(boxArray[i][j].getBackground())  && !checker[i][j] && boxArray[i][j].isVisible()) {
						checker[i][j] = true;
						findNeighbors(i, j);
					}
				} catch (Exception e) {}
				continue;
			}
		}
	}
	
//	private void gravityDownAnimation(JButton btn) {
//		Timer t = new Timer();
//		t.scheduleAtFixedRate(new TimerTask() {
//			int x = 0;
//			
//			@Override
//			public void run() {
//				btn.setLocation(btn.getX(), btn.getY() + 1);
//				if (x == 30) {
//					t.cancel();
//					t.purge();
//					return;
//				}
//				x++;
//			}
//		}, 0, 50);
//	}
//	
//	private void gravityLeftAnimation(JButton btn) {
//		Timer t = new Timer();
//		t.scheduleAtFixedRate(new TimerTask() {
//			int x = 0;
//			
//			@Override
//			public void run() {
//				btn.setLocation(btn.getX() - 1, btn.getY());
//				if (x == 30) {
//					t.cancel();
//					t.purge();
//					return;
//				}
//				x++;
//			}
//		}, 0, 50);
//	}
	
	// replaces the invisible box's color with the color of the box above it self
	private void gravityDown() {
		boolean checkEmptyTop = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (!boxArray[i][j].isVisible()) {
					for (int k = j; k > 0; k--) {
						if (!boxArray[i][k - 1].isVisible()) {
							checkEmptyTop = false;
						}
						else {
							checkEmptyTop = true;
							break;
						}
					}
					if (checkEmptyTop) {
						for (int k = j; k > 0; k--) {
							if (boxArray[i][k - 1].isVisible()) {
//								gravityDownAnimation(boxArray[i][k - 1]);
								boxArray[i][k - 1].setVisible(false);
								boxArray[i][k].setBackground(boxArray[i][k - 1].getBackground());
								boxArray[i][k].setVisible(true);
							}
						}
					}
				}
			}
		}
	}
	
	// replaces the color of the invisible box with the color of the box from its right
	private void gravityLeft() {
		boolean checkEmptyRight = false;
		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < 10; j++) {
				if (!boxArray[i][j].isVisible()) {
					for (int k = i; k < 9; k++) {
						if (!boxArray[k + 1][j].isVisible()) {
							checkEmptyRight = false;
						}
						else {
							checkEmptyRight = true;
							break;
						}
					}
					if (checkEmptyRight) {
						for (int k = i; k < 9; k++) {
							if (boxArray[k + 1][j].isVisible()) {
//								gravityLeftAnimation(boxArray[k + 1][j]);
								boxArray[k + 1][j].setVisible(false);
								boxArray[k][j].setBackground(boxArray[k + 1][j].getBackground());
								boxArray[k][j].setVisible(true);
							}
						}
					}
				}
			}
		}
	}
	
	// checks the conditions of games end
	private void checkEnd() {
		// checking if there is any possible moves?
		int t = 0;
		boolean isOver = true;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (boxArray[i][j].isVisible()) {
					findNeighbors(i, j);
					for (int k = 0; k < 10; k++) {
						for (int l = 0; l < 10; l++) {
							if (checker[k][l]) {
								t++;
							}
						}
					}
					if (t > 2) {
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 10; l++) {
								checker[k][l] = false;
							}
						}
						isOver = false;
						break;
					}
					else {
						isOver = true;
						for (int k = 0; k < 10; k++) {
							for (int l = 0; l < 10; l++) {
								checker[k][l] = false;
							}
						}
					}
					t = 0;
				}
			}
			if (!isOver)
				break;
		}
		
		// if there is no possible moves or if permitted moves are out...
		if (isOver || moves == 0) {
			CardLayout layout = (CardLayout) p.getParent().getLayout();
			
			// score that is shown in the end panel
			JLabel score = new JLabel("");
			score.setBounds(376, 198, 95, 14);
			score.setHorizontalAlignment(SwingConstants.CENTER);
			e.add(score);
			// calculating the score
			int r = (int) ((100 * removedBoxes) / ((movesNotChanged - moves) * Math.pow(timer.getTime(), 1 / 3)));
			score.setText(String.valueOf(r));
			
			// showing the end panel
			layout.show(p.getParent(), "end_back");
			
			// trying to load records if failed saving a new one!
			try {
				FileInputStream myFileInputStream = new FileInputStream("records.ser");
				ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
				rec = (Records) myObjectInputStream.readObject(); 
				myObjectInputStream.close();
			} catch (Exception e) {
				try {
					FileOutputStream myFileOutputStream = new FileOutputStream("records.ser");
					ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
					myObjectOutputStream.writeObject(rec);
					myObjectOutputStream.close();
				}
				catch (Exception d) {
					e.printStackTrace();
				}
			}
			
			// making a new row of records
			rec.newRecord(r, playerNameLocal, avatarLocal);
			
			// saving changed records
			try {
				FileOutputStream myFileOutputStream = new FileOutputStream("records.ser");
				ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
				myObjectOutputStream.writeObject(rec);
				myObjectOutputStream.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// updates remaining moves in the game panel
	public void moveUpdate(JLabel l) {
		Timer tm = new Timer();
		tm.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				if (moves == 0) {
					tm.cancel();
					tm.purge();
					return;
				}
				l.setText(String.valueOf(moves));
				l.repaint();
			}
		}, 0, 1);
	}
	
	// prints records on a (records) panel
	public void printRecords(JPanel panel) {
		this.rec.printRecords(panel);
	}
	
	// gets players avatar and name for saving their records
	public void setAvatarAndName(String name, JLabel avatar) {
		playerNameLocal = name;
		avatarLocal = avatar;
	}
	
	// taking the timer counter to stop after game is finished
	public void giveTimer(TimerCount t) {
		timer = t;
	}
}
