
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	Timer timer;
	final int MENU_STATE = 0;

	final int GAME_STATE = 1;

	final int END_STATE = 2;
	ObjectManager obj;
	int CURRENT_STATE;
	Font titleFont;

	public static BufferedImage rocketImg;

	public static BufferedImage bulletImg;

	public static BufferedImage spaceImg;
	RocketShip rock = new RocketShip(250, 750, 50, 50);

	public GamePanel() {
		timer = new Timer(1000 / 60, this);
		titleFont = new Font("arial", Font.PLAIN, 48);
		obj = new ObjectManager(rock);
		try {

			rocketImg = ImageIO.read(this.getClass().getResourceAsStream("rocket.png"));

			bulletImg = ImageIO.read(this.getClass().getResourceAsStream("bullet.png"));

			spaceImg = ImageIO.read(this.getClass().getResourceAsStream("space.png"));

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	@Override

	public void paintComponent(Graphics g) {
		if (CURRENT_STATE == MENU_STATE) {

			drawMenuState(g);

		} else if (CURRENT_STATE == GAME_STATE) {

			drawGameState(g);

		} else if (CURRENT_STATE == END_STATE) {

			drawEndState(g);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (CURRENT_STATE == MENU_STATE) {

			updateMenuState();

		} else if (CURRENT_STATE == GAME_STATE) {

			updateGameState();

		} else if (CURRENT_STATE == END_STATE) {

			updateEndState();

		}

		repaint();
	}

	void startGame() {
		timer.start();
	}

	void updateMenuState() {

	}

	void updateGameState() {
		obj.update();
		obj.manageEnemies();
		obj.checkCollision();
		obj.purgeObjects();
		if (rock.isAlive == false) {
			CURRENT_STATE = END_STATE;
		}
	}

	void updateEndState() {

	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		g.setFont(titleFont);
		g.setColor(Color.pink);
		g.drawString("LeagueInvaders", 75, 100);
	}

	void drawGameState(Graphics g) {
		g.setColor(Color.black);

		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		obj.draw(g);
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.red);

		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		g.setFont(titleFont);
		g.setColor(Color.pink);
		g.drawString("Game Over", 110, 100);
		obj.getScore();
		g.drawString("You killed " + obj.getScore() + " aliens", 60, 500);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			if (CURRENT_STATE == END_STATE) {

				CURRENT_STATE -= 2;
				rock = new RocketShip(250, 700, 50, 50);
				obj = new ObjectManager(rock);

			} else if (CURRENT_STATE == MENU_STATE) {

				CURRENT_STATE++;

			} else if (CURRENT_STATE == GAME_STATE) {

				CURRENT_STATE++;

			}

		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			rock.up = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			rock.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			rock.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rock.right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			obj.addProjectile(new Projectile(rock.x + rock.width / 2, rock.y, 10, 10));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			rock.up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			rock.down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			rock.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rock.right = false;
		}
	}
}
