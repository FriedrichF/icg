#version 140
in vec3 vertex;//inputPosition
in vec3 normal;
in vec3 color;
out vec3 fcolor;
uniform mat4 modelMatrix, normalMatrix, viewMatrix, projectionMatrix;
varying vec3 vertPos;
varying vec3 normalInterp;
void main() {
	fcolor = color;
	vec4 vertPos4 =  modelMatrix*vec4(vertex, 1.0);
//	gl_Position = projectionMatrix *  modelMatrix * vertPos4;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1.0);
//	gl_Position = projectionMatrix * viewMatrix * vertPos4;
	vertPos = vec3(vertPos4) / vertPos4.w;
	normalInterp = vec3(normalMatrix * vec4(normal, 0.0));
}
//void main() {
//	fcolor = color;
//	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertex, 1.0);
//}