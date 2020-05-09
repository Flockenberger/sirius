package at.flockenberger.sirius.engine.gl.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>VertexShader</h1><br>
 * Represents a vertex shader.
 * 
 * @author Florian Wagner
 *
 */
public class VertexShader extends Shader
{

	public VertexShader(String shader)
	{
		super(shader);
	}

	public VertexShader()
	{
		super();
	}

	@Override
	public long compileShader()
	{
		SLogger.getSystemLogger().info("Compiling Vertex Shader");

		int sID = -1;
		sID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(sID, getShaderCode().getCode());
		glCompileShader(sID);

		int success = glGetShaderi(sID, GL_COMPILE_STATUS);
		if (success == 0)
		{
			String infoLog = glGetShaderInfoLog(sID);
			SLogger.getSystemLogger().error("COMPILATION_FAILED\n" + infoLog);
		}
		this.id = sID;

		return sID;
	}

}
