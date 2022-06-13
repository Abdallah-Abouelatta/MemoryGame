
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 *
 * @author Abdallah Abouelatta
 */
public class Card extends JPanel {

	private boolean opened;
	JLabel imageFrame;
	ImageIcon image;
	ImageIcon backSide;
	static ArrayList<Card> oppenedCards = new ArrayList();
	static int sumOpened;

	public Card(String pfad) {
		this.image = new ImageIcon(getClass().getResource(pfad));
		this.backSide = new ImageIcon(getClass().getResource("/karten/rueckseite.png"));
	}

	public void openCard() {
		sumOpened++;
//test
		System.out.println("sum offen:" + sumOpened);
		if (this.opened == false) {
			this.imageFrame.setIcon(image);
			this.opened = true;
			this.oppenedCards.add(this);
			System.out.println("size is" + oppenedCards.size());
		}
	}

	public void coverCard() {
		if (this.opened = true) {
			this.imageFrame.setIcon(backSide);
			this.opened = false;
			this.oppenedCards.remove(this);
		}
	}

	public void isOpen() {
//		to make the opened paars unclickable
		MouseListener[] mouseListeners = Card.oppenedCards.get(0).imageFrame.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			Card.oppenedCards.get(0).imageFrame.removeMouseListener(mouseListeners[i]);
			Card.oppenedCards.get(1).imageFrame.removeMouseListener(mouseListeners[i]);
		}
	}
}
