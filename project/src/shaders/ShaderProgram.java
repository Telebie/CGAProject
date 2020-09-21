package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/*** Shader ***/

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); 	//Upload Matrizen in the Buffer 
	
	
	public ShaderProgram(String vertexFile, String fragmentFile) {					//Files bekommen 
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER); 
		programID = GL20.glCreateProgram(); 										//programmID = neues Programm 
		GL20.glAttachShader(programID, vertexShaderID);								//Shader zum Programm hinzufügen
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);												//programmID nutzen um auf den shader zuzugreifen
		GL20.glValidateProgram(programID); 
		getAllUniformLocations();
	}
	
	
	
	/***Uniforms***/
	
	protected abstract void getAllUniformLocations(); 
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName); 
	}
	
	//Upload Float Value to UniformLocation
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	//Upload Vector to UniformLocation
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	//Upload Boolean to UniformLocation
	protected void loadBoolean(int location, boolean value) {
		float toLoad = 0;
		if(value) {
			toLoad=1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	//Upload Matrix to UniformLocation
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4(location, false, matrixBuffer); 
	}
	
	
	
	
	/*** Attributes ***/
	
	protected abstract void bindAttributes();
	
	//Attribut des VAO einbinden 
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanup() {
		stop();
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	
	
	//Shader-sourcecode File uploaden
	private static int loadShader(String file, int type) {	//type=vertex/fragement Shader
		StringBuilder shaderSource = new StringBuilder(); 	
		
		try {	//öffnet shader files, liest alles in einen String
			BufferedReader reader = new BufferedReader (new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n"); 
			}
			reader.close(); 
		} catch (IOException e) {
			System.err.println("Could not read file!"); 
			e.printStackTrace();
			System.exit(-1);
		}
		
		int shaderID = GL20.glCreateShader(type); 			//Neuen Shader erstellen
		GL20.glShaderSource(shaderID, shaderSource); 		//ShaderSourceString zum Shader hinzufügen
		GL20.glCompileShader(shaderID);						//Shader kompilieren 
		if(GL20.glGetShaderi(shaderID,  GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1); 
		}
		return shaderID; 
	}
	
}
