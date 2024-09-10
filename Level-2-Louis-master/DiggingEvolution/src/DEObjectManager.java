import java.awt.Graphics;
import java.util.ArrayList;

public class DEObjectManager {

	// Member Variables

	ArrayList<DEObject> objects;

	

	// Constructor
	public DEObjectManager() {
		objects = new ArrayList<DEObject>();

	}
	// Methods

	public void addDEObject(DEObject object) {
		objects.add(object);
	}

	public void draw(Graphics g) {
		for (DEObject o : objects) {
			o.draw(g);
		}
	}

	public void restart() {
		objects.clear();
	}

}
