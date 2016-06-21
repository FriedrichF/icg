#version 330

in vec3 fcolor;
in vec3 normalInterp;
in vec3 vertPos;
out vec4 fragColor;

const vec3 lightPos = vec3(0.0, 9.0, 9.0);
const vec3 ambientColor = vec3(0.1, 0.0, 0.0);
const vec3 diffuseColor = vec3(0.7, 0.0, 0.0);
const vec3 specColor = vec3(1.0, 1.0, 1.0);
const float shininess = 1.0;
const float screenGamma = 2.2;

void main() {
	vec3 normal = normalize(normalInterp);
	vec3 lightDir = normalize(lightPos - vertPos);
	float lambertian = max(dot(lightDir, normal), 0.0);
	float specular = 0.0;
	vec3 viewDir = normalize(-vertPos);
	vec3 halfDir = normalize(lightDir + viewDir);
	float specAngle = max(dot(halfDir, normal), 0.0);
	specular = pow(specAngle, shininess);
	vec3 colorLinear = ambientColor + lambertian * diffuseColor
			+ specular * specColor;
	vec3 colorGammaCorrected = pow(colorLinear, vec3(1.0 / screenGamma));
	gl_FragColor = vec4(colorGammaCorrected, 1.0);
}