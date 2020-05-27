package at.flockenberger.sirius.game;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;

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
		render.drawTexture(compantionTex, position); // Draw
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
		position.x = (float) ((position.x - 24) * Math.sin(Sirius.timer.getTime()) * (Math.random() * 1));
		position.y = (float) ((position.y - 24) * Math.cos(Sirius.timer.getTime()) * (Math.random() * 1));

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
