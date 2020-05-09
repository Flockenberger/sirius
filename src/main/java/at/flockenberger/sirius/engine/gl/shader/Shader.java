package at.flockenberger.sirius.engine.gl.shader;

import org.lwjgl.opengl.GL20;

/**
 * <h1>Shader</h1><br>
 * Base class for all shaders.
 * 
 * @author Florian Wagner
 *
 */
public abstract class Shader
{
	protected ShaderCode code;
	protected int id;

	public Shader()
	{
		code = new ShaderCode();
	}

	public Shader(String code)
	{
		this();
		getShaderCode().setCode(code);
	}

	/**
	 * @return the code of this shader
	 */
	public ShaderCode getShaderCode()
	{
		return this.code;
	}

	/**
	 * @return the id of this shader
	 */
	public int getID()
	{
		return this.id;
	}

	/**
	 * Deletes the shader after compilation and linking
	 */
	public void deleteShader()
	{
		GL20.glDeleteShader(getID());
	}

	/**
	 * Attaches this shader to the program with the given id.
	 * 
	 * @param program the program to attach this shader to
	 */
	public void attachShader(int program)
	{
		GL20.glAttachShader(program, getID());
	}

	/**
	 * Compiles the shader and sets the id.
	 * 
	 * @return the id of the compiled shader
	 */
	public abstract long compileShader();

}
