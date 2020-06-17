package at.flockenberger.sirius.engine.collision;

import org.joml.Vector2f;
import org.joml.Vector3f;

import at.flockenberger.sirius.game.entity.Entity;

public class BoundingBox extends Bounds
{

	public BoundingBox()
	{
		super(0, 0, 0, 0, 0, 0);
	}

	public BoundingBox(float minX, float minY, float minZ, float width, float height, float depth)
	{
		super(minX, minY, minZ, width, height, depth);
	}

	public BoundingBox(float minX, float minY, float width, float height)
	{
		super(minX, minY, 0, width, height, 0);
	}

	public BoundingBox(Entity entity)
	{
		set(entity);
	}

	public void set(Entity entity)
	{
		this.minX = entity.getPosition().x;
		this.minY = entity.getPosition().y;
		this.minZ = 0;
		this.width = entity.getWidth();
		this.height = entity.getHeight();
		this.depth = 0;
		this.maxX = minX + width;
		this.maxY = minY + height;
		this.maxZ = minZ + depth;
	}
	
	
	@Override
	public boolean isEmpty()
	{ return getMaxX() < getMinX() || getMaxY() < getMinY() || getMaxZ() < getMinZ(); }

	@Override
	public boolean contains(Vector2f p)
	{
		if (p == null)
			return false;
		return contains(p.x, p.y, 0.0f);
	}

	@Override
	public boolean contains(Vector3f p)
	{
		if (p == null)
			return false;
		return contains(p.x, p.y, p.z);
	}

	@Override
	public boolean contains(float x, float y)
	{
		return contains(x, y, 0.0f);
	}

	@Override
	public boolean contains(float x, float y, float z)
	{
		if (isEmpty())
			return false;
		return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY() && z >= getMinZ() && z <= getMaxZ();
	}

	@Override
	public boolean contains(Bounds b)
	{
		if ((b == null) || b.isEmpty())
			return false;
		return contains(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
	}

	@Override
	public boolean contains(float x, float y, float w, float h)
	{
		return contains(x, y) && contains(x + w, y + h);

	}

	@Override
	public boolean contains(float x, float y, float z, float w, float h, float d)
	{
		return contains(x, y, z) && contains(x + w, y + h, z + d);
	}

	@Override
	public boolean intersects(Bounds b)
	{
		if ((b == null) || b.isEmpty())
			return false;
		return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
	}

	@Override
	public boolean intersects(float x, float y, float w, float h)
	{
		return intersects(x, y, 0, w, h, 0);
	}

	@Override
	public boolean intersects(float x, float y, float z, float w, float h, float d)
	{
		if (isEmpty() || w < 0 || h < 0 || d < 0)
			return false;
		return (x + w >= getMinX() && y + h >= getMinY() && z + d >= getMinZ() && x <= getMaxX() && y <= getMaxY()
				&& z <= getMaxZ());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof BoundingBox)
		{
			BoundingBox other = (BoundingBox) obj;
			return getMinX() == other.getMinX() && getMinY() == other.getMinY() && getMinZ() == other.getMinZ()
					&& getWidth() == other.getWidth() && getHeight() == other.getHeight()
					&& getDepth() == other.getDepth();
		} else
			return false;
	}

	@Override
	public int hashCode()
	{
		long bits = 7L;
		bits = 31L * bits + Float.floatToIntBits(getMinX());
		bits = 31L * bits + Float.floatToIntBits(getMinY());
		bits = 31L * bits + Float.floatToIntBits(getMinZ());
		bits = 31L * bits + Float.floatToIntBits(getWidth());
		bits = 31L * bits + Float.floatToIntBits(getHeight());
		bits = 31L * bits + Float.floatToIntBits(getDepth());
		return (int) (bits ^ (bits >> 32));
	}

	@Override
	public String toString()
	{
		return "BoundingBox [" + "minX:" + getMinX() + ", minY:" + getMinY() + ", minZ:" + getMinZ() + ", width:"
				+ getWidth() + ", height:" + getHeight() + ", depth:" + getDepth() + ", maxX:" + getMaxX() + ", maxY:"
				+ getMaxY() + ", maxZ:" + getMaxZ() + "]";
	}
}
