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
		float[] matrix = new float[16];

		// 1.Zeile
		matrix[0] = (float) (Math.pow(ax, 2) * (1 - Math.cos(angle)) + Math.cos(angle));
		matrix[1] = (float) (ax * ay * (1 - Math.cos(angle)) - az * Math.sin(angle));
		matrix[2] = (float) (ax * az * (1 - Math.cos(angle)) + ay * Math.sin(angle));
		matrix[3] = 0;
		
		//2. Zeile
		matrix[4] = (float) (ay * ax * (1 - Math.cos(angle)) + az * Math.sin(angle));
		matrix[5] = (float) (Math.pow(ay, 2) * (1 - Math.cos(angle)) + Math.cos(angle));
		matrix[6] = (float) (ay * az * (1 - Math.cos(angle)) - ax * Math.sin(angle));
		matrix[7] = 0;
		
		// 3.Zeile
		matrix[8] = (float) (az * ax * (1 - Math.cos(angle)) - ay * Math.sin(angle));
		matrix[9] = (float) (az * ay * (1 - Math.cos(angle)) + ax * Math.sin(angle));
		matrix[10] = (float) (Math.pow(az, 2) * (1 - Math.cos(angle)) + Math.cos(angle));
		matrix[11] = 0;
		
		//4. Zeile
		matrix[12] = 0;
		matrix[13] = 0;
		matrix[14] = 0;
		matrix[15] = 1;

		Matrix matrixResult = new MatrixImpl(matrix);

		return new MatrixImpl(new MatrixCore(matrixResult.asArray()),
				new MatrixCore(matrixResult.transpose().asArray()));
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
