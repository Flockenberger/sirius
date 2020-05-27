package at.flockenberger.sirius.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.component.IComponent;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.texture.Texture;

public abstract class Entity implements IFreeable
{
	private Map<String, IComponent> mComponents;

	protected Vector2f position;
	protected Vector2f direction;
	protected Vector2f previousPosition;
	protected Vector2f velocity;
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
		mComponents = new HashMap<String, IComponent>(16);

		position = new Vector2f(0);
		rotation = new Vector2f(0);
		direction = new Vector2f(0);
		scale = new Vector2f(1);
		previousPosition = new Vector2f(0);
		velocity = new Vector2f(0);
		color = Color.WHITE;
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
	public final <T extends IComponent> T getComponentByType(Class<T> componentType)
	{
		Object o = mComponents.get(componentType.getName());
		return (o == null) ? null : (T) o;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public abstract void render(Renderer render);

	public abstract void input();

	public abstract void update();

	public abstract void update(float delta);
}
