package src.main.base;

import java.util.*;

/**
 * Created by David on 4/13/2015.
 */
public class World {

	private ArrayList<Face> faces;

	public World() {
		faces = new ArrayList<>();
	}

	public ArrayList<Face> getFaces() {
		return faces;
	}

	public void addShape(Shape s) {
		faces.addAll(s.getFaces());
	}

	public void addFace(Face f) {
		faces.add(f);
	}
}
