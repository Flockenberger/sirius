package at.flockenberger.sirius.engine.render;

import org.lwjgl.opengl.GL20;

/**
 * <h1>ShapeType</h1><br>
 * The ShapeType defines the immediate drawing style of shapes.<br>
 * 
 * 
 * @author Florian Wagner
 *
 */
public enum ShapeType
{
	/**
	 * Draws the shape as points
	 */
	POINT(GL20.GL_POINTS),

	/**
	 * Draws the shape as lines
	 */
	LINE(GL20.GL_LINES),

	/**
	 * Draws the shape as triangles<br>
	 * Also referred to as "filled" drawing
	 */
	TRIANGLE(GL20.GL_TRIANGLES),

	/**
	 * Draws the shape as quads.
	 */
	QUAD(GL20.GL_QUADS);

	/**
	 * the opengl int type
	 */
	private final int _type;

	ShapeType(int type)
	{
		this._type = type;
	}

	/**
	 * @return the opengl type for this drawing style
	 */
	public int getType()
	{ return _type; }
}
