package at.flockenberger.sirius.engine.input;

/**
 * <h1>MouseButton</h1><br>
 * Set of mouse buttons.
 * 
 * @author Florian Wagner
 *
 */
public enum MouseButton {

	/**
	 * 
	 */
	BUTTON_1(0),

	/**
	 * 
	 */
	BUTTON_2(1),

	/**
	 * 
	 */
	BUTTON_3(2),

	/**
	 * 
	 */
	BUTTON_4(3),

	/**
	 * 
	 */
	BUTTON_5(4),

	/**
	 * 
	 */
	BUTTON_6(5),

	/**
	 * 
	 */
	BUTTON_7(6),

	/**
	 * 
	 */
	BUTTON_8(7),

	/**
	 * 
	 */
	LAST(BUTTON_8.getID()),

	/**
	 * 
	 */
	LEFT(BUTTON_1.getID()),

	/**
	 * 
	 */
	RIGHT(BUTTON_2.getID()),

	/**
	 * 
	 */
	MIDDLE(BUTTON_3.getID());

	private int id;

	MouseButton(int id) {
		this.id = id;
	}

	/**
	 * @return the GLFW id of this button
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Returns a {@link MouseButton} corresponding to the given int value or
	 * {@link MouseButton#LAST}.
	 * 
	 * @param b the button as int
	 * @return the {@link MouseButton} corresponding to the given value
	 */
	public static MouseButton getFromInt(int b) {
		switch (b) {
		case 0:
			return LEFT;
		case 1:
			return RIGHT;
		case 2:
			return MIDDLE;
		default:
			return LAST;
		}
	}

}
