package at.flockenberger.sirius.game.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.collision.BoundingBox;
import at.flockenberger.sirius.engine.component.IComponent;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;

public abstract class Entity implements IFreeable
{
	private Map<String, IComponent> mComponents;

	protected Vector2f position;
	protected Vector2f direction;
	protected Vector2f previousPosition;
	protected Vector2f velocity;
	protected Vector2f rotation;
	protected Vector2f scale;
	protected int width;
	protected int height;
	private Texture texture;
	protected Color color;
	private BoundingBox boundingBox;

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
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		this.boundingBox = new BoundingBox(this);

	}

	public Entity()
	{
		this.mComponents = new HashMap<String, IComponent>(16);

		this.position = new Vector2f(0);
		this.rotation = new Vector2f(0);
		this.direction = new Vector2f(0);
		this.scale = new Vector2f(1);
		this.previousPosition = new Vector2f(0);
		this.velocity = new Vector2f(0);
		this.width = 0;
		this.height = 0;
		this.color = Color.WHITE;
		this.boundingBox = new BoundingBox(this);

	}

	public Vector2f getPosition()
	{
		return position;
	}

	public Vector2f getDirection()
	{
		return direction;
	}

	public void setDirection(Vector2f direction)
	{
		this.direction = direction;
	}

	public Vector2f getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector2f velocity)
	{
		this.velocity = velocity;
	}

	public Vector2f getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector2f rotation)
	{
		this.rotation = rotation;
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public BoundingBox getBoundingBox()
	{
		this.boundingBox.set(this);
		return this.boundingBox;
	}

	public abstract void render(Renderer render);

	public abstract void input();

	public abstract void update();

	public abstract void update(float delta);

	public void free()
	{
		if (texture != null)
			texture.free();
	}

	protected void addComponent(IComponent component)
	{
		mComponents.put(component.getClass().getName(), component);
	}

	protected void addComponent(String name, IComponent component)
	{
		mComponents.put(name, component);
	}

	/**
	 * check if the entity matches all required components
	 * 
	 * @param componentsType
	 * @return boolean
	 */
	final boolean matchesComponentTypes(List<Class<? extends IComponent>> componentsType)
	{
		for (Class<? extends IComponent> componentType : componentsType)
		{
			if (getComponentByType(componentType) == null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * check if the entity matches all required components
	 * 
	 * @param componentsType
	 * @return boolean
	 */
	final boolean matchesComponentTypes(IComponent... componentsType)
	{
		for (IComponent componentType : componentsType)
		{
			if (getComponentByType(componentType.getClass()) == null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param componentType
	 * @param <T>           get the component of a given type
	 * @return T the component, null if not found
	 */
	@SuppressWarnings("unchecked")
	public final <T extends IComponent> T getComponentByType(Class<T> componentType)
	{
		Object o = mComponents.get(componentType.getName());
		return (o == null) ? null : (T) o;
	}

}
