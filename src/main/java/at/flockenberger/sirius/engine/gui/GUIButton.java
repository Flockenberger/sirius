package at.flockenberger.sirius.engine.gui;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.engine.render.Renderer.ShapeType;

public class GUIButton extends GUITextured
{

	private Text buttonText;
	private Color textColor;
	private Color buttonColor = Color.BRIGHT_ORANGE;

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
		if (renderer.isDrawing())
			renderer.end();

		renderer.updateMatrix(Sirius.game.getGUICamera());

		renderer.beginShape(ShapeType.TRIANGLE);
		renderer.color(buttonColor);
		renderer.rect(position.x - width / 2f, position.y - height / 2f, width, height);

		buttonText.position(position.x - (buttonText.getTextWidth() / 2f),
				position.y - buttonText.getTextHeight() / 2f);
		buttonText.color(textColor);
		buttonText.draw();
		renderer.end();
	}

	@Override
	public void onMouseClicked()
	{

	}

	@Override
	public void onMouseEntered()
	{
		this.textColor = Color.WHITE;
	}

	@Override
	public void onMouseExited()
	{
		this.textColor = Color.grayRgb(200);
	}

	@Override
	public int getMinWidth()
	{ return Math.max(this.buttonText.getTextWidth(), this.width); }

	@Override
	public int getMinHeight()
	{ return Math.max(this.buttonText.getTextHeight(), this.height); }
}
