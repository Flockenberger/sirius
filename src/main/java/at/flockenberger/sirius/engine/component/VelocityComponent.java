package at.flockenberger.sirius.engine.component;

import org.joml.Vector2f;

public class VelocityComponent implements IComponent
{
	private Vector2f velocity;

	public VelocityComponent()
	{
		velocity = new Vector2f(0);
	}

	public VelocityComponent(Vector2f velocity)
	{
		super();
		this.velocity = velocity;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Vector2f getVelocity()
	{
		return velocity;
	}

}
