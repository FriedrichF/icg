package scenegraph;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.app.Input;
import opengl.Shader;

public class Driver extends Entity {

	private float speed;
	private boolean pressed = false;

	public Driver(String name, Knoten knoten, float speed) {
		super(name, knoten);
		this.speed = speed;
	}

	@Override
	public void simulate(float elapsed, Input input) {
		int xRichtung = 0;
		int zRichtung = 0;

		if (this.getKnoten() instanceof Kameraknoten) {
			Kameraknoten cameraKnoten = (Kameraknoten) this.getKnoten();
			if (input.isKeyDown(GLFW_KEY_W))
				cameraKnoten.getCamera().walkForward(elapsed);
			if (input.isKeyDown(GLFW_KEY_S))
				cameraKnoten.getCamera().walkBackwards(elapsed);
			if (input.isKeyDown(GLFW_KEY_A))
				cameraKnoten.getCamera().strafeLeft(elapsed);
			if (input.isKeyDown(GLFW_KEY_D))
				cameraKnoten.getCamera().strafeRight(elapsed);
			

			if (input.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
				if(!pressed){
					cameraKnoten.getCamera().setMousePosition(input.getMousePosition().x(), input.getMousePosition().y());
					pressed = true;
				}
				// controll camera yaw from x movement fromt the mouse
				cameraKnoten.getCamera().yaw(cameraKnoten.getCamera().getDX(input) * 0.2f);
				// controll camera pitch from y movement fromt the mouse
				cameraKnoten.getCamera().pitch(cameraKnoten.getCamera().getDY(input) * 0.2f);
			}else{
				pressed = false;
			}

		} else {
			if (input.isKeyDown(GLFW_KEY_W))
				zRichtung = -1;
			if (input.isKeyDown(GLFW_KEY_S))
				zRichtung += 1;
			if (input.isKeyDown(GLFW_KEY_A))
				this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix()
						.mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0, 1, 0), 30 * elapsed)));
			if (input.isKeyDown(GLFW_KEY_D))
				this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix()
						.mult(FactoryImpl.vecmath.rotationMatrix(new VectorImpl(0, 1, 0), -30 * elapsed)));

			this.getKnoten().setTransformMatrix(this.getKnoten().getTransformMatrix()
					.mult(FactoryImpl.vecmath.translationMatrix(xRichtung * speed, 0, zRichtung * speed)));
		}
	}

}
