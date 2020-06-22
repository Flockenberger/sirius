package at.flockenberger.sirius.engine.gui;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.engine.render.ShapeType;

public class GUIButton extends GUIBase
{

	private Text buttonText;
	private Color textColor;
	private Color buttonColor = new Color(1, .5, 0.2, 1f);

	public GUIButton()
	{
		this("Button");
	}

	public GUIButton(String message)
	{
		this(message, new Vector2f(200, 300));
	}

	public GUIButton(String message, Vector2f position)
	{
		this(message, position, 100, 45);
	}

	public GUIButton(String message, Vector2f position, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.position = position;
		this.buttonText = new Text(message);
		this.textColor = Color.BRIGHT_GREEN;

		if (this.width < getMinWidth())
			this.width = getMinWidth();
		if (this.height < getMinHeight())
			this.height = getMinHeight();
		
		
	}

	public Text getText()
	{ return this.buttonText; }

	@Override
	public void render(Renderer renderer)
	{
		renderer.beginShape(ShapeType.TRIANGLE);
		renderer.color(buttonColor);
		
		renderer.rect(position.x - width / 2f, position.y - height / 2f, width, height);
		buttonText.position(position.x - (buttonText.getTextWidth() / 2f),
				position.y - buttonText.getTextHeight() / 2f);
		buttonText.color(textColor);
		buttonText.draw();
		renderer.endShape();

	}

	@Override
	public void onMouseClicked()
	{

	}

	@Override
	public void onMouseEntered()
	{
		this.textColor = Color.WHITE;
		this.buttonColor = Color.BRIGHT_ORANGE;
	}

	@Override
	public void onMouseExited()
	{
		this.buttonColor = Color.GREEN;
		this.textColor = Color.grayRgb(200);
	}

	@Override
	public void onKey(KeyCode keyCode)
	{
		System.out.println(keyCode);
	}
	
	@Override
	public int getMinWidth()
	{ return Math.max(this.buttonText.getTextWidth(), this.width); }

	@Override
	public int getMinHeight()
	{ return Math.max(this.buttonText.getTextHeight(), this.height); }

	
}
