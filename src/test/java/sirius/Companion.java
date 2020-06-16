package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.entity.AnimateableEntity;
import at.flockenberger.sirius.utillity.SUtils;

public class Companion extends AnimateableEntity
{
	private Player player;

	public Companion(Player p)
	{
		setTexture(Texture.createTexture(Sirius.resMan.getImage("companion").trimImage()), true);
		this.player = p;
	}

	@Override
	public void render(Renderer render)
	{
		render.drawEntity(this);

		drawBoundingBox(render);
	}

	@Override
	public void input()
	{

	}

	@Override
	public void update()
	{
		super.update();
	
		position.x = SUtils.lerp(position.x,
				(float) (player.getPosition().x - 15 * Math.sin((float) Sirius.timer.getTime() * 2 * Math.PI)), 0.2f);
		position.y = SUtils.lerp(position.y,
				(float) (player.getPosition().y - 15 * Math.cos((float) Sirius.timer.getTime() * 2 * Math.PI)), 0.2f);

	}

}
