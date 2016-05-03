package uebung3;

import ogl.vecmath.Matrix;

public class VectorTest {

	public static void main(String[] args) {
		float[] m1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		float[] m2 = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
		Matrix p1 = new MatrixImpl(m1);
		Matrix p2 = new MatrixImpl(m2);
		System.out.println(p1.mult(p2));
	}

}
