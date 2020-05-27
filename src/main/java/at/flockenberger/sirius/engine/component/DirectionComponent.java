package at.flockenberger.sirius.engine.component;

import org.joml.Vector2f;

public class DirectionComponent implements IComponent
{
	private Vector2f direction;

	public DirectionComponent()
	{
		direction = new Vector2f(0);
	}

	public DirectionComponent(Vector2f d)
	{
		this.direction = d;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Vector2f getDirection()
	{
		return direction;
	}

}
