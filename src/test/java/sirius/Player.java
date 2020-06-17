package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.GameObject;
import at.flockenberger.sirius.game.entity.AnimateableEntity;

public class Player extends AnimateableEntity
{
	private static final float G = 9.81f;
	private boolean jumping;
	private int jumpCount = 0;

	public Player()
	{
		super();
		setTexture(Texture.createTexture(Sirius.resMan.getImage("mc").trimImage()), true);
		Animation<TextureRegion> idleAni = new Animation<TextureRegion>(1, new TextureRegion(getTexture()));
		addAnimation("idle", idleAni);
		setActiveAnimation("idle");

	}

	@Override
	public void render(Renderer render)
	{
		TextureRegion reg = (TextureRegion) activeAnimation().getNextFrame();
		render.drawEntity(this, reg);
		drawBoundingBox(render);

	}

	@Override
	public void input()
	{
		Keyboard kb = Keyboard.get();
		int val = 3;
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
			direction.y -= 20;
		}

	}

	@Override
	public void onCollision(GameObject e)
	{
		if (e.isCollideable())
		{
			applyDefaultCollision(e);
			jumping = false;

		}
	}

	@Override
	public void update()
	{
		super.update();

		if (Sirius.timer.getFPS() > 0)
		{
			velocity.set(direction);
			velocity.y += G; // gravitation

			velocity.mul(100f);
			velocity.mul(1 / (float) Sirius.timer.getFPS());
			position.add(velocity);
			if (position.y > 0)
			{
				position.y = 0;
				jumping = false;

			}
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
