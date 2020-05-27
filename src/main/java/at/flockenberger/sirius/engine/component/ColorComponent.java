package at.flockenberger.sirius.engine.component;

import at.flockenberger.sirius.engine.graphic.Color;

public class ColorComponent implements IComponent
{
	private Color color;

	public ColorComponent()
	{
		color = new Color(0);
	}

	public ColorComponent(Color c)
	{
		this.color = c;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Color getColor()
	{
		return color;
	}

}
