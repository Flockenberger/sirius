package at.flockenberger.sirius.engine.particle;

/**
 * <h1>LifeTime</h1><br>
 * Defines the lifetime of a particle
 * 
 * @author Florian Wagner
 *
 */
public class LifeTime
{

	private double lifeTime = 0;
	private double age = 0;
	private boolean dead = false;

	public LifeTime(double lifeTIme)
	{
		this.lifeTime = lifeTIme;
	}

	/**
	 * @return a {@link LifeTime} with random life time between 0 -10
	 */
	public static LifeTime random()
	{
		return new LifeTime(Math.random() / (float) 10f);
	}

	/**
	 * @return the current age
	 */
	public double getAge()
	{
		return age;
	}

	/**
	 * @return the age in percent
	 */
	public double getAgePercent()
	{
		return age / (double) lifeTime;
	}

	/**
	 * Sets the age
	 * 
	 * @param life the age to set
	 */
	public void setAge(double life)
	{
		this.age = life;
	}

	/**
	 * Increments the age by the given amount <code> inc </code>
	 * 
	 * @param inc the amount to increment the age
	 */
	public void incrementAge(double inc)
	{
		this.age += inc;
	}

	/**
	 * @return the lifetime
	 */
	public double getLifeTime()
	{
		return lifeTime;
	}

	/**
	 * @return true if dead otherwise false
	 */
	public boolean isDead()
	{
		if (age >= lifeTime)
			dead = true;
		return dead;
	}

	public void setDead()
	{
		dead = true;
	}

	/**
	 * @return true if alive otherwise false
	 */
	public boolean isAlive()
	{
		if (age < lifeTime)
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		return "LifeTime: lifeTime:" + lifeTime + ", age:" + age + "stillAlive:" + isAlive();
	}

}