package at.flockenberger.sirius.engine.postprocess;

import at.flockenberger.sirius.engine.gl.shader.Shader;
import at.flockenberger.sirius.engine.gl.shader.ShaderProgram;

public abstract class PostProcessingFilter extends ShaderProgram
{

	public PostProcessingFilter(ShaderProgram program)
	{
		this((Shader[]) program.getShader().toArray());
	}

	public PostProcessingFilter(Shader... shader)
	{
		super(shader);
	}

	public PostProcessingFilter()
	{
		
	}
	
	public abstract void update();
}
