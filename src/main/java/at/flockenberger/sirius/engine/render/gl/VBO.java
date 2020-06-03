package at.flockenberger.sirius.engine.render.gl;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

/**
 * <h1>VertexBufferObject</h1><br>
 * Represents a VBO from OpenGL.
 * 
 * @author Florian Wagner
 *
 */
public class VBO extends GLObject
{

	/**
	 * Creates a Vertex Buffer Object (VBO).
	 */
	public VBO()
	{
		id = glGenBuffers();
	}

	/**
	 * Deletes this VBO.
	 */
	@Override
	public void free()
	{
		glDeleteBuffers(id);
	}

	/**
	 * Binds this VBO with specified target.
	 *
	 * @param target Target to bind
	 */
	public void bind(int target)
	{
		glBindBuffer(target, id);
	}

	@Override
	public void bind()
	{
		bind(GL15.GL_ARRAY_BUFFER);
	}

	@Override
	public void unbind()
	{

	}

	/**
	 * Upload vertex data to this VBO with specified target, data and usage.
	 *
	 * @param target Target to upload
	 * @param data   Buffer with the data to upload
	 * @param usage  Usage of the data
	 */
	public void uploadData(int target, FloatBuffer data, int usage)
	{
		glBufferData(target, data, usage);
	}

	/**
	 * Upload null data to this VBO with specified target, size and usage.
	 *
	 * @param target Target to upload
	 * @param size   Size in bytes of the VBO data store
	 * @param usage  Usage of the data
	 */
	public void uploadData(int target, long size, int usage)
	{
		glBufferData(target, size, usage);
	}

	/**
	 * Upload sub data to this VBO with specified target, offset and data. time.
	 *
	 * @param target Target to upload
	 * @param offset Offset where the data should go in bytes
	 * @param data   Buffer with the data to upload
	 */
	public void uploadSubData(int target, long offset, FloatBuffer data)
	{
		glBufferSubData(target, offset, data);
	}

	/**
	 * Upload element data to this EBO with specified target, data and usage.
	 *
	 * @param target Target to upload
	 * @param data   Buffer with the data to upload
	 * @param usage  Usage of the data
	 */
	public void uploadData(int target, IntBuffer data, int usage)
	{
		glBufferData(target, data, usage);
	}

	/**
	 * Getter for the Vertex Buffer Object ID.
	 *
	 * @return Handle of the VBO
	 */
	@Override
	public int getID()
	{
		return id;
	}
}
