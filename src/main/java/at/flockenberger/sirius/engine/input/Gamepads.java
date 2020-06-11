package at.flockenberger.sirius.engine.input;

/**
 * <h1>Gamepdas</h1><br>
 * The available gamepads.
 * 
 * @author Florian Wagner
 *
 */
public enum Gamepads
{

	/**
	 * 
	 */
	GAMEPAD_1(0),

	/**
	 * 
	 */
	GAMEPAD_2(1),

	/**
	 * 
	 */
	GAMEPAD_3(2),

	/**
	 * 
	 */
	GAMEPAD_4(3),

	/**
	 * 
	 */
	GAMEPAD_5(4),

	/**
	 * 
	 */
	GAMEPAD_6(5),

	/**
	 * 
	 */
	GAMEPAD_7(6),

	/**
	 * 
	 */
	GAMEPAD_8(7),

	/**
	 * 
	 */
	GAMEPAD_9(8),

	/**
	 * 
	 */
	GAMEPAD_10(9),

	/**
	 * 
	 */
	GAMEPAD_11(10),

	/**
	 * 
	 */
	GAMEPAD_12(11),

	/**
	 * 
	 */
	GAMEPAD_13(12),

	/**
	 * 
	 */
	GAMEPAD_14(13),

	/**
	 * 
	 */
	GAMEPAD_15(14),

	/**
	 * 
	 */
	GAMEPAD_16(15),

	/**
	 * 
	 */
	GAMEPAD_LAST(GAMEPAD_16);

	private int _id;

	Gamepads(int id)
	{
		this._id = id;
	}

	Gamepads(Gamepads gp)
	{
		this._id = gp._id;
	}

	/**
	 * @return the id of this gamepad
	 */
	public int getID()
	{ return this._id; }

}
