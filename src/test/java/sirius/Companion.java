package sirius;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.game.Entity;
import at.flockenberger.sirius.game.Player;

public class Companion extends Entity
{

	private Texture compantionTex;
	private Player player;

	public Companion(Player p)
	{
		Sirius.resMan.loadImageResource("companion", "/companion_0.2.png");
		compantionTex = Texture.createTexture(Sirius.resMan.getImage("companion"));
		this.player = p;
	}

	@Override
	public void render(Renderer render)
	{

		render.begin();
		render.drawTexture(compantionTex, position.x - compantionTex.getWidth() / 2, position.y - compantionTex.getHeight()); // Draw
		render.end();
	}

	@Override
	public void input()
	{
		
	}

	@Override
	public void update()
	{
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
