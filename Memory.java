
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import German.Memory;

/**
 *
 * @author Abdallah Abouelatta
 */

public class Memory extends JFrame implements ActionListener, MouseListener {
	private Player player1, player2;
	private ArrayList<Card> cardsList = new ArrayList<Card>(); // arrayList of the all cards's objects
	private HashMap<Card, Card> pairs = new HashMap<>(); // hashMap for every Paerchen

	public static void main(String[] args) {
		Memory m = new Memory();
	}

	public Memory() {
//        to create the panle which will be the container of the cards
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 4, 3, 3));
		mainPanel.setBackground(Color.black);
		mainPanel.setSize(700, 700);
//        to create the main frame of the game
		JFrame frame = new JFrame("Memory Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(700, 750);
//		frame.setLocationRelativeTo(null); // to set JFrame to appear centered
		frame.setResizable(false);
//        to create menu in the main fram 
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu gameMenu = new JMenu("Settings");
		menu.add(gameMenu);
//        to call the function which set the elements of the menu
		menuItem("New Game", gameMenu, this);
		menuItem("Exit", gameMenu, this);

//        to add the panel to the fram
		frame.add(mainPanel);

//       for loop to fill the arrayList with the cards after creating objects from them
		for (int j = 0; j <= 7; j++) {
			for (int i = 0; i < 2; i++) {
//        		to make an object for every icon
				Card cardObject = new Card("/karten/" + j + "-" + i + ".png");

//     			to add bilderrahmen for every icon
				cardObject.imageFrame = new JLabel();

//				to add the Imageicon to the bilderrahmen
				cardObject.imageFrame.setIcon(cardObject.image);

//				to addMouseListener for every icon'S JLabel
				cardObject.imageFrame.addMouseListener(this);

// 				to change the image scale
				Image imagemoddifeid = cardObject.image.getImage();
				Image scalable = imagemoddifeid.getScaledInstance(175, 187, Image.SCALE_SMOOTH);
				cardObject.image = new ImageIcon(scalable);

//        	    to add the cards to the array list
				cardsList.add(cardObject);

//				to make the default state be the backside of the cards
				cardObject.coverCard();
			}
		}

//	    to add the cards's pairs to the HashMap before shuffling the arraylist
//		+=2 to add tha pairs in the right places
		for (int i = 0; i < cardsList.size() - 1; i += 2) {
			pairs.put(cardsList.get(i), cardsList.get(i + 1));
		}
//test        just for test
//		System.out.println("for loop to fill the arrayList with the cards: " + "\n" + karteList);
//test      just for tests
//		System.out.println("for loop to fill the hashMap: " + "\n" + Paerchen + "\n");
//test		
//		Paerchen.forEach((key, value) -> System.out.println("key \n" + key + "\nvalue:\n" + value));

//        to shuffle the list
		Collections.shuffle(cardsList);
//        to add the all cards to the mainPanel after they shuffeled
		for (int j = 0; j < cardsList.size(); j++) {
			mainPanel.add(cardsList.get(j).imageFrame);
		}
//		to make the main frame visible after the all components added
		frame.setVisible(true);
//        to call the methode to start the game
		startGame();
	}

	private void startGame() {
//        the early first panel to ask start or exite the game
		JPanel startPanel = new JPanel();
//		for the buttons of the panel
		Object[] startButtonsPanel = { "Exite", "Start a new game" };
		int startButtons = JOptionPane.showOptionDialog(null, startPanel, "Welcome to the Memory Game",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, startButtonsPanel, null);
//        if the user clicked Exite button it will terminate
		if (startButtons == JOptionPane.OK_OPTION) {
			System.exit(0);
		}

//        to make objects for the two players and take the values from the user
		JTextField name1 = new JTextField();
		JTextField name2 = new JTextField();
		Object[] nameInputs = { "Player 1 name:", name1, "player 2 name:", name2 };

//        to generate the input panel
		int input = JOptionPane.showConfirmDialog(null, nameInputs, "enter player's names",
				JOptionPane.OK_CANCEL_OPTION);

//		to take from the user the names for every player
		this.player1 = new Player(name1.getText());
		this.player2 = new Player(name2.getText());

//test        just to test the output
		System.out.println("player1: " + player1.getName());
		System.out.println("player2: " + player2.getName() + "\n########");

//        to generate random number between o an 1 and to choose random player
		Random rnd = new Random();
		int randomValue = Math.abs((rnd.nextInt() % 2));

//        to save the two players in an array to choose one of them randomly
		Player[] players = new Player[2];
		players[0] = player1;
		players[1] = player2;

//        after ok clicked show out the random player
//test        
		if (input == JOptionPane.OK_OPTION) {
			System.out.println("-----");
			System.out.println(players[0].getName());
			System.out.println(players[1].getName());
			System.out.println("-------");
			System.out.println(players[randomValue].getName());

//			to print the players names on the start panel
			JOptionPane.showMessageDialog(null,
					"the actual game score is:\n" + player1.getName() + " - 0 | " + player2.getName() + " - 0\n"
							+ "the turn now is on the Player:\n" + players[randomValue].getName());
			players[randomValue].setHisTurn(true);
		}
	}

	private void checkPairsSimilar() {
//		the second condition to make it true after the second click
		if (Card.oppenedCards.get(0).equals(pairs.get(Card.oppenedCards.get(1)))
				|| Card.oppenedCards.get(1).equals(pairs.get(Card.oppenedCards.get(0)))) {
//test
			System.out.println("paare gefunden");

//			to call the function which make the paars unclickable anymore
			Card.oppenedCards.get(0).isOpen();
//			to cleare the arrayList to make it not able to have more than two values after the second click
			Card.oppenedCards.clear();

//			to add the point for the player
			if (!player1.hasTurn())
				player1.increasePoints();
			else
				player2.increasePoints();

//			to call the function to split a message for the user
			gameStatus(":) | Pair found!");

		} else {
//			to call the function to split a message for the user
			gameStatus(":- | Not Pair!");
//			to cover the image again
			Card.oppenedCards.get(0).coverCard();
			Card.oppenedCards.get(0).coverCard();
		}
	}

	private void checkWinner() {
		if (player1.getPoints() > player2.getPoints()) {
			endGame(player1.getName());
		} else if (player1.getPoints() < player2.getPoints()) {
			endGame(player2.getName());
		} else if (player1.getPoints() == player2.getPoints()) {
			endGame("No One");
		}
	}

	private void changePlayer() {
		if (this.player1.hasTurn()) {
			this.player2.setHisTurn(true);
			this.player1.setHisTurn(false);

		} else {
			this.player1.setHisTurn(true);
			this.player2.setHisTurn(false);
		}
	}

	private void endGame(String gewonnen) {
		JPanel endPanel = new JPanel();
		Object[] endButtonsPanel = { "Exit", "Start New Game" };
//		the last panel
		endPanel.add(new JLabel(
				"Game Over \n" + "'" + gewonnen + "'" + " is won \nwith score " + "(" + player1.getName() + " - "
						+ player1.getPoints() + " | " + player2.getName() + " - " + player2.getPoints() + ")" + "\n"));
		int endButtons = JOptionPane.showOptionDialog(null, endPanel, "\nGame Over", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, endButtonsPanel, null);

//      choose between Exite button and start a new game 
		if (endButtons == JOptionPane.OK_OPTION) {
			System.exit(0);
		} else if (endButtons == JOptionPane.NO_OPTION) {
			Memory m = new Memory();
		}
	}

	private void gameStatus(String meldung) {
		if (player1.getPoints() + player2.getPoints() < 8) {

			if (player1.hasTurn()) {
				JOptionPane.showMessageDialog(null,
						meldung + "\n" + "the actual game score is:\n" + player1.getName() + " - " + player1.getPoints()
								+ " | " + player2.getName() + " - " + player2.getPoints() + "\n"
								+ "the turn now is on the Player:\n" + player1.getName());
			} else if (player2.hasTurn()) {
				JOptionPane.showMessageDialog(null,
						meldung + "\n" + "the actual game score is:\n" + player1.getName() + " - " + player1.getPoints()
								+ " | " + player2.getName() + " - " + player2.getPoints() + "\n"
								+ "the turn now is on the Player:\n" + player2.getName());
			}
//			else means the game is still not over (points == 8)
		} else {
			checkWinner();
		}
	}

	// function to set the elements of the settings menu
	private void menuItem(String string, JMenu gameMenu, Memory listener) {
		JMenuItem newItem = new JMenuItem(string);
		newItem.setActionCommand(string);
		newItem.addActionListener(listener);
		gameMenu.add(newItem);
	}

//    the actions which happend to the elements of the settings menu
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New Game")) {
			Memory m = new Memory();
		}
		if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < cardsList.size(); i++) {
			if (e.getSource() == this.cardsList.get(i).imageFrame) {
				cardsList.get(i).openCard();

				if (Card.sumOpened % 2 == 0 || (Card.oppenedCards.size() % 2 == 0)) {
					this.changePlayer();
					this.checkPairsSimilar();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
