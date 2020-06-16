package sirius;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.GameObject;
import at.flockenberger.sirius.game.entity.AnimateableEntity;
import at.flockenberger.sirius.game.entity.Entity;

public class OtherEntity extends AnimateableEntity
{

	private Texture compantionTex;

	public OtherEntity()
	{
		compantionTex = Texture.createTexture(Sirius.resMan.getImage("companion").trimImage());
		this.width = compantionTex.getWidth();
		this.height = compantionTex.getHeight();
		position.x = 100;
	}

	@Override
	public void render(Renderer render)
	{
		render.begin();
		render.drawTexture(compantionTex, position.x - width / 2f, position.y - height / 2f); // Draw
		render.end();

		drawBoundingBox(render);
		getAudioSource().setLooping(true);
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

	

	@Override
	public void free()
	{
		compantionTex.free();
	}

	@Override
	public void update(float delta)
	{

	}

	@Override
	public void onCollision(GameObject e)
	{
		
	}
}
