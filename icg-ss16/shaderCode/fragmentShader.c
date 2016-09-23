#version 330

in vec2 ftexCoord;
in vec3 fcolor;
in vec3 normalInterp;
in vec3 vertPos;
uniform float hasNormal;
uniform sampler2D tex;
uniform sampler2D normalTex;
uniform vec3 lightPos;
out vec4 fragColor;

float screenGamma = 1.0;

void main() {
	vec3 normal;
		if (hasNormal < 0.5) {
			normal = normalize(normalInterp);
		} else {
			// fetch normal from normal map, expand to the [-1, 1] range, and normalize
			normal = vec3(texture (normalTex, ftexCoord));
			normal = normalize(normal*2-1.0);
		}

		// Diffuse
		vec3 lightDir = normalize(lightPos - vertPos);
		float lambertian = max(dot(lightDir, normal), 0.0);


		vec3 diffuse = lambertian * vec3(1.0);
		float spec = 0.0;

		if(lambertian > 0.0){
			vec3 viewDir = normalize(-vertPos);
			vec3 reflectDir = reflect(-lightDir, normal);
			spec = pow(max(dot(reflectDir, viewDir), 0.0), 50);
		}

		// Specular
		float specularStrength = 1.5f;
		vec3 specular = specularStrength * spec * vec3(1.0);

		vec3 decalColor = vec3(texture(tex, ftexCoord));

		// output final color
		vec4 colorLinear = vec4((vec3(diffuse) + specular) * decalColor, 1.0);
		vec4 colorGammaCorrected = pow(colorLinear, vec4(1.0 / screenGamma));
		fragColor = colorGammaCorrected;
}
