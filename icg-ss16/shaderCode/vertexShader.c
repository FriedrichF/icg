#version 330

in vec3 vertex;
in vec3 color;
in vec3 normal;
in vec2 texCoord;
out vec2 ftexCoord;
out vec3 vertPos;
out vec3 normalInterp;
out vec3 cameraPosition;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 normalMatrix;

void main() {
	ftexCoord = texCoord;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix
			* vec4(vertex, 1.0);
	vec4 vertPos4 = modelMatrix * vec4(vertex, 1.0);
	vertPos = vec3(vertPos4) / vertPos4.w;
	normalInterp = vec3(normalMatrix * vec4(normal, 0.0));
	cameraPosition = vec3(viewMatrix[3]) / viewMatrix[3][3];
}
