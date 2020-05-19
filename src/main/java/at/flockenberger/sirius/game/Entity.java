package at.flockenberger.sirius.game;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.texture.Texture;

public abstract class Entity
{
	protected Vector2f position;
	protected Vector2f rotation;
	protected Vector2f scale;
	protected Texture texture;
	protected Color color;

	public Entity(Vector2f position)
	{
		this(position, new Vector2f(0));
	}

	public Entity(Vector2f position, Vector2f rotation)
	{
		this(position, rotation, new Vector2f(1));
	}

	public Entity(Vector2f position, Vector2f rotation, Vector2f scale)
	{
		this(position, rotation, scale, Texture.createTexture(1, 1));
	}

	public Entity(Vector2f position, Vector2f rotation, Vector2f scale, Texture texture)
	{
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
	}

	public Entity()
	{
		position = new Vector2f(0);
		rotation = new Vector2f(0);
		scale = new Vector2f(1);
		color = Color.WHITE;
	}

	public Vector2f getPosition()
	{
		return this.position;
	}

	public Vector2f getRotation()
	{
		return this.rotation;
	}

	public Vector2f getScale()
	{
		return this.scale;
	}

	public abstract void render(Renderer render);

	public abstract void input();

	public abstract void update();
}
