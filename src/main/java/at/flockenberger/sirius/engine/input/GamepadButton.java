package at.flockenberger.sirius.engine.input;

/**
 * <h1>GamepadButton</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public enum GamepadButton
{

	/**
	 * 
	 */
	A(0),

	/**
	 * 
	 */
	B(1),

	/**
	 * 
	 */
	X(2),

	/**
	 * 
	 */
	Y(3),

	/**
	 * 
	 */
	LEFT_BUMPER(4),

	/**
	 * 
	 */
	RIGHT_BUMPER(5),

	/**
	 * 
	 */
	BACK(6),

	/**
	 * 
	 */
	START(7),

	/**
	 * 
	 */
	GUIDE(8),

	/**
	 * 
	 */
	LEFT_THUMB(9),

	/**
	 * 
	 */
	RIGHT_THUMB(10),

	/**
	 * 
	 */
	DPAD_UP(11),

	/**
	 * 
	 */
	DPAD_RIGHT(12),

	/**
	 * 
	 */
	DPAD_DOWN(13),

	/**
	 * 
	 */
	DPAD_LEFT(14),

	/**
	 * 
	 */
	LAST(DPAD_LEFT),

	/**
	 * 
	 */
	CROSS(A),

	/**
	 * 
	 */
	CIRCLE(B),

	/**
	 * 
	 */
	SQUARE(X),

	/**
	 * 
	 */
	TRIANGLE(Y);

	private int _id;

	GamepadButton(int id)
	{
		this._id = id;
	}

	GamepadButton(GamepadButton button)
	{
		this._id = button._id;
	}

	/**
	 * @return the id of this button
	 */
	public int getID()
	{ return this._id; }
}
