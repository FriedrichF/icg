package uebung3;

import ogl.vecmath.Vector;

public class VectorTest {

	public static void main(String[] args) {
		Vector p1 = new VectorImpl(1,1,1);
		Vector p2 = new VectorImpl(4,3,2);
		System.out.println(p1.compareTo(p2));
	}

}
