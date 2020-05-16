package at.flockenberger.sirius.engine.gl;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * <h1>VertexArrayObject</h1><br>
 * Represents a VAO from OpenGL.
 * 
 * @author Florian Wagner
 *
 */
public class VAO extends GLObject
{

	/**
	 * Creates a Vertex Array Object (VAO).
	 */
	public VAO()
	{
		id = glGenVertexArrays();
	}

	/**
	 * Binds the VAO.
	 */
	@Override
	public void bind()
	{
		glBindVertexArray(id);
	}

	@Override
	public void unbind()
	{
		glBindVertexArray(0);
	}

	/**
	 * Deletes the VAO.
	 */
	@Override
	public void free()
	{
		glDeleteVertexArrays(id);
	}

	/**
	 * Getter for the Vertex Array Object ID.
	 *
	 * @return Handle of the VAO
	 */
	@Override
	public int getID()
	{
		return id;
	}

}
