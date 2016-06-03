package icg.math;

import myMath.MatrixCore;
import myMath.MatrixImpl;
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
		float[] identityMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f };
		return new MatrixImpl(new MatrixCore(identityMatrix), new MatrixCore(identityMatrix));
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
		return translationMatrix(t.x(), t.y(), t.z());
	}

	@Override
	public Matrix translationMatrix(float x, float y, float z) {
		float[] matrix = identityMatrix().asArray();
		matrix[3] = x;
		matrix[7] = y;
		matrix[11] = z;
		float[] matrixInvers = identityMatrix().asArray();
		matrix[3] = -x;
		matrix[7] = -y;
		matrix[11] = -z;
		return new MatrixImpl(new MatrixCore(matrix), new MatrixCore(matrixInvers));
	}

	@Override
	public Matrix rotationMatrix(Vector axis, float angle) {
		return rotationMatrix(axis.x(), axis.y(), axis.z(), angle);
	}

	@Override
	public Matrix rotationMatrix(float ax, float ay, float az, float angle) {
		Matrix matrixResult = identityMatrix();
		if (ax == 1.0f) {
			float[] matrixArrayX = identityMatrix().asArray();
			matrixArrayX[5] = (float) Math.cos(angle);
			matrixArrayX[6] = (float) -Math.sin(angle);
			matrixArrayX[9] = (float) Math.sin(angle);
			matrixArrayX[10] = (float) Math.cos(angle);
			Matrix matrixX = new MatrixImpl(matrixArrayX);
			matrixResult = matrixResult.mult(matrixX);
		}
		if (ay == 1.0f) {
			float[] matrixArrayY = identityMatrix().asArray();
			matrixArrayY[0] = (float) Math.cos(angle);
			matrixArrayY[2] = (float) Math.sin(angle);
			matrixArrayY[8] = (float) -Math.sin(angle);
			matrixArrayY[10] = (float) Math.cos(angle);
			Matrix matrixY = new MatrixImpl(matrixArrayY);
			matrixResult = matrixResult.mult(matrixY);
		}
		if (az == 1.0f) {
			float[] matrixArrayZ = identityMatrix().asArray();
			matrixArrayZ[0] = (float) Math.cos(angle);
			matrixArrayZ[1] = (float) -Math.sin(angle);
			matrixArrayZ[4] = (float) Math.sin(angle);
			matrixArrayZ[5] = (float) Math.cos(angle);
			Matrix matrixZ = new MatrixImpl(matrixArrayZ);
			matrixResult = matrixResult.mult(matrixZ);
		}
		
		Matrix test = new MatrixImpl(new MatrixCore(matrixResult.asArray()),
				new MatrixCore(matrixResult.transpose().asArray()));

		return test;
	}

	@Override
	public Matrix scaleMatrix(Vector s) {
		return scaleMatrix(s.x(), s.y(), s.z());
	}

	@Override
	public Matrix scaleMatrix(float x, float y, float z) {
		float[] matrix = identityMatrix().asArray();
		matrix[0] = x;
		matrix[5] = y;
		matrix[10] = z;
		float[] matrixInvers = identityMatrix().asArray();
		matrix[0] = 1 / x;
		matrix[5] = 1 / y;
		matrix[10] = 1 / z;
		return new MatrixImpl(new MatrixCore(matrix), new MatrixCore(matrixInvers));
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
