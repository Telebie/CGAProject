#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_colour;


uniform sampler2D textureSampler;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity; 


void main (void){
	
	/**Light**/
	vec3 unitNormal = normalize(surfaceNormal); 								//Vektoren normalisieren um gleiche Länge zu bekommen
	vec3 unitLightVector = normalize(toLightVector);
	
	float normalDot = dot(unitNormal, unitLightVector);							//Skalarprodukt -> Helligkeit des Pixels 
	float brightness = max(normalDot, 0.3);										//Skalarprodukt zwischen 0-1 ->wenn es höher gesetzt wird werden dunkle stellen heller 
	vec3 diffuse = brightness * lightColour;									//final light value
	
	/**Reflectivity**/
	vec3 unitVectorToCamera = normalize(toCameraVector);						//Vektor zur Kamera 
	vec3 lightDirection = -(unitLightVector);									//Vektor von Quelle zu Vertex 
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);			//GLSL: reflect(incoming LightVector, normale)
	
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);	//Skalarprodukt um zu schauen wie nah beieinander die Vektoren sind für die Stärke der Reflektion
	specularFactor = max(specularFactor, 0.0);									//Keine negativen Zahlen
	float dampedFactor = pow(specularFactor, shineDamper);						//Stärke des Dampers hinzufügen
	vec3 finalSpecular = dampedFactor * reflectivity * lightColour;				//Lichtfarbe und Reflectivity hinzufügen
	
	out_colour = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoords) + vec4(finalSpecular, 1.0);
} 