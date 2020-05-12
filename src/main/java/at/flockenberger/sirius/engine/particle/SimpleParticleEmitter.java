package at.flockenberger.sirius.engine.particle;

import org.joml.Vector3f;

import at.flockenberger.sirius.engine.graphic.Color;

public class SimpleParticleEmitter extends ParticleEmitterBase
{

	public SimpleParticleEmitter(long amount, Vector3f position)
	{
		super(amount, position);
	}

	@Override
	public void emit(ParticleSystem system)
	{
		for (int i = 0; i < getAmount(); i++)
		{
			system.createParticle(new Particle(LifeTime.random(), Particle.randomVelocity(), getPosition(), Color.GREEN,
					Color.ORANGE));
		}
	}

}
