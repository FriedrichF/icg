package tests;

import static org.junit.Assert.*;

import org.junit.Assert;

import static ogl.vecmathimp.FactoryDefault.vecmath;
import icg.math.FactoryImpl;
import myMath.MatrixImpl;
import myMath.VectorImpl;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

import org.junit.Test;

public class vcmathTest {
	
	private float[] valuesVec1 = {0,1,1,1};
	Vector vectorPoint1 		= new VectorImpl(valuesVec1);
	//{{5,2,1,0}, {1,7,8,0}, {9,3,4,0}, {0,0,0,1}}
		private float[] values1 = {	5,2,1,0,
									1,7,8,0,
									9,3,4,0,
									0,0,0,1};
		
		//{{0,0,0,4},{0,0,0,1},{0,0,0,6},{0,0,0,1}}
		private float[] values2 = {	0,0,0,4,
									0,0,0,1,
									0,0,0,6,
									0,0,0,1};
		
		Matrix matrix1 		= new MatrixImpl(values1);
		Matrix matrix2 		= new MatrixImpl(values2);
		
		Matrix matrixVec1 = vecmath.matrix(values1);
		Matrix matrixVec2 = vecmath.matrix(values2);
		
		

	@Test
	public void test() {
		Vector v = new VectorImpl(1, 5, 4);
		
		float[] matrixOwn = FactoryImpl.vecmath.translationMatrix(v).asArray();
		float[] matrix = vecmath.translationMatrix(v).asArray();
		Assert.assertArrayEquals(matrixOwn, matrix,0.0f);
	}
	
	@Test
	public void multTest(){		
		Matrix res1 = matrix1.mult(matrix2);
		
		Matrix res2 = matrixVec1.mult(matrixVec2);
		
		float[] test1 = res1.asArray();
		float[] test2 = res2.asArray();
		
		Assert.assertArrayEquals(test1, test2, 0.0f);
		
		
		
	}
	
	@Test
	public void translationTest(){
		float[] test1 = FactoryImpl.vecmath.translationMatrix(vectorPoint1).asArray();
		float[] test2 = vecmath.translationMatrix(vectorPoint1).asArray();
		Assert.assertArrayEquals(test1, test2, 0.0f);
	}
	
	@Test
	public void rotationTest(){
		float[] test1 = FactoryImpl.vecmath.rotationMatrix(vectorPoint1, 91f).asArray();
		float[] test2 = vecmath.rotationMatrix(vectorPoint1, 91f).asArray();
		Assert.assertArrayEquals(test1, test2, 0.1f);
	}
	
	@Test
	public void scaleTest(){
		float[] test1 = FactoryImpl.vecmath.scaleMatrix(vectorPoint1).asArray();
		float[] test2 = vecmath.scaleMatrix(vectorPoint1).asArray();
		Assert.assertArrayEquals(test1, test2, 0.0f);
	}
	
	@Test
	public void perspectiveTest(){
		float[] test1 = FactoryImpl.vecmath.perspectiveMatrix(60f, 1, 0.1f, 100f).asArray();
		float[] test2 = vecmath.perspectiveMatrix(60f, 1, 0.1f, 100f).asArray();
		Assert.assertArrayEquals(test1, test2, 0.1f);
	}
	
	@Test
	public void lookAtTest(){
		float[] test1 = FactoryImpl.vecmath.lookatMatrix(FactoryImpl.vecmath.vector(0f, 0f, 3f), FactoryImpl.vecmath.vector(0f, 0f, 0f),
				FactoryImpl.vecmath.vector(0f, 1f, 0f)).asArray();
		float[] test2 = vecmath.lookatMatrix(FactoryImpl.vecmath.vector(0f, 0f, 3f), FactoryImpl.vecmath.vector(0f, 0f, 0f),
				FactoryImpl.vecmath.vector(0f, 1f, 0f)).asArray();
		Assert.assertArrayEquals(test1, test2, 0.1f);
	}

}
