package at.flockenberger.sirius.engine.gui;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.engine.render.Renderer.ShapeType;

public class TestComponent
{

	private float length;
	private Vector2f position;

	public TestComponent()
	{
		this.length = 10;
		position = new Vector2f(10, 10);
	}

	public void update()
	{

	}

	public void input()
	{
		Mouse m = Mouse.get();
		float x = (float) m.getX();
		float y = (float) m.getY();
		if (x >= position.x && x <= position.x + length && y >= position.y && y <= position.y + length)
		{
			System.out.println("mouse in");
		}
	}

	public void render(Renderer renderer)
	{
		renderer.updateMatrix(Sirius.game.getGUICamera());
		renderer.beginShape(ShapeType.TRIANGLE);
		renderer.color(Color.GREEN);
		renderer.rect(position.x - (length / 2f), position.y - (length / 2f), length, length);
		renderer.endShape();

	}
}
