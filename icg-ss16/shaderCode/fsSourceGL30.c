#version 140	
in vec3 fcolor;
out vec4 fragColor;	
varying vec3 normalInterp;
varying vec3 vertPos;
uniform int mode;
const vec3 lightPos = vec3(1.0,1.0,1.0);
const vec3 ambientColor = vec3(0.1, 0.0, 0.0);
const vec3 diffuseColor = vec3(0.7, 0.0, 0.0);
const vec3 specColor = vec3(0.5, 0.5, 0.5);
const float shininess = 16.0;
const float screenGamma = 2.2; // Assume the monitor is calibrated to the sRGB color space
void main() {
	  vec3 normal = normalize(normalInterp);
	  vec3 lightDir = normalize(lightPos - vertPos);
	  float lambertian = max(dot(lightDir,normal), 0.0);
	   float specular = 0.0;
	   if(lambertian > 0.0) {
		   vec3 viewDir = normalize(-vertPos);
		   // this is blinn phong
		      vec3 halfDir = normalize(lightDir + viewDir);
		      float specAngle = max(dot(halfDir, normal), 0.0);
		      specular = pow(specAngle, shininess);
		      // this is phong (for comparison)
		         if(mode == 2) {
		           vec3 reflectDir = reflect(-lightDir, normal);
		           specAngle = max(dot(reflectDir, viewDir), 0.0);
		           // note that the exponent is different here
		           specular = pow(specAngle, shininess/4.0);
		         }
	   }
	   vec3 colorLinear = ambientColor +
	                        lambertian * diffuseColor +
	                        specular * specColor;
	   vec3 colorGammaCorrected = pow(colorLinear, vec3(1.0/screenGamma));
	   fragColor = vec4(colorGammaCorrected, 1.0);
			}
//
//	void main() {
//	  fragColor = vec4(fcolor, 1.0);
//				}