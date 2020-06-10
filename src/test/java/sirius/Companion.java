package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.entity.AnimateableEntity;

public class Companion extends AnimateableEntity
{

	private Texture compantionTex;
	private Player player;

	public Companion(Player p)
	{

		compantionTex = Texture.createTexture(Sirius.resMan.getImage("companion").trimImage());
		this.width = compantionTex.getWidth();
		this.height = compantionTex.getHeight();
		this.player = p;
	}

	@Override
	public void render(Renderer render)
	{
		render.begin();
		render.drawTexture(compantionTex, position.x, position.y); // Draw
		render.end();

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
		position.set(player.getPosition());
		position.x = (float) ((position.x - 24) + Math.sin((float) Sirius.timer.getTime() * 2 * Math.PI) * 10);
		position.y = (float) ((position.y - 24) + Math.cos((float) Sirius.timer.getTime() * 2 * Math.PI) * 10);

	}

	@Override
	public void free()
	{
		compantionTex.free();
	}

	@Override
	public void update(float delta)
	{

	}
}
