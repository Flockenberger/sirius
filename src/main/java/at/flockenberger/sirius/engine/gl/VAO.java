package at.flockenberger.sirius.engine.gl;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * <h1>VAO</h1><br>
 * VAO stands for Vertex-Array-Object. It describes a series of vertex data that
 * is combined into one object to draw.
 * 
 * @author Florian Wagner
 *
 */
public class VAO implements IVertexData
{

	protected VertexAttribute[] attributes;
	private int numComponents;
	private FloatBuffer buffer;
	private int vertCount;

	public VAO(int vertCount, VertexAttribute... attributes)
	{
		this.attributes = attributes;
		for (VertexAttribute a : attributes)
			numComponents += a.numComponents;
		this.vertCount = vertCount;

		// our buffer which holds our data
		this.buffer = BufferUtils.createFloatBuffer(vertCount * numComponents);
	}

	public VAO(int vertCount, List<VertexAttribute> attributes)
	{
		this(vertCount, attributes.toArray(new VertexAttribute[attributes.size()]));
	}

	@Override
	public void draw(int geom, int first, int count)
	{
		glDrawArrays(geom, first, count);

	}

	@Override
	public IVertexData clear()
	{
		buffer.clear();
		return this;
	}

	@Override
	public IVertexData flip()
	{
		buffer.flip();
		return this;
	}

	@Override
	public IVertexData put(float[] verts, int offset, int length)
	{
		buffer.put(verts, offset, length);
		return this;
	}

	@Override
	public IVertexData put(float v)
	{
		buffer.put(v);
		return this;
	}

	@Override
	public FloatBuffer buffer()
	{
		return buffer;
	}

	@Override
	public int getTotalNumComponents()
	{
		return numComponents;
	}

	@Override
	public int getVertexCount()
	{
		return vertCount;
	}

	@Override
	public void bind()
	{
		int offset = 0;
		// 4 bytes per float
		int stride = numComponents * 4;

		for (int i = 0; i < attributes.length; i++)
		{
			VertexAttribute a = attributes[i];
			buffer.position(offset);
			glEnableVertexAttribArray(a.location);
			glVertexAttribPointer(a.location, a.numComponents, GL11.GL_FLOAT, false, stride, buffer);
			offset += a.numComponents;
		}

	}

	@Override
	public void unbind()
	{
		for (int i = 0; i < attributes.length; i++)
		{
			VertexAttribute a = attributes[i];
			glDisableVertexAttribArray(a.location);
		}
	}

}
