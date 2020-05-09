package at.flockenberger.sirius.engine.gl;

/**
 * <h1>VertexAttribute</h1><br>
 * Describes a single vertex attribute for opengl.
 * 
 * @author Florian Wagner
 *
 */
public class VertexAttribute
{

	protected final String name;
	protected final int numComponents;
	protected final int location;

	public VertexAttribute(int location, String name, int numComponents)
	{
		this.location = location;
		this.name = name;
		this.numComponents = numComponents;
	}

	public String getName()
	{
		return name;
	}

	public int getNumComponents()
	{
		return numComponents;
	}

	public int getLocation()
	{
		return location;
	}

	public String toString()
	{
		return name + " (" + numComponents + ")";
	}
}