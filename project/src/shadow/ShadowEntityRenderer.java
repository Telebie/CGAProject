package shadow;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.Transformation;

public class ShadowEntityRenderer {
	
	private Matrix4f projectionViewMatrix;				//rendern mit LichtQuelle
	private ShadowShader shader;
	
	
	protected ShadowEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix) {
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}
	
	
	private void bindModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);				//braucht keine normals/textures
	}
	
	
	private void prepareInstance(Entity entity) {
		Matrix4f modelMatrix = Transformation.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		Matrix4f mvpMatrix = modelMatrix.mul(projectionViewMatrix);
		shader.loadMvpMatrix(mvpMatrix);
	}
	
	
	
	protected void render(Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			RawModel rawModel = model.getRawModel();
			bindModel(rawModel);								//jedes Model einbinden
			for (Entity entity : entities.get(model)) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getIndexcount(),		//final render
						GL11.GL_UNSIGNED_INT, 0);
			}
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
