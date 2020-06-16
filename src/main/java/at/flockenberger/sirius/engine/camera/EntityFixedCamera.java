package at.flockenberger.sirius.engine.camera;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.game.entity.Entity;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>EntityFixedCamera</h1><br>
 * This camera extends the base {@link Camera} class. <br>
 * It is able to follow a given {@link Entity}.
 * 
 * @author Florian Wagner
 * @see Camera
 * @see Entity
 */
public class EntityFixedCamera extends Camera
{
	/**
	 * the entity this camera follows
	 */
	private Entity entity;

	/**
	 * the lerp factor<br>
	 * the higher this factor the better this camera keeps up with the entity
	 */
	private float lerpFactor;

	/**
	 * Constructor<br>
	 * Creates a new Camera Object.<br>
	 * The {@link #lerpFactor} will be set to 0.2f as default value.
	 * 
	 * @param entity the entity to follow
	 */
	public EntityFixedCamera(Entity entity)
	{
		this(entity, 0.2f);
	}

	/**
	 * Constructor<br>
	 * Creates a new Camera Object.<br>
	 * 
	 * @param entity     the entity to follow
	 * @param lerpFactor the lerp factor
	 */
	public EntityFixedCamera(Entity entity, float lerpFactor)
	{
		super();
		this.entity = entity;
		this.zoomLevel = 100;
		this.lerpFactor = lerpFactor;
	}

	/**
	 * Sets the entity which this camera should follow.<br>
	 * 
	 * @param p the entity to follow
	 */
	public void setEntity(Entity p)
	{ this.entity = p; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update()
	{
		update(Sirius.timer.getDelta());

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta)
	{
		if (entity != null)
		{
			Vector2f pos = new Vector2f(entity.getPosition());
			pos.x += entity.getWidth() / 2f;
			pos.y += entity.getHeight() / 2f;

			SUtils.lerp(position, pos.negate(), lerpFactor);

		}
	}

}
