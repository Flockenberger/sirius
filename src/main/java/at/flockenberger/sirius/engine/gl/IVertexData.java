package at.flockenberger.sirius.engine.gl;

import java.nio.FloatBuffer;

import at.flockenberger.sirius.engine.IBindable;

/**
 * <h1>IVertexData</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public interface IVertexData extends IBindable
{
	/**
	 * Draws this vertex data.
	 * 
	 * @param geom
	 * @param first
	 * @param count
	 */
	public void draw(int geom, int first, int count);

	/**
	 * Clears this data
	 * 
	 * @return this vertex data
	 */
	public IVertexData clear();

	/**
	 * Flips this data.
	 * 
	 * @return this vertex data
	 */
	public IVertexData flip();

	/**
	 * Puts multiple vertices into this vertex data
	 * 
	 * @param verts  the vertices to put
	 * @param offset the offset of the data
	 * @param length the length of the data
	 * @return this vertex data
	 */
	public IVertexData put(float[] verts, int offset, int length);

	/**
	 * Puts a float data into this vertex
	 * 
	 * @param v the data to put into this vertex
	 * @return this data
	 */
	public IVertexData put(float v);

	/**
	 * @return the data buffer of this vertex data
	 */
	public FloatBuffer buffer();

	/**
	 * @return the number of components in this vertex data
	 */
	public int getTotalNumComponents();

	/**
	 * @return the number of vertices
	 */
	public int getVertexCount();
}
