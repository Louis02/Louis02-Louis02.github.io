 import javax.swing.JFrame;

class DERunner {

	// Member Variables
	JFrame frame;
	final static int numCols = 9;
	final static int numRows = 10;
	final static int blockSize = 45;
	final static int width = numCols * blockSize;
	final static int height = numRows * blockSize;
	// final static int extraPanelY = height;
	DEGamePanel gp;

	// Main
	public static void main(String[] args) {
		new DERunner();

	}

	DERunner() {
		frame = new JFrame();
		gp = new DEGamePanel(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(gp);
		frame.setVisible(true);

		frame.addKeyListener(gp);
		frame.pack();
		frame.setSize(width, height);

		frame.setResizable(false);

	}

	public void startNewGame() {
		frame.dispose();
		new DERunner();

	}
}