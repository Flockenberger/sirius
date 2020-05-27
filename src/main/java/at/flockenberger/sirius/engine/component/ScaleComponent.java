package at.flockenberger.sirius.engine.component;

import org.joml.Vector2f;

public class ScaleComponent implements IComponent
{
	private Vector2f scale;

	public ScaleComponent()
	{
		super();
		scale = new Vector2f(1);
	}

	public ScaleComponent(Vector2f scale)
	{
		super();
		this.scale = scale;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Vector2f getScale()
	{
		return scale;
	}

}
