package at.flockenberger.sirius.engine.gui;

import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.utillity.SUtils;

public abstract class GUITextured extends GUIBase
{
	protected Texture texture;

	protected GUITextured()
	{
		
	}

	public GUITextured(Texture texture)
	{
		SUtils.checkNull(texture, Texture.class);
		this.texture = texture;
	}

	public void setTexture(Texture texture)
	{
		SUtils.checkNull(texture, Texture.class);
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	public Texture getTexture()
	{ return this.texture; }
}
