package at.flockenberger.sirius.engine.render.gl.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import org.lwjgl.opengl.GL20;

import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>FragmentShader</h1><br>
 * Represents a fragment shader.
 * 
 * @author Florian Wagner
 *
 */
public class FragmentShader extends Shader
{
	public FragmentShader(String shader)
	{
		super(shader);
	}

	public FragmentShader()
	{
		super();
	}

	@Override
	public long compileShader()
	{
		SLogger.getSystemLogger().info("Compiling Fragment Shader");

		int sID = -1;
		sID = glCreateShader(GL20.GL_FRAGMENT_SHADER);
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
