import java.awt.Dimension;

import javax.swing.JFrame;

public class LeagueInvaders {
	JFrame frame;
	static int height = 800;
	static int width = 500;
	GamePanel game;
	
	public static void main(String[] args) {
		LeagueInvaders l = new LeagueInvaders();
		l.setup();
	}

	public LeagueInvaders() {
		frame = new JFrame();
		game = new GamePanel();
	}

	void setup() {
		frame.add(game);
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.startGame();
		frame.addKeyListener(game);
		
	}
}
