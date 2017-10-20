package graphics;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import main.Main;
import misc.Utils;

public class Renderer
{
	public static final float FOV = (float)(Math.toRadians(60.0f));
	public static final float Z_NEAR = 0.01f;
	public static final float Z_FAR = 1000.0f;
	public static Transformation transformation;
	private ShaderProgram shaderProgram;
	private ShaderProgram shaderHUD;
	public static List<Model> models;
	public static List<Model> huds;
	public static Matrix4f projectionMatrix;
	public static Matrix4f viewMatrix;
	private void renderHUD()
	{
		shaderHUD.bind();
		
		//shaderHUD.setUniform("textureSampler", 0);
		Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, 
				Main.window.getWidth(), Main.window.getHeight(), 0);
		
		for(Model model: huds)
		{
			Mesh mesh = model.getMesh();
			int hasTexture = 0;
			if(mesh.getTexture() != null)
				hasTexture = 1;
			
			Matrix4f projModelMatrix = transformation.buildOrtoProjModelMatrix(model, ortho);
			shaderHUD.setUniform("colour", mesh.getColor());
			shaderHUD.setUniform("hasTexture", hasTexture);
			shaderHUD.setUniform("projModelMatrix", projModelMatrix);
			mesh.render();
		}
		
		shaderHUD.unbind();
	}
	
	private void renderModels()
	{
		shaderProgram.bind();

		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		
		shaderProgram.setUniform("textureSampler", 0);
		
		for(Model model: models)
		{
			Mesh mesh = model.getMesh();
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(model, viewMatrix);
			int hasTexture = 0;
			if(mesh.getTexture() != null)
				hasTexture = 1;
			
			shaderProgram.setUniform("projectionMatrix", projectionMatrix);
			shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
			shaderProgram.setUniform("color", mesh.getColor());
			shaderProgram.setUniform("hasTexture", hasTexture);
			
			mesh.render();
		}
		
		shaderProgram.unbind();
	}

	private void initShaderHUD()
	{
		shaderHUD = new ShaderProgram();
		
		shaderHUD.createVertexShader(Utils.loadFileAsText("/shaders/hud.vs"));
		shaderHUD.createFragmentShader(Utils.loadFileAsText("/shaders/hud.fs"));
		shaderHUD.link();
		
		shaderHUD.createUniform("projModelMatrix");
		//shaderHUD.createUniform("textureSampler");
		shaderHUD.createUniform("colour");
		shaderHUD.createUniform("hasTexture");
	}
	
	private void initShaderProgram()
	{
		shaderProgram = new ShaderProgram();
		
		shaderProgram.createVertexShader(Utils.loadFileAsText("/shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadFileAsText("/shaders/fragment.fs"));
		shaderProgram.link();
		
		shaderProgram.createUniform("textureSampler");
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelViewMatrix");
		shaderProgram.createUniform("color");
		shaderProgram.createUniform("hasTexture");
	}
	
	public void cleanup()
	{
		shaderProgram.cleanup();
		shaderHUD.cleanup();
	}
	
	public void render()
	{
		GL11.glViewport(0, 0, Main.window.getWidth(), Main.window.getHeight());
		
		projectionMatrix = new Matrix4f(transformation.getProjectionMatrix(FOV, Main.window.getWidth(), 
				Main.window.getHeight(), Z_NEAR, Z_FAR));
		viewMatrix = transformation.getViewMatrix(Main.camera, viewMatrix);
		renderModels();
		renderHUD();
	}
	
	public void clear()
	{
		models.clear();
		huds.clear();
	}
	
	public void init()
	{
		initShaderProgram();
		initShaderHUD();
	}
	
	public Renderer()
	{
		Renderer.transformation = new Transformation();
		models = new ArrayList<>();
		huds = new ArrayList<>();
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		init();
	}
}
