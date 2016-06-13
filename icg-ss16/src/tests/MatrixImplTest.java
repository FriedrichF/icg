package tests;

import org.junit.Assert;
import org.junit.Test;

import myMath.MatrixImpl;
import myMath.VectorImpl;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

/**
 * !!!!!!!!!!!!!!  UNBEDINGT !!!!!!!!!!!!!!!!!!!!
 * Die equals Methode von Matrix überschreiben: equals(Object obj) NICHT equals(Matrix obj, float epsi)
 * 
 * @author Michael Banck 
 * @matrikelnr 2004907
 *
 */
public class MatrixImplTest {

	private Matrix matrixEinheits, matrix1, matrix2, matrix3, matrix4;
	private Vector vectorPoint1, vectorPoint2, vectorDirection1, vectorDirection2;
	
	//WolframAlpha Notation
	//{{1,0,0,0}, {0,1,0,0}, {0,0,1,0}, {0,0,0,1}}
	private float[] einheits = {1,0,0,0,
								0,1,0,0,
								0,0,1,0,
								0,0,0,1};
	
	//{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}
	private float[] values1 = {	5,1,9,0,2,7,3,0,1,8,4,0,0,0,0,1};
	
	//{{0,0,0,4},{0,0,0,1},{0,0,0,6},{0,0,0,1}}
	private float[] values2 = {	0,0,0,0,0,0,0,0,0,0,0,0,4,1,6,1};

	private float[] values3 = {	0,0,0,0,0,0,0,0,0,0,0,0,7,3,2,0};
	
	//{{4,3,23,0},{1,0,7,0},{4,5,17,0},{0,0,0,0}}
	private float[] values4 = {	4,1,4,0,3,0,5,0,23,7,17,0,0,0,0,0};
	
	private float[] valuesVec1 = {4,3,23,1};
	private float[] valuesVec2 = {2,7,-2,1};
	private float[] valuesVec3 = {-7,3,3,0};
	private float[] valuesVec4 = {4,9,-1,0};

	
	public MatrixImplTest(){
		initMatrix();
	}
	

	public void initMatrix() {
		
		matrixEinheits 	= new MatrixImpl(einheits);
		matrix1 		= new MatrixImpl(values1);
		matrix2 		= new MatrixImpl(values2);
		matrix3 		= new MatrixImpl(values3);
		matrix4 		= new MatrixImpl(values4);
		
		vectorPoint1 		= new VectorImpl(valuesVec1);
		vectorPoint2 		= new VectorImpl(valuesVec2);
		vectorDirection1 	= new VectorImpl(valuesVec3);
		vectorDirection2 	= new VectorImpl(valuesVec4);
	
	}
	
	/*
	 * MatrixImpl get()
	 * 
	 */
	@Test
	public void testGet() {
		
		Assert.assertArrayEquals(einheits, matrixEinheits.getValues(), 0.0f);
		Assert.assertArrayEquals(values1, matrix1.getValues(), 0.0f);
		Assert.assertArrayEquals(values2, matrix2.getValues(), 0.0f);
		Assert.assertArrayEquals(values3, matrix3.getValues(), 0.0f);
		Assert.assertArrayEquals(values4, matrix4.getValues(), 0.0f);
		
	}
	
	/*
	 * Testen der Matrizen Multiplikation
	 */
	@Test
	public void testMult(){
		
		//bleibt gleich
//		Matrix res1 = matrix1.mult(matrixEinheits);
//		Assert.assertEquals(matrix1, res1);
		
		// {{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}*
		// {{0,0,0,4},{0,0,0,1},{0,0,0,6},{0,0,0,1}}
		// https://www.wolframalpha.com/input/?i=%7B%7B5,2,1,0%7D,+%7B1,7,8,0%7D,+%7B9,3,4,0%7D,+%7B0,0,0,1%7D%7D*%7B%7B0,0,0,4%7D,%7B0,0,0,1%7D,%7B0,0,0,6%7D,%7B0,0,0,1%7D%7D
		Matrix res2 = matrix1.mult(matrix2);
		
		float[] fResWA = {	0,0,0,28,
							0,0,0,59,
							0,0,0,63,
							0,0,0,1};
		
		Matrix res2WA = new MatrixImpl(fResWA);
		Assert.assertEquals(res2WA, res2);
		
		
		
		// {{0,0,0,4},{0,0,0,1},{0,0,0,6},{0,0,0,1}}*{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}
		//https://www.wolframalpha.com/input/?i=%7B%7B0,0,0,4%7D,%7B0,0,0,1%7D,%7B0,0,0,6%7D,%7B0,0,0,1%7D%7D*%7B%7B5,2,1,0%7D,+%7B1,7,8,0%7D,+%7B9,3,4,0%7D,+%7B0,0,0,1%7D%7D
		Matrix res3 = matrix2.mult(matrix1);
		
		float[] fResWA2 = {	0,0,0,4,
							0,0,0,1,
							0,0,0,6,
							0,0,0,1};

		Matrix res3WA = new MatrixImpl(fResWA2);
		Assert.assertEquals(res3WA, res3);
		
		
		//{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}*{{4,3,23,0},{1,0,7,0},{4,5,17,0},{0,0,0,0}}
		//https://www.wolframalpha.com/input/?i=%7B%7B5,2,1,0%7D,+%7B1,7,8,0%7D,+%7B9,3,4,0%7D,+%7B0,0,0,1%7D%7D*%7B%7B4,3,23,0%7D,%7B1,0,7,0%7D,%7B4,5,17,0%7D,%7B0,0,0,0%7D%7D
		Matrix res4 = matrix1.mult(matrix4);
		
		float[] fResWA3 = {	26,20,146,0,
							43,43,208,0,
							55,47,296,0,
							0,0,0,0};

		Matrix res4WA = new MatrixImpl(fResWA3);
		Assert.assertEquals(res4WA, res4);
		
		
	}
	
	
	@Test
	public void testTransformPoint() {
		
		//{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}*{4,3,23,1}
		//https://www.wolframalpha.com/input/?i=%7B%7B5,2,1,0%7D,+%7B1,7,8,0%7D,+%7B9,3,4,0%7D,+%7B0,0,0,1%7D%7D*%7B4,3,23,1%7D
		Vector res1 = matrix1.transformPoint(vectorPoint1);
		float[] fWA1 = {49, 209, 137,1};
		Vector res1WA = new VectorImpl(fWA1);
		Assert.assertEquals(res1WA, res1);
		
		
		//{{4,3,23,0},{1,0,7,0},{4,5,17,0},{0,0,0,0}}*{2,7,-2,1}
		//https://www.wolframalpha.com/input/?i=%7B%7B4,3,23,0%7D,%7B1,0,7,0%7D,%7B4,5,17,0%7D,%7B0,0,0,0%7D%7D*%7B2,7,-2,1%7D
		Vector res2 = matrix4.transformPoint(vectorPoint2);
		float[] fWA2 = {-17,-12,9,0};
		Vector res2WA = new VectorImpl(fWA2);
		Assert.assertEquals(res2WA, res2);
		
	}
	
	
	@Test
	public void testTransformDirection() {
		
		//{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}*{-7,3,3,0}
		//https://www.wolframalpha.com/input/?i=%7B%7B5,2,1,0%7D,+%7B1,7,8,0%7D,+%7B9,3,4,0%7D,+%7B0,0,0,1%7D%7D*%7B-7,3,3,0%7D
		Vector res1 = matrix1.transformDirection(vectorDirection1);
		float[] fWA1 = {-26, 38, -42,0};
		Vector res1WA = new VectorImpl(fWA1);
		Assert.assertEquals(res1WA, res1);
		
		
		//{{4,3,23,0},{1,0,7,0},{4,5,17,0},{0,0,0,0}}*{4,9,-1,0}
		//https://www.wolframalpha.com/input/?i=%7B%7B4,3,23,0%7D,%7B1,0,7,0%7D,%7B4,5,17,0%7D,%7B0,0,0,0%7D%7D*%7B4,9,-1,0%7D
		Vector res2 = new VectorImpl(matrix4.transformDirection(vectorDirection2));
		float[] fWA2 = {20,-3,44,0};
		Vector res2WA = new VectorImpl(fWA2);
		Assert.assertEquals(res2WA, res2);
		
	}
	
	
	
	@Test
	public void testTranspose() {
		

		float[] values1 = {	4,3,23,0,
							1,0,7,0,
							4,5,17,0,
							0,0,0,0};
		
		float[] values1t = {4,1,4,0,
							3,0,5,0,
							23,7,17,0,
							0,0,0,0};
		
		
		float[] values2 = {	8,3,-3,0,
							1,4,7,2,
							4,5,-6,0,
							0,0,7,0};
		
		float[] values2t = {8,1,4,0,
							3,4,5,0,
							-3,7,-6,7,
							0,2,0,0};
		
		
		Matrix m1 = new MatrixImpl(values1);
		Matrix m1t = new MatrixImpl(values1t);
		Matrix m2 = new MatrixImpl(values2);
		Matrix m2t = new MatrixImpl(values2t);
		
		
		Assert.assertEquals(m1t, m1.transpose());
		Assert.assertEquals(m2t, m2.transpose());
		
		Assert.assertEquals(m1, m1t.transpose());
		Assert.assertEquals(m2, m2t.transpose());
		

	}

	
	

}
