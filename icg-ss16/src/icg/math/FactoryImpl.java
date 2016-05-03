package icg.math;

import ogl.vecmath.Color;
import ogl.vecmath.Factory;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class FactoryImpl implements Factory {

	@Override
	public Vector vector(float nx, float ny, float nz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector xAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector yAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector zAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int vectorSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Matrix identityMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix matrix(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix matrix(float[] elements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix matrix(Vector b0, Vector b1, Vector b2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix translationMatrix(Vector t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix translationMatrix(float x, float y, float z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix rotationMatrix(Vector axis, float angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix rotationMatrix(float ax, float ay, float az, float angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix scaleMatrix(Vector s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix scaleMatrix(float x, float y, float z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix lookatMatrix(Vector eye, Vector center, Vector up) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix frustumMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix perspectiveMatrix(float fovy, float aspect, float zNear, float zFar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color color(float r, float g, float b) {
		return new RGBColorImpl(r, g, b);
	}

	public final static Factory vecmath = new FactoryImpl();
	
	@Override
	public int colorSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
