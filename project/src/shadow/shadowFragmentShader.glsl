#version 330

out vec4 out_colour;

uniform sampler2D modelTexture;			// Farben und Licht werden nicht gebraucht -> nur die vertex Positions

void main(void){

	out_colour = vec4(1.0);
	
}