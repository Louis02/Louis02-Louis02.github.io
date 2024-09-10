import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {
	RocketShip rock;
	ArrayList<Projectile> plist = new ArrayList<Projectile>();
	ArrayList<Alien> alist = new ArrayList<Alien>();
	long enemyTimer = 0;
	int enemySpawnTime = 500;
	int Score = 0;

	public ObjectManager(RocketShip rock) {
		this.rock = rock;
	}

	public int getScore() {
		return Score;
	}

	public void update() {
		rock.update();
		for (Projectile p : plist) {
			p.update();
		}
		for (Alien a : alist) {
			a.update();
		}
	}

	public void draw(Graphics g) {
		rock.draw(g);
		for (Projectile p : plist) {
			p.draw(g);
		}
		for (Alien a : alist) {
			a.draw(g);
		}
	}

	public void addProjectile(Projectile pro) {
		plist.add(pro);
	}

	public void addAlien(Alien a) {
		alist.add(a);
	}

	public void manageEnemies() {
		if (System.currentTimeMillis() - enemyTimer >= enemySpawnTime) {
			addAlien(new Alien(new Random().nextInt(LeagueInvaders.width), 0, 50, 50));

			enemyTimer = System.currentTimeMillis();
		}
	}

	public void purgeObjects() {
		for (int i = 0; i < plist.size(); i++) {
			Projectile p = plist.get(i);
			if (!p.isAlive) {
				plist.remove(p);

			}

		}
		for (int i = 0; i < alist.size(); i++) {

			Alien a = alist.get(i);
			if (!a.isAlive) {
				alist.remove(a);
				System.out.println("hi");
			}
		}

	}

	public void checkCollision() {
		for (Alien a : alist) {

			if (rock.collisionBox.intersects(a.collisionBox)) {

				a.isAlive = false;
				rock.isAlive = false;

			}
			for (Projectile p : plist) {
				if (a.collisionBox.intersects(p.collisionBox)) {
					p.isAlive = false;
					a.isAlive = false;
					Score++;
					System.out.println(Score);
					

				}
			}

		}
	}
}
