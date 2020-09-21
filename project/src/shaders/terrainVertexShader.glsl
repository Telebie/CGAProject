#version 400 core

in vec3 position;			//Position des aktuellen Vertex
in vec2 textureCoords;		
in vec3 normal;


out vec2 pass_textureCoords;
out vec3 surfaceNormal;				//Normale mit den Transformationen
out vec3 toLightVector;				//Vector in Richtung der Lichtquelle 
out vec3 toCameraVector; 			//Vektor vom Vertex zur Kamera 


uniform mat4 transMatrix;			//man kann die Matrix für jede Entität(Model) uploaden
uniform mat4 projectionMatrix;		//Entity platzieren
uniform mat4 viewMatrix; 			//Camera Matrix negativ
uniform vec3 lightPosition;


void main(void){														//Transformation Matrix (Model) transformieren bevor es gerendert wird  
	
	vec4 worldPosition = transMatrix * vec4(position, 1.0);				//Vertex könnte bewegt worden sein 
	
	gl_Position = projectionMatrix * viewMatrix * worldPosition; 		//apply the transformation to each vertex position 
	pass_textureCoords = textureCoords * 500;							// Multipliziert alle TextureCoords mit 40
	
	surfaceNormal = (transMatrix * vec4(normal, 0.0)).xyz;		//transMatrix, da das Model vorher bewegt wird und die Normale somit auch
	toLightVector = lightPosition - worldPosition.xyz;			//xyz da vec3 
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;	//Kamera Vektor umkehren * vec4 bekommen = Kamera Position 
}