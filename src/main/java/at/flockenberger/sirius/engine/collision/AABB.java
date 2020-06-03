package at.flockenberger.sirius.engine.collision;

import org.joml.Vector2f;

import at.flockenberger.sirius.game.entity.Entity;

/**
 * <h1>AABB</h1><br>
 * Axis aligned bounding box
 * 
 * @author Florian Wagner
 *
 */
public class AABB
{

	private Vector2f min;
	private Vector2f max;

	public AABB(Entity entity)
	{
		set(entity);
	}

	public void set(Entity entity)
	{
		set(entity.getPosition().x, entity.getPosition().y, entity.getWidth(), entity.getHeight());
	}

	public void set(float x, float y, float width, float height)
	{
		if (min == null)
			min = new Vector2f(x, y);
		else
			min.set(x, y);
		if (max == null)
			max = new Vector2f(x + width, y + height);
		else
			max.set(x + width, y + height);

	}

	/**
	 * Checks if this AABB intersects another AABB.
	 *
	 * @param other The other AABB
	 *
	 * @return true if a collision was detected.
	 */
	public boolean intersects(AABB other)
	{
		if (this.max.x < other.min.x)
			return false;

		if (this.max.y < other.min.y)
			return false;

		if (this.min.x > other.max.x)
			return false;

		if (this.min.y > other.max.y)
			return false;

		return true;
	}
}
