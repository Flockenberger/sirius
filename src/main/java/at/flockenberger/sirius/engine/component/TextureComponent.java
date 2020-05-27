package at.flockenberger.sirius.engine.component;

import at.flockenberger.sirius.engine.graphic.texture.Texture;

public class TextureComponent implements IComponent
{
	private Texture texture;

	public TextureComponent()
	{
		texture = Texture.createEmptyTexture(0, 0);
	}

	public TextureComponent(Texture texture)
	{
		super();
		this.texture = texture;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void update(float dt)
	{

	}

	public Texture getTexture()
	{
		return texture;
	}

}
