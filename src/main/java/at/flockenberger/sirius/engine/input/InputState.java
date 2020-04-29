package at.flockenberger.sirius.engine.input;

/**
 * <h1>InputState</h1><br>
 * The InputState is used to determine which state a key or mouse button
 * currently has. <br>
 * Every Input state is assigned to an ID. <br>
 * {@link #RELEASED} = 0 <br>
 * {@link #PRESSED} = 1 <br>
 * {@link #REPEAT} = 2<br>
 * 
 */
public enum InputState {

	/**
	 * This state indicates that a key or button has been released.
	 */
	RELEASED(0),
	/**
	 * This state indicates that a key or button is being pressed.
	 */
	PRESSED(1),
	/**
	 * This state indicates that a key or button is being pressed for a longer
	 * duration of time.
	 */
	REPEAT(2);

	private int id;

	InputState(int id) {
		this.id = id;
	}

	/**
	 * Returns an {@link InputState} based on the given integer. <br>
	 * Every Input state is assigned to an ID. <br>
	 * {@link #RELEASED} = 0 <br>
	 * {@link #PRESSED} = 1 <br>
	 * {@link #REPEAT} = 2<br>
	 * 
	 * @param i the id of the input state to get
	 * @return the given {@link InputState} or {@link #REPEAT} as default if the ID
	 *         was not found
	 */
	public static InputState getFromInt(int i) {
		switch (i) {
		case 0:
			return RELEASED;
		case 1:
			return PRESSED;
		default:
			return REPEAT;
		}
	}

	/**
	 * @return the id of the {@link InputState}<br>
	 *         Every Input state is assigned to an ID. <br>
	 *         {@link #RELEASED} = 0 <br>
	 *         {@link #PRESSED} = 1 <br>
	 *         {@link #REPEAT} = 2<br>
	 */
	public int getID() {
		return this.id;
	}

}
