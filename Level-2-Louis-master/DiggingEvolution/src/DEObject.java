import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DEObject {

	// Member Variables

	int x;

	int y;

	int row;

	int col;

	int width;

	int height;

	int state;



	int[] skyState = new int[9];

	int counter = 0;

	static int colors;

	static int[] cloudMap;
	// private static final Color invisible = new Color(119, 85, 34);

	// next

	// private static final Color topState = new Color(200, 100, 100);

	static final Float[] opaque = { 1f, .7f, .4f, .2f, 0.12f };

	// Constructor
	public DEObject(int width, int height, int row, int col, int state) {
		x = width * col;

		y = height * row;

		

		this.width = width;

		this.height = height;

		this.state = state;

//		for (int i = 0; i < skyState.length; i++) {
//			if (i % 2 == 0) {
//				skyState[i] = DEGamePanel.NOcloud;
//			} else {
//				skyState[i] = DEGamePanel.Cloud;
//			}
//		}

	}

	// Methods
	public void draw(Graphics g) {
		counter++;

		if (DEGamePanel.Cloud == state || DEGamePanel.NOcloud == state) {

			if (state == DEGamePanel.NOcloud) {
				g.drawImage(DEGamePanel.cloudlessPic, x, y, width, height, null);
			
				
			} else {
				if (state == DEGamePanel.Cloud) {
					g.drawImage(DEGamePanel.skyPic, x, y, width, height, null);

					
				}
			}
		} else if (DEGamePanel.emptyBottom == state) {

			g.drawImage(DEGamePanel.dirtPic, x, y, width, height, null);

		}

		else if (DEGamePanel.ab == state) {

			// System.out.println(" " + colors + " " + colorList.length + " ");

			if (colors < opaque.length) {
				// g.setColor(colorList[colors]);
				g.drawImage(DEGamePanel.dirtPic, x, y, width, height, null);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setComposite(AlphaComposite.SrcOver.derive(opaque[colors]));
				g2d.drawImage(DEGamePanel.ApricotPic, x, y, width, height, null);
				g2d.dispose();
			} else {
				g.drawImage(DEGamePanel.dirtPic, x, y, width, height, null);

			}
		} else if (DEGamePanel.jef == state) {
			if (y == 0) {
				g.drawImage(DEGamePanel.skyPic, x, y, width, height, null);
			}

			else {
				g.drawImage(DEGamePanel.dirtPic, x, y, width, height, null);
			}

			g.drawImage(DEGamePanel.ChefPic, x, y, width, height, null);

		} else if (DEGamePanel.grass == state) {
			g.drawImage(DEGamePanel.grassPic, x, y, width, height, null);
		}

	}

	public void setCloudMap(int array[]) {
		cloudMap = array;
	}

	public void clouds() {

	}

	public static void setVisible(int v) {
		colors = v;
	}

	public void state(int state) {
		this.state = state;
	}
}
