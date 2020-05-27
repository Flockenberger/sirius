package at.flockenberger.sirius.engine.particle;

import org.joml.Vector3f;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Particle</h1><br>
 * 
 * 
 * @author Florian Wagner
 *
 */
public class Particle implements IParticle
{

	private LifeTime lifeTime;
	private long id;
	private double agingSpeed;
	private Vector3f velocity;
	private Vector3f acceleration;
	private Vector3f position;

	private float size;
	private float iniSize;

	private Vector3f rotation;

	private boolean doesAge;
	private Color startColor;
	private Color endColor;
	private Color color;
	private Texture texture;

	public Particle(LifeTime lifeTime, long id, double agingSpeed, Vector3f velocity, Vector3f acceleration,
			Vector3f position, boolean doesAge, Color startColor, Color endColor)
	{
		super();
		this.lifeTime = lifeTime;
		this.id = id;
		this.agingSpeed = agingSpeed;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.position = position;
		this.doesAge = doesAge;
		this.startColor = startColor;
		this.endColor = endColor;
		this.iniSize = size;
	}

	public Particle(LifeTime lf, Vector3f velocity, Vector3f pos, Color start, Color end)
	{
		this.lifeTime = lf;
		this.id = 0;
		this.agingSpeed = 0.0001d;
		this.velocity = velocity;
		this.acceleration = new Vector3f(0);
		this.position = pos;
		this.doesAge = true;
		this.startColor = start;
		this.endColor = end;
		this.size = (float) Math.random() * 8;
		this.rotation = new Vector3f(1);
		this.iniSize = size;
	}

	public float getSize()
	{
		return size;
	}

	public void setSize(float size)
	{
		this.size = size;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}

	public Color getCurrentColor()
	{
		return color;
	}

	public Color getStartColor()
	{
		return startColor;
	}

	public Color getEndColor()
	{
		return endColor;
	}

	public double getAgingSpeed()
	{
		return agingSpeed;
	}

	public void setAgingSpeed(double agingSpeed)
	{
		this.agingSpeed = agingSpeed;
	}

	public boolean isDoesAge()
	{
		return doesAge;
	}

	public void setDoesAge(boolean doesAge)
	{
		this.doesAge = doesAge;
	}

	public LifeTime getLifeTime()
	{
		return lifeTime;
	}

	public void setLifeTime(LifeTime lifeTime)
	{
		this.lifeTime = lifeTime;
	}

	public long getID()
	{
		return id;
	}

	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}

	public Vector3f getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(Vector3f acceleration)
	{
		this.acceleration = acceleration;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void isOutOfBounds()
	{
		if (getPosition().x <= 0)
		{
			getPosition().x = 0;
			addBounds();
		} else if (getPosition().x >= Window.getActiveWidth())
		{
			getPosition().x = Window.getActiveWidth();
			addBounds();
		} else if (getPosition().y >= Window.getActiveHeight())
		{
			getPosition().y = Window.getActiveHeight();
			addBounds();
		} else if (getPosition().y <= 0)
		{
			getPosition().y = 0;
			addBounds();
		}

	}

	public void addBounds()
	{
		getVelocity().x = (-getVelocity().x * 0.9f);
		getVelocity().y = (-getVelocity().y * 0.9f);
	}

	public void addAcceleration(Vector3f v)
	{
		acceleration.add(v);
	}

	public static Vector3f randomVelocity()
	{
		float f1 = (float) Math.random();
		f1 = (float) SUtils.map(f1, 0, 1, -10, 10);
		float f2 = (float) Math.random();
		f2 = (float) SUtils.map(f2, 0, 1, -10, 10);
		float f3 = (float) Math.random();
		f3 = (float) SUtils.map(f3, 0, 1, -10, 10);

		return new Vector3f(f1, f2, f3);
	}

	@Override
	public void update()
	{
		color = this.startColor.interpolate(this.endColor, lifeTime.getAgePercent());

		// isOutOfBounds();
		getPosition().add(getVelocity());

		setSize((float) (iniSize * (1 - lifeTime.getAgePercent())));
		// if (getSize() < 0.006f)
		// lifeTime.setDead();

		getLifeTime().incrementAge(getAgingSpeed());

		getRotation().mul(getVelocity());

		getVelocity().add(getAcceleration());

	}

}
