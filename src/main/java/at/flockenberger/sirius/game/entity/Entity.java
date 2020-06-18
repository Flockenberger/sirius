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
	/**
	 * components extension
	 */
	private Map<String, IComponent> mComponents;

	/**
	 * current direction fo the entity
	 */
	protected Vector2f direction;

	/**
	 * the previous position of the entity
	 */
	protected Vector2f previousPosition;

	/**
	 * the current velocity of the entity
	 */
	protected Vector2f velocity;

	/**
	 * the name of the entity as {@link Text}
	 */
	protected Text name;

	/**
	 * the texture of the entity
	 */
	private Texture texture;

	/**
	 * the color of this entity
	 */
	protected Color color;

	/**
	 * the width of this entity
	 */
	protected int width;

	/**
	 * the height of this entity
	 */
	protected int height;

	/**
	 * Constructor.<br>
	 * Creates a new {@link Entity}<br>
	 * 
	 * @param position the position of this entity
	 */
	public Entity()
	{
		this(new Vector2f(0));
	}

	/**
	 * Constructor.<br>
	 * Creates a new {@link Entity}<br>
	 * 
	 * @param position the position of this entity
	 */
	public Entity(Vector2f position)
	{
		this(position, new Vector2f(0));
	}

	/**
	 * Constructor.<br>
	 * Creates a new {@link Entity}<br>
	 * 
	 * @param position the position of this entity
	 * @param rotation the rotation of this entity
	 */
	public Entity(Vector2f position, Vector2f rotation)
	{
		this(position, rotation, new Vector2f(1));
	}

	/**
	 * Constructor.<br>
	 * Creates a new {@link Entity}<br>
	 * 
	 * @param position the position of this entity
	 * @param rotation the rotation of this entity
	 * @param scale    the scale of this entity
	 */
	public Entity(Vector2f position, Vector2f rotation, Vector2f scale)
	{
		this(position, rotation, scale, Texture.createTexture(1, 1));
	}

	/**
	 * Constructor.<br>
	 * Creates a new {@link Entity}<br>
	 * 
	 * @param position the position of this entity
	 * @param rotation the rotation of this entity
	 * @param scale    the scale of this entity
	 * @param texture  the texture of this entity
	 */
	public Entity(Vector2f position, Vector2f rotation, Vector2f scale, Texture texture)
	{
		SUtils.checkNull(position);
		SUtils.checkNull(rotation);
		SUtils.checkNull(scale);
		SUtils.checkNull(texture);

		this.position = position;
		this.position.x -= texture.getWidth() / 2f;
		this.position.y -= texture.getHeight() / 2f;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		this.direction = new Vector2f(0);
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
		this.boundingBox = new BoundingBox(this);
		this.name = new Text("Ana");

		this.velocity = new Vector2f(0);
		this.previousPosition = new Vector2f(0);
		this.color = Color.WHITE;
		this.mComponents = new HashMap<String, IComponent>(16);
	}

	/**
	 * Draws this entities {@link BoundingBox}.<br>
	 * <b>Note:</b>This method does not actually use the {@link BoundingBox} to draw
	 * the surrounding box but rather use the position and {@link #getWidth()} /
	 * {@link #getHeight()} parameters. <br>
	 * This method ends any rendering should {@link Renderer#isDrawing()} return
	 * true. It also starts a new {@link Renderer#begin()} call.
	 * 
	 * @param render the renderer
	 */
	public void drawBoundingBox(Renderer render)
	{
		if (render.isDrawing())
			render.end();

		render.beginShape(ShapeType.LINE);
		render.rect(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getWidth(), boundingBox.getHeight());
		render.endShape();
		render.begin();
	}

	/**
	 * The current direction of this entity.<br>
	 * To set this vector use this method and the
	 * {@link Vector2f#set(org.joml.Vector2fc)} functionality.
	 * 
	 * @return the direction {@link Vector2f}
	 */
	public Vector2f getDirection()
	{ return direction; }

	/**
	 * The current velocity of this entity.<br>
	 * To set this vector use this method and the
	 * {@link Vector2f#set(org.joml.Vector2fc)} functionality.
	 * 
	 * @return the velocity {@link Vector2f}
	 */
	public Vector2f getVelocity()
	{ return velocity; }

	/**
	 * @return the texture attached to this entity
	 */
	public Texture getTexture()
	{ return texture; }

	/**
	 * Sets the {@link Texture} of this entity.<br>
	 * This method also sets the width and height of this entity if
	 * <code>applyDimension</code> is set to true.
	 * 
	 * @param texture        the texture to set
	 * @param applyDimension set this to true to apply the textures width and height
	 *                       to this entity
	 */
	public void setTexture(Texture texture, boolean applyDimension)
	{
		SUtils.checkNull(texture, Texture.class);
		this.texture = texture;
		if (applyDimension)
		{
			this.width = texture.getWidth();
			this.height = texture.getHeight();
		}
	}

	/**
	 * @return the current color of this entiy
	 */
	public Color getColor()
	{ return color; }

	/**
	 * Sets the color of this entity.<br>
	 * This method does not store a reference to the given {@link Color}
	 * <code>color</code> but copies the values into the internal color.
	 * 
	 * @param color the color to set this entity to
	 */
	public void setColor(Color color)
	{
		this.color.set(color);
	}

	/**
	 * Sets the width of this entity
	 * 
	 * @param width the new width to set
	 */
	public void setWidth(int width)
	{ this.width = width; }

	/**
	 * Sets the height of this entity
	 * 
	 * @param height the new height to set
	 */
	public void setHeight(int height)
	{ this.height = height; }

	/**
	 * @return the width of this entity
	 */
	public int getWidth()
	{ return width; }

	/**
	 * @return the height of this entity
	 */
	public int getHeight()
	{ return height; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoundingBox getBoundingBox()
	{
		this.boundingBox.set(this);
		return this.boundingBox;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2f getPosition()
	{
		Vector2f vec = new Vector2f(position);
		vec.x -= getWidth() / 2f;
		vec.y -= getHeight() / 2f;
		return vec;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2f getRotation()
	{ return rotation; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2f getScale()
	{ return scale; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update()
	{
		this.boundingBox.set(this);
		getAudioSource().setPosition(getPosition());
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta)
	{
		this.boundingBox.set(this);
		getAudioSource().setPosition(getPosition());

	}

	/**
	 * Default collision for entities.<br>
	 * This method uses {@link #applyDefaultCollision(GameObject)} for default
	 * collision calculations<br>
	 * <br>
	 * <br>
	 * {@inheritDoc}
	 */
	@Override
	public void onCollision(GameObject e)
	{
		if (e.isCollideable())
		{
			applyDefaultCollision(e);
		}
	}

	/**
	 * This method is called in {@link #onCollision(GameObject)} to support a
	 * default collision method.<br>
	 * If you want to handle collisions differently override the
	 * {@link #onCollision(GameObject)} method.
	 * 
	 * @param e the other {@link GameObject} to collide
	 */
	public void applyDefaultCollision(GameObject e)
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
