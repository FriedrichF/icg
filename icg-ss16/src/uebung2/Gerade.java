package uebung2;

import icg.rendering.LineImpl;
import icg.warmup.base.ImageGenerator;
import ogl.vecmath.Vector;

public class Gerade {

	public static void main(String[] args) {
		Vector p1 = new Point(100,100,1);
		Vector p2 = new Point(400,300,2);
		new ImageGenerator(new LineImpl(p1, p2));

	}

}
