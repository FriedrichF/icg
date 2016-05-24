package uebung3;

import ogl.vecmath.Matrix;

public class VectorTest {

	public static void main(String[] args) {
		float[] m1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		float[] m2 = {21,22,23,24,25,26,27,28,29,210,211,212,213,214,215,216};
		Matrix p1 = new MatrixImpl(m1);
		Matrix p2 = new MatrixImpl(m2);
		System.out.println(p1);
		System.out.println(p1.transpose());
	}

}
