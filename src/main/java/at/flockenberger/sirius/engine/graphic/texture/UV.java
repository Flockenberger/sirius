package at.flockenberger.sirius.engine.graphic.texture;

/**
 * <h1>UV</h1><br>
 * Class for defining UV coordinates.
 * 
 * @author Florian Wagner
 *
 */
public class UV
{
	public float u1;
	public float v1;
	public float u2;
	public float v2;

	/**
	 * Default Constructor<br>
	 * Creates a new default UV with coordinates (0,0,1,1) for (u1,v1,u2,v2)
	 */
	public UV()
	{
		this(0f, 0f, 1f, 1f);
	}

	/**
	 * Constructor<br>
	 * Creates a new UV.
	 * 
	 * @param u1 the first u coordinate
	 * @param v1 the first v coordinate
	 * @param u2 the second u coordinate
	 * @param v2 the second v coordinate
	 */
	public UV(float u1, float v1, float u2, float v2)
	{
		super();
		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;
	}

	public void flip()
	{
		float tmp = u1;
		u1 = u2;
		u2 = tmp;

		tmp = v1;
		v1 = v2;
		v2 = tmp;
	}

	/**
	 * @return the first u coordinate
	 */
	public float getU1()
	{
		return u1;
	}

	/**
	 * @return the first v coordinate
	 */
	public float getV1()
	{
		return v1;
	}

	/**
	 * @return the second u coordinate
	 */
	public float getU2()
	{
		return u2;
	}

	/**
	 * @return the second v coordinate
	 */
	public float getV2()
	{
		return v2;
	}

	public void setU1(float u1)
	{
		this.u1 = u1;
	}

	public void setV1(float v1)
	{
		this.v1 = v1;
	}

	public void setU2(float u2)
	{
		this.u2 = u2;
	}

	public void setV2(float v2)
	{
		this.v2 = v2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "UV [u1=" + u1 + ", v1=" + v1 + ", u2=" + u2 + ", v2=" + v2 + "]";
	}

}
