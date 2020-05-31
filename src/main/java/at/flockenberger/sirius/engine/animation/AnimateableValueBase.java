package at.flockenberger.sirius.engine.animation;

public abstract class AnimateableValueBase<T> implements AnimateableValue<T>
{
	protected float agility;
	protected T target;
	protected T actual;

	public AnimateableValueBase()
	{
		this.agility = 1f;
	}

	public AnimateableValueBase(T init, T target)
	{
		this(init, target, 1f);
	}

	public AnimateableValueBase(T init, T target, float speed)
	{
		this.actual = init;
		this.target = target;
		this.agility = speed;
	}

	@Override
	public T get()
	{
		return actual;
	}

	@Override
	public void target(T target)
	{
		this.target = target;
	}

	@Override
	public void set(T value)
	{
		this.target = this.actual = value;
	}
}
