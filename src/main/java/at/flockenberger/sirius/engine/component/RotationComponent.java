package at.flockenberger.sirius.engine.component;

import org.joml.Vector2f;

public class RotationComponent
implements IComponent
{
	private Vector2f rotation;


	public RotationComponent()
	{
		super();
		rotation = new Vector2f(0);
		
	}

	public RotationComponent(Vector2f rotation)
	{
		super();
		this.rotation = rotation;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Vector2f getRotation()
	{
		return rotation;
	}

}
