package at.flockenberger.sirius.engine.graphic.text;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Color;

/**
 * <h1>Text</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class Text
{

	private SiriusFont font;
	private String message;
	private Vector2f position;
	private Vector2f scale;
	private Color color;

	public Text()
	{
		this("");
	}

	public Text(String message)
	{
		this(message, Sirius.fontDefault);
	}

	public Text(String message, SiriusFont font)
	{
		this(message, font, new Vector2f(0, 0));
	}

	public Text(String message, SiriusFont font, Vector2f position)
	{
		this(message, font, position, new Vector2f(1f));
	}

	public Text(String message, SiriusFont font, Vector2f position, Vector2f scale)
	{
		this.message = message;
		this.font = font;
		this.position = position;
		this.scale = scale;
		this.color = Color.WHITE;
	}

	public void draw()
	{
		if (Sirius.renderer.isDrawing())
			Sirius.renderer.end();

		if (this.font == null)
			this.font = Sirius.fontDefault;

		Sirius.renderer.begin();
		this.font.drawText(Sirius.renderer, message, position.x, position.y, color);
		Sirius.renderer.end();
	}

	public int getTextWidth()
	{
		return font.getWidth(message);
	}

	public int getTextHeight()
	{
		return font.getHeight(message);
	}

	public void setColor(Color c)
	{
		this.color = c;
	}

	public void setPosition(float x, float y)
	{
		this.position.set(x, y);
	}

	public void setPosition(Vector2f vector)
	{
		this.position.set(vector);
	}

	public void setScale(float x, float y)
	{
		this.scale.set(x, y);

	}

	public void setText(String text)
	{
		this.message = text;
	}

	public void setFont(SiriusFont font)
	{
		this.font = font;
	}

	public Vector2f getPosition()
	{
		return this.position;
	}
}
