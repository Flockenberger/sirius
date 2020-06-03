package at.flockenberger.sirius.engine.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.magicwerk.brownies.collections.BigList;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.Image;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;

/**
 * <h1>ParticleSystem</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class ParticleSystem implements IFreeable
{
	// we use a big list here for performance.
	private List<Particle> particles;
	private List<ParticleModifierBase> modifiers;
	private List<ParticleEmitterBase> emitters;
	private Image img;
	private Texture tex;

	private long maxParticles;

	public ParticleSystem()
	{
		particles = new BigList<>();
		modifiers = new ArrayList<>();
		emitters = new ArrayList<>();
		maxParticles = 10000;
		img = new Image(16, 16);
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				img.setPixel(i, j, Color.ORANGE);

		tex = Texture.createTexture(img);
	}

	public long getMaxParticles()
	{
		return maxParticles;
	}

	public List<ParticleModifierBase> getModifiers()
	{
		return modifiers;
	}

	public void setModifiers(List<ParticleModifierBase> modifiers)
	{
		this.modifiers = modifiers;
	}

	public List<ParticleEmitterBase> getEmitters()
	{
		return emitters;
	}

	public void setEmitters(List<ParticleEmitterBase> emitters)
	{
		this.emitters = emitters;
	}

	public void setMaxParticles(long maxParticles)
	{
		this.maxParticles = maxParticles;
	}

	public long getParticleAmount()
	{
		return particles.size();
	}

	public void createParticle(Particle p)
	{
		if (canCreateParticle())
			particles.add(p);
	}

	public void addModifier(ParticleModifierBase mod)
	{
		modifiers.add(mod);
	}

	public void removeModifier(ParticleModifierBase mod)
	{
		modifiers.remove(mod);
	}

	public void addEmitter(ParticleEmitterBase emi)
	{
		emitters.add(emi);
	}

	public void removeEmitter(ParticleEmitterBase emi)
	{
		emitters.remove(emi);
	}

	public boolean canEmit(ParticleEmitterBase emi)
	{
		return getParticleAmount() + emi.getAmount() <= getMaxParticles();
	}

	public boolean canCreateParticle()
	{
		return (getParticleAmount() + 1 <= getMaxParticles());
	}

	public List<Particle> getParticles()
	{
		return particles;
	}

	private void modifyParticles()
	{
		for (Particle p : particles)
			for (ParticleModifierBase modifier : modifiers)
			{
				modifier.modifyParticle(p, this);
			}

		modifyAllParticles();
	}

	private void modifyAllParticles()
	{
		for (ParticleModifierBase modifier : modifiers)
		{
			modifier.modifyParticles(particles, this);
		}
	}

	public void update()
	{
		for (ParticleEmitterBase e : emitters)
			if (canEmit(e))
			{
				e.emit(this);
			}

		modifyParticles();

		/*
		 * I am not sure how smart it is to always delete and reallocate new particles
		 * into the list. Might want to change that later on
		 */
		for (Iterator<Particle> iterator = particles.iterator(); iterator.hasNext();)
		{
			Particle par = (Particle) iterator.next();
			if (par.getLifeTime().isDead())
			{
				iterator.remove();

			} else
			{
				par.update();
			}
		}
	}

	public void render(Renderer render)
	{
		tex.bind();
		for (Particle p : particles)
		{

			render.drawColor(p.getPosition().x, p.getPosition().y, p.getSize(), p.getSize(), Color.BRIGHT_ORANGE);
		}

	}

	@Override
	public void free()
	{
		particles.clear();
		emitters.clear();
		modifiers.clear();
	}

}
