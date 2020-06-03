package at.flockenberger.sirius.engine.render.gl;

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
	
	public static final int Position = 1;
	public static final int ColorUnpacked = 2;
	public static final int ColorPacked = 4;
	public static final int Normal = 8;
	public static final int TextureCoordinates = 16;
	public static final int Generic = 32;
	public static final int BoneWeight = 64;
	public static final int Tangent = 128;
	public static final int BiNormal = 256;

	/**
	 * Creates a new vertex attribute. <br>
	 * 
	 * @param location      the opengl location of this attribute
	 * @param name          the name of this attribute
	 * @param numComponents the number of components
	 */
	public VertexAttribute(int location, String name, int numComponents)
	{
		this.location = location;
		this.name = name;
		this.numComponents = numComponents;
	}

	public VertexAttribute(int location, int numComponents, String name)
	{
		this.location = location;
		this.name = name;
		this.numComponents = numComponents;

	}

	/**
	 * @return the name of the vertex attribute
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the number of components
	 */
	public int getNumComponents()
	{
		return numComponents;
	}

	/**
	 * @return the location of this attribute
	 */
	public int getLocation()
	{
		return location;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return name + " (" + numComponents + ")";
	}
}