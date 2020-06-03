package at.flockenberger.sirius.engine.collision;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * <h1>Bounds</h1><br>
 * This class describes the 3D bounds of an object.
 * 
 * @author Florian Wagner
 *
 */
public abstract class Bounds
{
	/**
	 * min x position of this bounds
	 */
	protected float minX;

	/**
	 * min y position of this bounds
	 */
	protected float minY;

	/**
	 * min z position of this bounds
	 */
	protected float minZ;

	/**
	 * max x position of this bounds
	 */
	protected float maxX;

	/**
	 * max y position of this bounds
	 */
	protected float maxY;

	/**
	 * max z position of this bounds
	 */
	protected float maxZ;

	/**
	 * the depth of this bounds
	 */
	protected float depth;

	/**
	 * the width of this bounds
	 */
	protected float width;

	/**
	 * the height of this bounds
	 */
	protected float height;

	protected Bounds()
	{

	}

	/**
	 * Creates a new instance of {@code Bounds} class.
	 * 
	 * @param minX   the X coordinate of the upper-left corner
	 * @param minY   the Y coordinate of the upper-left corner
	 * @param minZ   the minimum z coordinate of the {@code Bounds}
	 * @param width  the width of the {@code Bounds}
	 * @param height the height of the {@code Bounds}
	 * @param depth  the depth of the {@code Bounds}
	 */
	protected Bounds(float minX, float minY, float minZ, float width, float height, float depth)
	{
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.maxX = minX + width;
		this.maxY = minY + height;
		this.maxZ = minZ + depth;
	}

	public void setMinX(float minX)
	{
		this.minX = minX;
	}

	public void setMinY(float minY)
	{
		this.minY = minY;
	}

	public void setMinZ(float minZ)
	{
		this.minZ = minZ;
	}

	public void setDepth(float depth)
	{
		this.depth = depth;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public final float getMinX()
	{
		return minX;
	}

	public final float getMinY()
	{
		return minY;
	}

	public final float getMinZ()
	{
		return minZ;
	}

	public final float getMaxX()
	{
		return maxX;
	}

	public final float getMaxY()
	{
		return maxY;
	}

	public final float getMaxZ()
	{
		return maxZ;
	}

	public final float getDepth()
	{
		return depth;
	}

	public final float getWidth()
	{
		return width;
	}

	public final float getHeight()
	{
		return height;
	}

	public final float getCenterX()
	{
		return (getMaxX() + getMinX()) / 2f;
	}

	public final float getCenterY()
	{
		return (getMaxY() + getMinY()) / 2f;
	}

	public final float getCenterZ()
	{
		return (getMaxZ() + getMinZ()) / 2f;
	}

	public abstract boolean isEmpty();

	public abstract boolean contains(Vector2f p);

	public abstract boolean contains(Vector3f p);

	public abstract boolean contains(float x, float y);

	public abstract boolean contains(float x, float y, float z);

	public abstract boolean contains(Bounds b);

	public abstract boolean contains(float x, float y, float w, float h);

	public abstract boolean contains(float x, float y, float z, float w, float h, float d);

	public abstract boolean intersects(Bounds b);

	public abstract boolean intersects(float x, float y, float w, float h);

	public abstract boolean intersects(float x, float y, float z, float w, float h, float d);

}
