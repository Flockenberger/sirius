package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.entity.AnimateableEntity;

public class Player extends AnimateableEntity
{
	private static final float G = 9.81f;
	private boolean jumping;

	public Player()
	{
		super();
		setTexture(Texture.createTexture(Sirius.resMan.getImage("mc").trimImage()));
		Animation<TextureRegion> idleAni = new Animation<TextureRegion>(1, new TextureRegion(getTexture()));
		addAnimation("idle", idleAni);
		setActiveAnimation("idle");

	}

	@Override
	public void render(Renderer render)
	{
		TextureRegion reg = (TextureRegion) activeAnimation().getNextFrame();
		render.draw(reg, position.x - reg.getWidth() / 2f, position.y - reg.getHeight() / 2f, reg.getWidth() / 2f,
				reg.getHeight() / 2f, reg.getWidth(), reg.getHeight(), 1, -1, 0);

		drawBoundingBox(render);

		super.render(render);

	}

	@Override
	public void input()
	{
		Keyboard kb = Keyboard.get();
		int val = 1;
		if (kb.isShiftDown())
		{
			val = 15;
		}
		if (kb.isKeyPressed(KeyCode.W))
		{
			direction.y = -val;
		}
		if (kb.isKeyPressed(KeyCode.A))
		{
			direction.x = -val;

		}
		if (kb.isKeyPressed(KeyCode.S))
		{
			direction.y = val;

		}
		if (kb.isKeyPressed(KeyCode.D))
		{
			direction.x = val;

		}
		if (kb.isKeyTyped(KeyCode.SPACE) && !jumping)
		{
			jumping = true;
			direction.y -= 1;
		}

	}

	@Override
	public void update()
	{
		super.update();

		if (Sirius.timer.getFPS() > 0)
		{
			velocity.set(direction);
			velocity.mul(100f);
			velocity.mul(1 / (float) Sirius.timer.getFPS());
			// velocity.y += G; //gravitation
			position.add(velocity);
			direction.mul(0.9f);
		}

		Sirius.audioManager.getListener().setPosition(getPosition());
	}

	@Override
	public void free()
	{
		super.free();

	}

	@Override
	public void update(float delta)
	{

	}
}
