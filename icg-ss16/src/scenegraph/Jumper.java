package scenegraph;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.Input;
import ogl.vecmath.Vector;

public class Jumper extends Entity {

	private int axis;
	private float speed;
	private float max;
	private float min;
	private boolean directionUp;

	public Jumper(String name, Knoten knoten, Vector axis, float distance, float speed) {
		super(name, knoten);

		this.speed = speed;
		directionUp = true;

		if (axis.x() == 1)
			this.axis = 0;
		else if (axis.y() == 1)
			this.axis = 1;
		else if (axis.z() == 1)
			this.axis = 1;

		// Wenn es nach unten hüpfen soll
		if (distance > 0) {
			min = knoten.getTransformMatrix().get(3, this.axis);
			max = min + distance;
		} else {
			max = knoten.getTransformMatrix().get(3, this.axis);
			min = max + distance;
		}
	}

	@Override
	public void simulate(float elapsed, Input input) {
		Vector translation = new VectorImpl(0, 0, 0);
		if ((directionUp && this.getKnoten().getTransformMatrix().get(3, axis) < max)
				|| !directionUp && this.getKnoten().getTransformMatrix().get(3, axis) < min) {
			directionUp = true;
			translation.asArray()[axis] += elapsed * speed;
		} else if ((!directionUp && this.getKnoten().getTransformMatrix().get(3, axis) > min)
				|| directionUp && this.getKnoten().getTransformMatrix().get(3, axis) > max) {
			directionUp = false;
			translation.asArray()[axis] -= elapsed * speed;
		}

		this.getKnoten().setTransformMatrix(
				this.getKnoten().getTransformMatrix().mult(FactoryImpl.vecmath.translationMatrix(translation)));

	}

}
