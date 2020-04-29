package at.flockenberger.sirius.engine.input;

/**
 * <h1>CursorType</h1><br>
 * Defines different types of cursors.
 * 
 * @author Florian Wagner
 *
 */
public enum CursorType {

	/**
	 * The regular arrow cursor.
	 */
	ARROW(0x00036001),

	/**
	 * The text input I-beam cursor shape.
	 */
	IBEAM(0x00036002),

	/**
	 * The crosshair shape.
	 */
	CROSSHAIR(0x00036003),

	/**
	 * The hand shape.
	 */
	HAND(0x00036004),

	/**
	 * The horizontal resize arrow shape.
	 */
	HRESIZE(0x00036005),

	/**
	 * The vertical resize arrow shape.
	 */
	VRESIZE(0x00036006),

	/**
	 * A custom cursor
	 */
	CUSTOM(-1);

	private int id;

	CursorType(int id) {
		this.id = id;
	}

	/**
	 * @return the id for GLFW
	 */
	public int getID() {
		return this.id;
	}
}
