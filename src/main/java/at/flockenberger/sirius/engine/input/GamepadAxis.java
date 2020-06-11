package at.flockenberger.sirius.engine.input;

/**
 * <h1>GamepadAxis</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public enum GamepadAxis
{

	/**
	 * 
	 */
	LEFT_X(0),

	/**
	 * 
	 */
	LEFT_Y(1),

	/**
	 * 
	 */
	RIGHT_X(2),

	/**
	 * 
	 */
	RIGHT_Y(3),

	/**
	 * 
	 */
	LEFT_TRIGGER(4),

	/**
	 * 
	 */
	RIGHT_TRIGGER(5),

	/**
	 * 
	 */
	LAST(RIGHT_TRIGGER);

	private int _id;

	GamepadAxis(int id)
	{
		this._id = id;
	}

	GamepadAxis(GamepadAxis axis)
	{
		this._id = axis._id;
	}

	/**
	 * @return the id of this axis
	 */
	public int getID()
	{ return this._id; }
}
