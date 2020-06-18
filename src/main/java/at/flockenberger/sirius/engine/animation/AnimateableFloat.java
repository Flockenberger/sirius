package at.flockenberger.sirius.engine.animation;

/**
 * <h1>AnimateableFloat</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class AnimateableFloat extends AnimateableValueBase<Float>
{

	public AnimateableFloat()
	{
		this.target = 1f;
		this.actual = 1f;
		this.agility = 1f;
	}

	public AnimateableFloat(Float initialValue, Float target)
	{
		super(initialValue, target);
	}

	public AnimateableFloat(Float initValue, Float target, float speed)
	{
		super(initValue, target, speed);
	}

	@Override
	public void update(float dt)
	{
		float offset = target - actual;
		float change = offset * dt * agility;
		actual += change;
	}

}
