#version 330

in vec2 ftexCoord;
in vec3 normalInterp;
in vec3 vertPos;
in vec3 cameraPosition;
uniform float hasNormal;
uniform sampler2D tex;
uniform sampler2D normalTex;
uniform vec3 lightPos;

const float screenGamma = 1.0;

const float diffuseCoeff = 5.6;
const float specularCoeff = 200.3;

void main() {
	vec3 normal;
	if (hasNormal < 0.5) {
		normal = normalize(normalInterp);
	} else {
		// fetch normal from normal map, expand to the [-1, 1] range, and normalize
		normal = 2.0 * (texture2D(normalTex, ftexCoord).rgb - 1.0);
		normal = normalize(normal);
	}

	// Diffuse
	vec3 lightDir = normalize(lightPos - vertPos);
	float diff = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = diff * vec3(1.0);

	// Specular
	float specularStrength = 2.5f;
	vec3 viewDir = normalize(cameraPosition - vertPos);
	vec3 reflectDir = reflect(-lightDir, normal);
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
	vec3 specular = specularStrength * spec * vec3(1.0);

	vec3 decalColor = texture2D(tex, ftexCoord).rgb;

	// output final color
	vec4 colorLinear = vec4((vec3(diffuse) + specular) * decalColor, 1.0);
	vec4 colorGammaCorrected = pow(colorLinear, vec4(1.0 / screenGamma));
	gl_FragColor = colorGammaCorrected;

}
