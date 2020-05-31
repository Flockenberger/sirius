package at.flockenberger.sirius.engine.animation;

import org.joml.Vector2f;

public class AnimateableVector2f extends AnimateableValueBase<Vector2f>
{

	public AnimateableVector2f()
	{
		super();
	}

	public AnimateableVector2f(Vector2f init, Vector2f target)
	{
		super(init, target);
	}

	public AnimateableVector2f(Vector2f initial, Vector2f target, float agility)
	{
		super(initial, target, agility);
	}

	@Override
	public void update(float dt)
	{
		Vector2f offset = new Vector2f();
		target.sub(actual, offset);

		Vector2f change = new Vector2f();

		offset.mul(dt, change);
		change.mul(agility);
		actual = actual.add(change);

	}

}
