package at.flockenberger.sirius.engine.graphic.text;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Text</h1><br>
 * A renderable text.<br>
 * The text's (0,0) point is on the top left corner.
 * 
 * @author Florian Wagner
 *
 */
public class Text implements IFreeable
{

	/**
	 * the font of this text
	 */
	private SiriusFont font;

	/**
	 * the position of this text
	 */
	private Vector2f position;

	/**
	 * the scale of this text
	 */
	private Vector2f scale;

	/**
	 * the message that this text print to the screen
	 */
	private String message;

	/**
	 * the text's rotation
	 */
	private float rotation;

	/**
	 * the color of the text
	 */
	private Color color;

	/**
	 * Creates and initializes a new {@link Text} object with no text.<br>
	 * The default {@link SiriusFont} which can be found by
	 * {@link Sirius#fontDefault}.
	 */
	public Text()
	{
		this("");
	}

	/**
	 * Creates and initializes a new {@link Text} object with the given text<br>
	 * The default {@link SiriusFont} which can be found by
	 * {@link Sirius#fontDefault}.
	 *
	 * @param message the message that this text should hold
	 */
	public Text(String message)
	{
		this(message, Sirius.fontDefault);
	}

	/**
	 * Creates and initializes a new {@link Text} object with the given text and
	 * font.
	 * 
	 * @param message the message that this text should hold
	 * @param font    the font of this text
	 */
	public Text(String message, SiriusFont font)
	{
		this(message, font, new Vector2f(0, 0));
	}

	/**
	 * Creates and initializes a new {@link Text} object with the given text, font
	 * and position.
	 * 
	 * @param message  the message that this text should hold
	 * @param font     the font of this text
	 * @param position the position of this text
	 */
	public Text(String message, SiriusFont font, Vector2f position)
	{
		this(message, font, position, new Vector2f(1f));
	}

	/**
	 * Creates and initializes a new {@link Text} object with the given text, font,
	 * position and scale.
	 * 
	 * @param message  the message that this text should hold
	 * @param font     the font of this text
	 * @param position the position of this text
	 * @param scale    the scale of this text
	 */
	public Text(String message, SiriusFont font, Vector2f position, Vector2f scale)
	{
		this.message = message;
		this.font = font;
		this.position = position;
		this.scale = scale;
		this.color = Color.WHITE;
	}

	/**
	 * Draws this text to the screen.
	 */
	public void draw()
	{
		if (Sirius.renderer.isDrawing())
			Sirius.renderer.end();

		if (this.font == null)
			this.font = Sirius.fontDefault;

		Sirius.renderer.begin();
		this.font.drawText(Sirius.renderer, message, position.x, position.y, color, scale.x, scale.y, rotation);
		Sirius.renderer.end();
	}

	/**
	 * Sets the rotation of this text
	 * 
	 * @param rot the text's rotation
	 */
	public void rotate(float rot)
	{
		this.rotation = rot;
	}

	/**
	 * Sets this text's color
	 * 
	 * @param c the color of this text to draw
	 */
	public void color(Color c)
	{
		SUtils.checkNull(c, "Color");
		this.color = (c);
	}

	/**
	 * Sets the position of this text
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	public void position(float x, float y)
	{
		this.position.set(x, y);
	}

	/**
	 * Sets the position of this text
	 * 
	 * @param vector the position vector
	 */
	public void position(Vector2f vector)
	{
		SUtils.checkNull(vector, "Vector2f");
		this.position.set(vector);
	}

	/**
	 * @return the position of this text
	 */
	public Vector2f getPosition()
	{
		return this.position;
	}

	/**
	 * @return the text's width
	 */
	public int getTextWidth()
	{
		return font.getWidth(message);
	}

	/**
	 * @return the text's height
	 */
	public int getTextHeight()
	{
		return font.getHeight(message);
	}

	/**
	 * Sets the scale of this text
	 * 
	 * @param x the x scale
	 * @param y the y scale
	 */
	public void scale(float x, float y)
	{
		this.scale.set(x, y);

	}

	/**
	 * Sets the String this text should display
	 * 
	 * @param text the String to display
	 */
	public void setText(String text)
	{
		SUtils.checkNull(text, "String");
		this.message = text;
	}

	/**
	 * Sets this text to the {@link #toString()} method of the given object
	 * 
	 * @param obj the object to set as text to draw
	 */
	public void setText(Object obj)
	{
		SUtils.checkNull(obj, "Object");
		this.message = obj.toString();
	}

	/**
	 * Sets the font of this text
	 * 
	 * @param font the {@link SiriusFont} to set
	 */
	public void setFont(SiriusFont font)
	{
		SUtils.checkNull(font, "SiriusFont");
		this.font = font;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void free()
	{
		font.free();
	}
}
