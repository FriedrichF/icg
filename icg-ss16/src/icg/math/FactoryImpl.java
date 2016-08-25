package icg.math;

import myMath.MatrixCore;
import myMath.MatrixImpl;
import myMath.VectorImpl;
import ogl.vecmath.Color;
import ogl.vecmath.Factory;
import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;

public class FactoryImpl implements Factory {

	@Override
	public Vector vector(float nx, float ny, float nz) {
		return new VectorImpl(nx, ny, nz);
	}

	@Override
	public Vector xAxis() {
		throw new IllegalArgumentException("xAxis");
	}

	@Override
	public Vector yAxis() {
		throw new IllegalArgumentException("yAxis");
	}

	@Override
	public Vector zAxis() {
		throw new IllegalArgumentException("zAxis");
	}

	@Override
	public int vectorSize() {
		return 3;
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
		throw new IllegalArgumentException("matrix floats");
	}

	@Override
	public Matrix matrix(float[] elements) {
		throw new IllegalArgumentException("matrix float array");
	}

	@Override
	public Matrix matrix(Vector b0, Vector b1, Vector b2) {
		throw new IllegalArgumentException("matrix Vector");
	}

	@Override
	public Matrix translationMatrix(Vector t) {
		return translationMatrix(t.x(), t.y(), t.z());
	}

	@Override
	public Matrix translationMatrix(float x, float y, float z) {
		float[] matrix = identityMatrix().asArray();
		matrix[12] = x;
		matrix[13] = y;
		matrix[14] = z;
		
		float[] matrixInvers = identityMatrix().asArray();
		matrixInvers[12] = -x;
		matrixInvers[13] = -y;
		matrixInvers[14] = -z;
		
		return new MatrixImpl(new MatrixCore(matrix), new MatrixCore(matrixInvers));
	}

	@Override
	public Matrix rotationMatrix(Vector axis, float angle) {
		return rotationMatrix(axis.x(), axis.y(), axis.z(), angle);
	}

	@Override
	public Matrix rotationMatrix(float ax, float ay, float az, float angle) {
		float[] matrix = new float[16];
		
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));

		// 1.Zeile
		matrix[0] = (float) (Math.pow(ax, 2) * (1 - cos) + cos);
		matrix[4] = (float) (ax * ay * (1 - cos) - az * sin);
		matrix[8] = (float) (ax * az * (1 - cos) + ay * sin);
		matrix[12] = 0;
		
		//2. Zeile
		matrix[1] = (float) (ay * ax * (1 - cos) + az * sin);
		matrix[5] = (float) (Math.pow(ay, 2) * (1 - cos) + cos);
		matrix[9] = (float) (ay * az * (1 - cos) - ax * sin);
		matrix[13] = 0;
		
		// 3.Zeile
		matrix[2] = (float) (az * ax * (1 - cos) - ay * sin);
		matrix[6] = (float) (az * ay * (1 - cos) + ax * sin);
		matrix[10] = (float) (Math.pow(az, 2) * (1 - cos) + cos);
		matrix[14] = 0;
		
		//4. Zeile
		matrix[3] = 0;
		matrix[7] = 0;
		matrix[11] = 0;
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
		matrixInvers[0] = 1 / x;
		matrixInvers[5] = 1 / y;
		matrixInvers[10] = 1 / z;
		return new MatrixImpl(new MatrixCore(matrix), new MatrixCore(matrixInvers));
	}

	@Override
	public Matrix lookatMatrix(Vector eye, Vector center, Vector up) {
		throw new IllegalArgumentException("lookatMatrix");
	}

	@Override
	public Matrix frustumMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
		throw new IllegalArgumentException("frustumMatrix");
	}

	@Override
	public Matrix perspectiveMatrix(float fovy, float aspect, float zNear, float zFar) {
		throw new IllegalArgumentException("perspectiveMatrix");
	}

	@Override
	public Color color(float r, float g, float b) {
		return new RGBColorImpl(r, g, b);
	}

	public final static Factory vecmath = new FactoryImpl();

	@Override
	public int colorSize() {
		return 3;
	}

}
