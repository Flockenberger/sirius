package at.flockenberger.sirius.engine.particle;

import java.util.List;

import org.joml.Vector3f;

public abstract class ParticleModifierBase
{
	private double strength;
	private Vector3f position;

	public ParticleModifierBase(double strength, Vector3f position)
	{
		super();
		this.strength = strength;
		this.position = position;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public double getStrength()
	{
		return strength;
	}

	public void setStrength(double strength)
	{
		this.strength = strength;
	}

	public abstract void modifyParticle(Particle p, ParticleSystem particleSystem);

	public abstract void modifyParticles(List<Particle> particles, ParticleSystem particleSystem);

}
