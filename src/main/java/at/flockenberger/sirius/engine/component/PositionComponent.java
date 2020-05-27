package at.flockenberger.sirius.engine.component;

import org.joml.Vector2f;

public class PositionComponent implements IComponent
{
	public Vector2f position;

	public PositionComponent()
	{
		super();
		this.position = new Vector2f(0);
	}

	public PositionComponent(Vector2f position)
	{
		super();
		this.position = position;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

}
