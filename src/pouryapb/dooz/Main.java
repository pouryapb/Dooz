package pouryapb.dooz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class Main {

	private JFrame frmTheDooz;
	protected JTextField nameField;
	private JTextField permittedMovesField;
	protected TimerCount timer = new TimerCount();
	protected JLabel avatar = new JLabel("");
	protected JLabel playerName = new JLabel("");
	private Boxes box;
	private Records recs = new Records();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmTheDooz.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTheDooz = new JFrame();
		frmTheDooz.setTitle("The Dooz V.1.4");
		frmTheDooz.setResizable(false);
		frmTheDooz.setBounds(100, 100, 720, 480);
		frmTheDooz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTheDooz.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel mainMenu = new JPanel();
		mainMenu.setBackground(Color.decode("#ffc64e"));
		frmTheDooz.getContentPane().add(mainMenu, "main_menu");
		mainMenu.setLayout(null);
		
		JPanel preGameScreen = new JPanel();
		frmTheDooz.getContentPane().add(preGameScreen, "pre_game_screen");
		preGameScreen.setLayout(null);
		
		JPanel gameScreen = new JPanel();
		frmTheDooz.getContentPane().add(gameScreen, "game_screen");
		gameScreen.setLayout(null);
		
		JPanel records = new JPanel();
		frmTheDooz.getContentPane().add(records, "records");
		records.setLayout(null);
		
		JLabel lblRecords = new JLabel("Records");
		lblRecords.setFont(new Font("SansSerif", Font.PLAIN, 30));
		lblRecords.setForeground(Color.DARK_GRAY);
		lblRecords.setBounds(10, 11, 111, 61);
		records.add(lblRecords);
		
		// back button in records panel
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.setBackground(Color.WHITE);
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
				layout.show(mainMenu.getParent(), "main_menu");
				records.removeAll();
				records.add(lblRecords);
				records.add(btnBack_1);
			}
		});
		btnBack_1.setBounds(615, 418, 89, 23);
		records.add(btnBack_1);
		
		JPanel endPanel = new JPanel();
		endPanel.setBackground(new Color(255, 255, 255));
		frmTheDooz.getContentPane().add(endPanel, "end_back");
		endPanel.setLayout(null);
		
		JLabel gameOver = new JLabel("Game Over!");
		gameOver.setFont(new Font("SansSerif", Font.PLAIN, 22));
		gameOver.setBounds(298, 158, 118, 29);
		endPanel.add(gameOver);
		
		JLabel lblYourScore = new JLabel("your score:");
		lblYourScore.setBounds(281, 198, 71, 14);
		endPanel.add(lblYourScore);
		
		MainMenuUI ui = new MainMenuUI();
		MainMenuUI ui2 = new MainMenuUI();

		ui.paintOn(mainMenu, 0, 0);
		ui2.paintOn(mainMenu, 0, 310);

		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
				layout.show(mainMenu.getParent(), "pre_game_screen");
			}
		});
		newGame.setBackground(Color.WHITE);
		newGame.setFont(new Font("SansSerif", Font.PLAIN, 35));
		newGame.setBounds(248, 152, 218, 72);
		mainMenu.add(newGame);
		
		JButton recordsButton = new JButton("Records");
		recordsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
				layout.show(mainMenu.getParent(), "records");
				
				// tries to load records file if failed, saves a new one! 
				try {
					FileInputStream myFileInputStream = new FileInputStream("records.ser");
					ObjectInputStream myObjectInputStream = new ObjectInputStream(myFileInputStream);
					recs = (Records) myObjectInputStream.readObject(); 
					myObjectInputStream.close();
				} catch (Exception e) {
					try {
						FileOutputStream myFileOutputStream = new FileOutputStream("records.ser");
						ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
						myObjectOutputStream.writeObject(recs);
						myObjectOutputStream.close();
					}
					catch (Exception d) {
						e.printStackTrace();
					}
				}
				
				// prints 4 best records
				try {
					recs.printRecords(records);
				} catch (NullPointerException e) {}
				
			}
		});
		recordsButton.setFont(new Font("SansSerif", Font.PLAIN, 35));
		recordsButton.setBackground(Color.WHITE);
		recordsButton.setBounds(248, 235, 218, 72);
		mainMenu.add(recordsButton);
		
		JLabel avatarPicker = new JLabel("");
		avatarPicker.setIcon(new ImageIcon(Main.class.getResource("/avatars/Cool.png")));
		avatarPicker.setBounds(322, 47, 70, 70);
		preGameScreen.add(avatarPicker);
		
		JLabel image1 = new JLabel("");
		image1.setIcon(new ImageIcon(Main.class.getResource("/avatars/Cool.png")));
		image1.setBounds(205, 128, 70, 70);
		image1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// to make a feedback
				image1.setBorder(null);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// to make a feedback
				image1.setBorder(new LineBorder(new Color(0, 0, 0)));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avatarPicker.setIcon(image1.getIcon());
			}
		});
		preGameScreen.add(image1);
		
		JLabel image2 = new JLabel("");
		image2.setIcon(new ImageIcon(Main.class.getResource("/avatars/Guy.png")));
		image2.setBounds(285, 128, 70, 70);
		image2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// to make a feedback
				image2.setBorder(null);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// to make a feedback
				image2.setBorder(new LineBorder(new Color(0, 0, 0)));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avatarPicker.setIcon(image2.getIcon());
			}
		});
		preGameScreen.add(image2);
		
		JLabel image3 = new JLabel("");
		image3.setIcon(new ImageIcon(Main.class.getResource("/avatars/Ninja.png")));
		image3.setBounds(365, 128, 70, 70);
		image3.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// to make a feedback
				image3.setBorder(null);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// to make a feedback
				image3.setBorder(new LineBorder(new Color(0, 0, 0)));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avatarPicker.setIcon(image3.getIcon());
			}
		});
		preGameScreen.add(image3);
		
		JLabel image4 = new JLabel("");
		image4.setIcon(new ImageIcon(Main.class.getResource("/avatars/Rapper guy.png")));
		image4.setBounds(445, 128, 70, 70);
		image4.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// to make a feedback
				image4.setBorder(null);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// to make a feedback
				image4.setBorder(new LineBorder(new Color(0, 0, 0)));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				avatarPicker.setIcon(image4.getIcon());
			}
		});
		preGameScreen.add(image4);
		
		JPanel timerPanel = new JPanel();
		timerPanel.setBounds(85, 55, 40, 14);
		gameScreen.add(timerPanel);
		timerPanel.setLayout(null);
		
		JLabel min = new JLabel("00");
		min.setBounds(1, 0, 15, 14);
		timerPanel.add(min);
		
		JLabel sec = new JLabel("00");
		sec.setBounds(22, 0, 15, 14);
		timerPanel.add(sec);
		
		JLabel dots = new JLabel(":");
		dots.setBounds(15, 0, 4, 14);
		timerPanel.add(dots);
		
		JButton returnToMain = new JButton("Main Manu");
		returnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
				layout.show(mainMenu.getParent(), "main_menu");
				
				// making things ready for the next game start
				nameField.setText("Player");
				nameField.setForeground(Color.GRAY);
				permittedMovesField.setText("");
				avatarPicker.setIcon(image1.getIcon());
				
				// stopping the timer
				// why here? :|
				// calculations are done in less than a second so there would be no problem!
				timer.setStop(true);
				
				gameScreen.removeAll();
				endPanel.removeAll();
				endPanel.add(gameOver);
				endPanel.add(lblYourScore);
				endPanel.add(returnToMain);
				gameScreen.add(timerPanel);
			}
		});
		returnToMain.setBackground(Color.WHITE);
		returnToMain.setFont(new Font("SansSerif", Font.PLAIN, 14));
		returnToMain.setBounds(298, 245, 118, 36);
		endPanel.add(returnToMain);
		
		// an error message!
		JLabel errorMessage = new JLabel("Please fill all fields correctly!");
		errorMessage.setFont(new Font("SansSerif", Font.PLAIN, 14));
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(268, 317, 178, 19);
		
		// back button in preGameScreen panel
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
				layout.show(mainMenu.getParent(), "main_menu");
				
				// in case things are changed by user
				preGameScreen.remove(errorMessage);
				nameField.setText("Player");
				nameField.setForeground(Color.GRAY);
				permittedMovesField.setText("");
				avatarPicker.setIcon(image1.getIcon());
			}
		});
		btnBack.setBackground(Color.WHITE);
		btnBack.setBounds(10, 11, 89, 23);
		preGameScreen.add(btnBack);
		
		nameField = new JTextField();
		nameField.setForeground(Color.GRAY);
		nameField.setText("Player");
		nameField.setFont(new Font("SansSerif", Font.PLAIN, 33));
		nameField.setBounds(314, 209, 201, 43);
		preGameScreen.add(nameField);
		nameField.setColumns(10);
		nameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// maximum length for name
				if (nameField.getText().length() > 20) {
					e.consume();
				}
			}
		});
		// something :|
		nameField.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (nameField.getText().equals("Player")) {
					nameField.setText("");
					nameField.setForeground(Color.BLACK);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		JLabel lblYourName = new JLabel("Your Name:");
		lblYourName.setForeground(Color.DARK_GRAY);
		lblYourName.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblYourName.setBounds(205, 209, 101, 43);
		preGameScreen.add(lblYourName);
		
		permittedMovesField = new JTextField();
		permittedMovesField.setFont(new Font("SansSerif", Font.PLAIN, 33));
		permittedMovesField.setColumns(10);
		permittedMovesField.setBounds(365, 263, 150, 43);
		preGameScreen.add(permittedMovesField);
		permittedMovesField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// permittedMovesField's limitations
				char c = e.getKeyChar();
				if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || permittedMovesField.getText().length() >= 2) {
					e.consume();
				}
			}
		});
		
		JLabel lblPermittedMoves = new JLabel("Permitted Moves:");
		lblPermittedMoves.setForeground(Color.DARK_GRAY);
		lblPermittedMoves.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblPermittedMoves.setBounds(205, 263, 150, 43);
		preGameScreen.add(lblPermittedMoves);

		JButton startGame = new JButton("Start Game");
		startGame.setFont(new Font("Tahoma", Font.PLAIN, 18));
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!nameField.getText().isEmpty() && !permittedMovesField.getText().isEmpty() && Integer.valueOf(permittedMovesField.getText()) > 0 ) {
					
					CardLayout layout = (CardLayout) mainMenu.getParent().getLayout();
					layout.show(mainMenu.getParent(), "game_screen");
					
					// making a new box array to show in screen
					box = new Boxes(Integer.parseInt(permittedMovesField.getText()));
					// giving box endPanel to add score in it
					box.giveEndPanel(endPanel);
					// painting boxes! 
					box.painOn(gameScreen, 200, 100);
					
					// player's avatar in the game screen
					avatar.setBounds(10, 11, 70, 70);
					gameScreen.add(avatar);
					avatar.setIcon(avatarPicker.getIcon());

					// player's name in the game screen
					playerName = new JLabel("");
					playerName.setBounds(10, 80, 134, 22);
					gameScreen.add(playerName);
					playerName.setText(nameField.getText());

					// a timer! 00:00
					timer = new TimerCount();
					timer.start(min, sec);
					box.giveTimer(timer);

					// remaining permitted moves
					JLabel remainingMoves = new JLabel("");
					remainingMoves.setBounds(85, 70, 46, 14);
					gameScreen.add(remainingMoves);
					
					// updating remaining permitted moves
					box.moveUpdate(remainingMoves);
					
					// in case error message is visible and removing it for the next game
					preGameScreen.remove(errorMessage);
					
					// giving players name and avatar to add in records from boxes
					box.setAvatarAndName(playerName.getText(), avatar);
				}
				else {
					// shows error message if user didn't enter their name or permitted moves
					preGameScreen.add(errorMessage);
					preGameScreen.repaint();
				}
			}
		});
		startGame.setBackground(Color.WHITE);
		startGame.setBounds(273, 366, 168, 43);
		preGameScreen.add(startGame);
		
	}
}
