#version 330

in vec3 vertex;
in vec3 color;
in vec3 normal;
out vec3 fcolor;
out vec3 vertPos;
out vec3 normalInterp;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 normalMatrix;

void main() {
	fcolor = color;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix
			* vec4(vertex, 1.0);
	vec4 vertPos4 = viewMatrix * modelMatrix * vec4(vertex, 1.0);
	vertPos = vec3(vertPos4) / vertPos4.w;
	normalInterp = vec3(normalMatrix * vec4(normal, 0.0));
}
