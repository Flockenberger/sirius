package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.entity.AnimateableEntity;

public class OtherEntity extends AnimateableEntity
{

	public OtherEntity()
	{
		setTexture(Texture.createTexture(Sirius.resMan.getImage("companion").trimImage()), true);
		position.x = (float) (Math.random() * 60) - 10;
		position.y = (float) (Math.random() * 70) + 10;
	}

	@Override
	public void render(Renderer render)
	{
		render.drawEntity(this);
	}

	@Override
	public void input()
	{

	}

	@Override
	public void update()
	{
		super.update();

		audioSource.setPosition(this.position);
	}

}
