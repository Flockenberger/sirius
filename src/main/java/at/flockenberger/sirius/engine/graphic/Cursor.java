package at.flockenberger.sirius.engine.graphic;

import java.nio.file.Path;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.input.CursorType;

/**
 * <h1>Cursor</h1><br>
 * A mouse cursor.<br>
 * 
 * @author Florian Wagner
 *
 */
public class Cursor extends Image implements IFreeable
{

	private static final long serialVersionUID = 121973262138123279L;
	private long id;
	private CursorType type;

	// default cursor
	private static Cursor cDefault;

	/**
	 * @return the default cursor
	 */
	public static Cursor getDefault()
	{
		if (cDefault == null)
			cDefault = new Cursor(CursorType.ARROW);
		return cDefault;
	}

	/**
	 * Instantiates a new Cursor.<br>
	 * If the {@link CursorType} given is {@link CursorType#CUSTOM} then
	 * {@link #setCursor(Path)} has to be called.
	 * 
	 * @param type the type of cursor to create
	 */
	public Cursor(CursorType type)
	{
		super();
		this.type = type;
		if (!this.type.equals(CursorType.CUSTOM))
			this.id = GLFW.glfwCreateStandardCursor(type.getID());

	}

	/**
	 * Instantiates a new Cursor.<br>
	 * Sets the {@link CursorType} to {@link CursorType#CUSTOM} and loads the given
	 * cursor image.<br>
	 * 
	 * @param image the image to set this cursor
	 * @param xhot  the desired x-coordinate, in pixels, of the cursor hotspot
	 * @param yhot  the desired y-coordinate, in pixels, of the cursor hotspot
	 */
	public Cursor(Image image, int xhot, int yhot)
	{
		super(image);
		this.type = CursorType.CUSTOM;
		setCursor(xhot, yhot);
	}

	/**
	 * Instantiates a new Cursor.<br>
	 * Sets the {@link CursorType} to {@link CursorType#CUSTOM} and loads the given
	 * cursor image.<br>
	 * 
	 * @param image the image to set this cursor
	 */
	public Cursor(Image image)
	{
		this(image, 0, 0);
	}

	/**
	 * Instantiates a new Cursor.<br>
	 * Sets the {@link CursorType} to {@link CursorType#CUSTOM} and loads the given
	 * cursor image.<br>
	 * 
	 * @param img the path to the image to load for this cursor
	 */
	public Cursor(Path img)
	{
		super(img);
		setCursor(0, 0);
	}

	/**
	 * @return the type of this cursor
	 */
	public CursorType getType()
	{
		return this.type;
	}

	/**
	 * @return the id of this cursor
	 */
	public long getID()
	{
		return this.id;
	}

	/**
	 * Frees this cursor.
	 */
	@Override
	public void free()
	{
		GLFW.glfwDestroyCursor(getID());
	}

	protected void setCursor(int xhot, int yhot)
	{
		GLFWImage img = GLFWImage.malloc();
		img.width(getWidth()).height(getHeight()).pixels(getPixelData());
		this.id = GLFW.glfwCreateCursor(img, xhot, yhot);
	}

}
