package uebung3;

import java.nio.FloatBuffer;

import ogl.vecmath.Matrix;
import ogl.vecmath.Vector;
import ogl.vecmathimp.VectorImp;

public class MatrixImpl implements Matrix {

	float[] matrix = new float[16];

	public MatrixImpl(float[] vals) {
		setValues(vals);
	}

	@Override
	public float[] getValues() {
		return matrix;
	}

	@Override
	public void setValues(float[] vals) {
		this.matrix = vals;
	}

	@Override
	public float get(int c, int r) {
		return matrix[4 * r + c];
	}

	@Override
	public Matrix mult(Matrix m) {
		float[] result = new float[16];

		for (int resultCell = 0; resultCell < result.length; resultCell++) {
			for (int i = 0; i < 4; i++) {
				result[resultCell] += get(resultCell, i) * m.get(i, resultCell);
			}
		}

		return new MatrixImpl(result);
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < matrix.length; i++) {
			if (i % 4 == 0)
				result += "\n";
			result += matrix[i] + "|";
		}
		return result;
	}

	@Override
	public Matrix multSlow(Matrix m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector transformPoint(Vector v) {
		return new VectorImp(matrix[0] * v.x() + matrix[1] * v.y() + matrix[2] * v.z() + matrix[3] * 1.0f,
				matrix[4] * v.x() + matrix[5] * v.y() + matrix[6] * v.z() + matrix[7] * 1.0f,
				matrix[8] * v.x() + matrix[9] * v.y() + matrix[10] * v.z() + matrix[11] * 1.0f);
	}

	@Override
	public Vector transformDirection(Vector v) {

		return new VectorImp(matrix[0] * v.x() + matrix[1] * v.y() + matrix[2] * v.z() + matrix[3] * 0.0f,
				matrix[4] * v.x() + matrix[5] * v.y() + matrix[6] * v.z() + matrix[7] * 0.0f,
				matrix[8] * v.x() + matrix[9] * v.y() + matrix[10] * v.z() + matrix[11] * 0.0f);
	}

	@Override
	public Vector transformNormal(Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix transpose() {
		float[] result = new float[16];
		
		for(int resultIndex = 0; resultIndex < matrix.length; resultIndex++){
				result[resultIndex] = matrix[(resultIndex*4)%15];
		}
		
		result[15] = matrix[15];
		
		return new MatrixImpl(result);
	}

	@Override
	public Matrix invertRigid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix invertFull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] asArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatBuffer asBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fillBuffer(FloatBuffer buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public Matrix getRotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix getTranslation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Matrix m, float epsilon) {
		// TODO Auto-generated method stub
		return false;
	}

}
