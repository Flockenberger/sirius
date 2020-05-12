package at.flockenberger.sirius.engine.particle;

import org.joml.Vector3f;

public abstract class ParticleEmitterBase
{

	private long amount;
	private float x;
	private float y;
	private float z;

	public ParticleEmitterBase(long amount, Vector3f position)
	{
		super();
		this.amount = amount;
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
	}

	public Vector3f getPosition()
	{
		return new Vector3f(x, y, z);
	}

	public void setPosition(Vector3f position)
	{
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
	}

	public long getAmount()
	{
		return amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	public abstract void emit(ParticleSystem system);

}
