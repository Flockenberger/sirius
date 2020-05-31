package at.flockenberger.sirius.game;

import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;

public class Player extends Entity
{
	private Texture idleTexture;
	private Map<String, Animation<TextureRegion>> animationCache;

	public Player()
	{
		animationCache = new HashMap<>();
		Sirius.resMan.loadImageResource("mc", "/MC_0.2.png");
		idleTexture = Texture.createTexture(Sirius.resMan.getImage("mc"));
		Animation<TextureRegion> idleAni = new Animation<TextureRegion>(1, new TextureRegion(idleTexture));
		animationCache.put("idle", idleAni);

	}

	@Override
	public void render(Renderer render)
	{

		render.begin();
		TextureRegion reg = animationCache.get("idle").getNextFrame();
		render.draw(reg, position.x, position.y, reg.getWidth() / 2, reg.getHeight() / 2, reg.getWidth(),
				reg.getHeight(), 1, -1, 0); // Draw
		render.end();
	}

	@Override
	public void input()
	{

		int val = 1;
		if (Keyboard.isKeyPressed(KeyCode.W))
		{
			direction.y = -val;
		}
		if (Keyboard.isKeyPressed(KeyCode.A))
		{
			direction.x = -val;

		}
		if (Keyboard.isKeyPressed(KeyCode.S))
		{
			direction.y = val;

		}
		if (Keyboard.isKeyPressed(KeyCode.D))
		{
			direction.x = val;

		}

	}

	@Override
	public void update()
	{
		previousPosition.set(position.x, position.y);

		velocity = direction.mul(600f);
		float t = Sirius.timer.getFPS();
		if (t <= 0)
			t = 1;
		position.add(velocity.mul(1 / t));
		direction.x = direction.x * (1 / t);
		direction.y = direction.y * (1 / t);

	}

	@Override
	public void free()
	{
		idleTexture.free();
	}

	@Override
	public void update(float delta)
	{

	}
}
