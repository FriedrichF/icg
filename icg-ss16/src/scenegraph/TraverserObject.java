package scenegraph;

import java.util.Stack;

import icg.math.FactoryImpl;
import myMath.VectorImpl;
import ogl.vecmath.Matrix;
import opengl.Shader;

public class TraverserObject extends Traverser {
	Stack<Matrix> matrixStack = new Stack<Matrix>();

	public void visit(Gruppenknoten k) {
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(k.getTransformMatrix()));

		for (Knoten child : k.getChilds()) {
			child.accept(this);
		}
		matrixStack.pop();
	}

	public void visit(Geometrieknoten k) {
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(k.getTransformMatrix()));
		
		Matrix topMatrix = matrixStack.pop();
		
		Shader.getInstance().addVertexArrayObject(k);
		Shader.getInstance().addMatrices(topMatrix);
		
	}

	public void visit(Kameraknoten kameraknoten) {
		if (matrixStack.isEmpty())
			matrixStack.push(kameraknoten.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(kameraknoten.getTransformMatrix()));
		
		Matrix pop = matrixStack.pop().invertFull();
		
//		Shader.getInstance().addViewMatrix(pop);
		Shader.getInstance().addViewMatrix(kameraknoten.getCamera().lookThrough());
	}

	public void visit(Lichtknoten lichtknoten) {
		if (matrixStack.isEmpty())
			matrixStack.push(lichtknoten.getTransformMatrix());
		else
			matrixStack.push(matrixStack.peek().mult(lichtknoten.getTransformMatrix()));
		
		Matrix pop = matrixStack.pop();
		
		Shader.getInstance().addLightPos(pop);
	}
}
