package scenegraph;

import java.util.Stack;

import ogl.vecmath.Matrix;

public class Traverser {
	Stack<Matrix> matrixStack = new Stack<Matrix>();

	public void visit(Gruppenknoten k) {
		System.out.println(k);
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.lastElement().mult(k.getTransformMatrix()));

		for (Knoten child : k.getChilds()) {
			child.accept(this);
		}
		
	}

	public void visit(Geometrieknoten k) {
		if (matrixStack.isEmpty())
			matrixStack.push(k.getTransformMatrix());
		else
			matrixStack.push(matrixStack.lastElement().mult(k.getTransformMatrix()));
		
		System.out.println(matrixStack.lastElement().transformPoint(k.vector));
	}
}
