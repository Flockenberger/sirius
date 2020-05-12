package at.flockenberger.sirius.game;

import org.joml.Vector3f;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.texture.Texture;

public abstract class Entity
{
	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;

	protected Texture texture;

	public Entity()
	{
		position = new Vector3f(0);
		rotation = new Vector3f(0);
		scale = new Vector3f(1);
	}

	public Vector3f getPosition()
	{
		return this.position;
	}

	public Vector3f getRotation()
	{
		return this.rotation;
	}

	public Vector3f getScale()
	{
		return this.scale;
	}

	public abstract void render(Renderer render);

	public abstract void input();

	public abstract void update();
}
