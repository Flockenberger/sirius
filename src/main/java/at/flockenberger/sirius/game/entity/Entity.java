package at.flockenberger.sirius.game.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.collision.BoundingBox;
import at.flockenberger.sirius.engine.component.IComponent;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.engine.render.Renderer.ShapeType;
import at.flockenberger.sirius.game.GameObject;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Entity</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public abstract class Entity extends GameObject
{
	private Map<String, IComponent> mComponents;

	protected Vector2f direction;
	protected Vector2f previousPosition;
	protected Vector2f velocity;
	protected Text name;
	protected int width;
	protected int height;
	private Texture texture;
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
		SUtils.checkNull(position);
		SUtils.checkNull(rotation);
		SUtils.checkNull(scale);
		SUtils.checkNull(texture);

		this.position = position;

		this.position.x -= texture.getWidth() / 2f;
		this.position.y -= texture.getHeight() / 2f;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		this.boundingBox = new BoundingBox(this);
		this.name = new Text("Ana");

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
		this.name = new Text("Ana");
	}

	public void drawBoundingBox(Renderer render)
	{
		render.beginShape(ShapeType.LINE);
		render.rect(getPosition().x, getPosition().y, width, height);
		render.endShape();
	}

	@Override
	public void render(Renderer render)
	{
		render.begin();
		name.position(getPosition().x, getPosition().y);
		name.draw();
		render.end();
	}

	public Vector2f getDirection()
	{ return direction; }

	public void setDirection(Vector2f direction)
	{ this.direction = direction; }

	public Vector2f getVelocity()
	{ return velocity; }

	public void setVelocity(Vector2f velocity)
	{ this.velocity = velocity; }

	public Vector2f getRotation()
	{ return rotation; }

	public void setRotation(Vector2f rotation)
	{ this.rotation = rotation; }

	public Vector2f getScale()
	{ return scale; }

	public void setScale(Vector2f scale)
	{ this.scale = scale; }

	public Texture getTexture()
	{ return texture; }

	public void setTexture(Texture texture)
	{
		this.texture = texture;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	public Color getColor()
	{ return color; }

	public void setColor(Color color)
	{ this.color = color; }

	public void setPosition(Vector2f position)
	{ this.position = position; }

	public int getWidth()
	{ return this.width; }

	public int getHeight()
	{ return this.height; }

	public BoundingBox getBoundingBox()
	{
		this.boundingBox.set(this);
		return this.boundingBox;
	}

	public Vector2f getPosition()
	{
		Vector2f vec = new Vector2f(position);
		vec.x -= width / 2f;
		vec.y -= height / 2f;
		return vec;
	}

	@Override
	public void update()
	{
		this.boundingBox.set(this);
		getAudioSource().setPosition(getPosition());

	}

	@Override
	public void update(float delta)
	{
		this.boundingBox.set(this);
		getAudioSource().setPosition(getPosition());

	}

	/**
	 * Default collision for entities.<br>
	 * {@inheritDoc}
	 */
	@Override
	public void onCollision(GameObject e)
	{

		float dx = 0;
		float dy = 0;
		float dx2 = 0;
		float dy2 = 0;

		BoundingBox bb = getBoundingBox();
		BoundingBox bbo = e.getBoundingBox();

		// pull the input for direction
		input();

		// we go up
		if (direction.y < 0)
		{

			dy = Math.abs(bb.getMinY() - bbo.getMaxY());

			dx = Math.abs(bb.getMaxX() - bbo.getMinX()); // left
			dx2 = Math.abs(bb.getMinX() - bbo.getMaxX()); // right

			if (dy < dx && dy < dx2)
				position.y = bbo.getMaxY() + bb.getHeight() / 2f;

			// corner cases
			// case1: left bottom corner
			else if (dy == dx)
			{
				position.y = bbo.getMaxY() + bb.getHeight() / 2f;
				position.x = bbo.getMinX() - bb.getWidth() / 2f;
				// case2: right bottom corner
			} else if (dy == dx2)
			{
				position.x = bbo.getMaxX() + bb.getWidth() / 2f;
				position.y = bbo.getMaxY() + bb.getHeight() / 2f;

			}
		}
		// we go down
		if (direction.y > 0)
		{
			dy = Math.abs(bb.getMaxY() - bbo.getMinY());

			dx = Math.abs(bb.getMaxX() - bbo.getMinX()); // left
			dx2 = Math.abs(bb.getMinX() - bbo.getMaxX()); // right

			if (dy < dx && dy < dx2)
			{
				position.y = bbo.getMinY() - bb.getHeight() / 2f;
				// case1: top left corner
			} else if (dy == dx)
			{
				position.y = bbo.getMinY() - bb.getHeight() / 2f;
				position.x = bbo.getMinX() - bb.getWidth() / 2f;

				// case2: top right corner
			} else if (dy == dx2)
			{
				position.x = bbo.getMaxX() + bb.getWidth() / 2f;
				position.y = bbo.getMinY() - bb.getHeight() / 2f;
			}
		}
		// we go right
		if (direction.x > 0)
		{

			dx = Math.abs(bb.getMaxX() - bbo.getMinX());

			dy = Math.abs(bb.getMinY() - bbo.getMaxY());
			dy2 = Math.abs(bb.getMaxY() - bbo.getMinY());
			if (dx < dy && dx < dy2)
			{
				position.x = bbo.getMinX() - bb.getWidth() / 2f;
			}
// else if (dx == dy)
// {
// position.x = bbo.getMinX() - bb.getWidth() / 2f;
// position.y = bbo.getMaxY() + bb.getHeight() / 2f;
//
// } else if (dx == dy2)
// {
// position.y = bbo.getMinY() - bb.getHeight() / 2f;
// position.x = bbo.getMinX() - bb.getWidth() / 2f;
// }
		}
		// we go left
		if (direction.x < 0)
		{
			dx = Math.abs(bb.getMinX() - bbo.getMaxX());

			dy = Math.abs(bb.getMaxY() - bbo.getMinY());
			dy2 = Math.abs(bb.getMinY() - bbo.getMaxY());

			if (dx < dy && dx < dy2)
			{
				position.x = bbo.getMaxX() + bb.getWidth() / 2f;
			}
// else if (dx == dy)
// {
// position.x = bbo.getMaxX() + bb.getWidth() / 2f;
// position.y = bbo.getMinY() - bb.getHeight() / 2f;
//
// } else if (dx == dy2)
// {
// position.y = bbo.getMaxY() + bb.getHeight() / 2f;
// position.x = bbo.getMaxX() + bb.getWidth() / 2f;
//
// }
		}
		// clear the pulled direction as we only use it for calculation
		direction.x = direction.y = 0;
	}

	@Override
	public void free()
	{
		super.free();
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
	public final boolean matchesComponentTypes(List<Class<? extends IComponent>> componentsType)
	{
		for (Class<? extends IComponent> componentType : componentsType)
		{
			if (getComponentByType(componentType) == null)
			{ return false; }
		}
		return true;
	}

	/**
	 * check if the entity matches all required components
	 * 
	 * @param componentsType
	 * @return boolean
	 */
	public final boolean matchesComponentTypes(IComponent... componentsType)
	{
		for (IComponent componentType : componentsType)
		{
			if (getComponentByType(componentType.getClass()) == null)
			{ return false; }
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
