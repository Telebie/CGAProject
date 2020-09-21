#version 150

in vec3 in_position;

uniform mat4 mvpMatrix;

void main(){
	gl_Position = mvpMatrix * vec4(in_position, 1.0); 	//Model-View-Orijection-Matrix * Vertex Position
}