package at.flockenberger.sirius.game;

import org.joml.Vector2f;

import at.flockenberger.sirius.audio.AudioSource;
import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.camera.Camera;
import at.flockenberger.sirius.engine.collision.BoundingBox;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.game.entity.Entity;

/**
 * </h1>GameObject</h1><br>
 * The {@link GameObject} is the base class for all {@link Entity} related
 * classes aswell as the {@link Camera} classes. <br>
 * In general any object that might have a position, rotation and scale as well
 * as being able to update, handle input or be rendered to the screen should be
 * inheriting from this {@link GameObject} class.
 * 
 * @author Florian Wagner
 * 
 * @see IFreeable
 * @see Entity
 * @see Camera
 *
 */
public abstract class GameObject implements IFreeable
{
	/**
	 * position of the object
	 */
	protected Vector2f position;

	/**
	 * rotation of the object
	 */
	protected Vector2f rotation;

	/**
	 * scale of the object
	 */
	protected Vector2f scale;

	/**
	 * the object bounding box
	 */
	protected BoundingBox boundingBox;

	/**
	 * the {@link AudioSource} of this object
	 */
	protected AudioSource audioSource;

	public GameObject()
	{
		this.position = new Vector2f(0);
		this.rotation = new Vector2f(0);
		this.scale = new Vector2f(1);
		this.audioSource = new AudioSource(this.position);
	}

	/**
	 * Called by the main application to render this object.<br>
	 * Depending on what kind of type this object is, for example GUI related then
	 * this object should call
	 * {@link Renderer#beginShape(at.flockenberger.sirius.engine.render.Renderer.ShapeType)}
	 * to start shape rendering and {@link Renderer#endShape()} to end it.<br>
	 * Otherwise a global {@link Renderer#begin()} and {@link Renderer#end()} call
	 * should be handled inside of the current {@link LayerBase}.
	 * 
	 * @param render the renderer
	 */
	public abstract void render(Renderer render);

	/**
	 * Called to handle any input for this object.<br>
	 * It is called before the {@link #update()} / {@link #update(float)} and
	 * {@link #render(Renderer)} calls.
	 */
	public abstract void input();

	/**
	 * This method is called on every frame before the {@link #render(Renderer)}
	 * call but after the {@link #input()} call<br>
	 * It can be used to update the current object, check states etc.<br>
	 * <b>Note</b>: {@link #update()} and {@link #update(float)} are called in the
	 * same manner and can be called multiple times per frame. Only update method
	 * should be implemented!
	 */
	public abstract void update();

	/**
	 * This method is called on every frame before the {@link #render(Renderer)}
	 * call but after the {@link #input()} call<br>
	 * It can be used to update the current object, check states etc.<br>
	 * <b>Note</b>: {@link #update()} and {@link #update(float)} are called in the
	 * same manner and can be called multiple times per frame. Only update method
	 * should be implemented!
	 * 
	 * @param delta the delta time of this and the last frame
	 */
	public abstract void update(float delta);

	/**
	 * Constructs and returns a {@link BoundingBox} for this object.
	 * 
	 * @return this object {@link BoundingBox}
	 */
	public abstract BoundingBox getBoundingBox();

	/**
	 * @return the {@link AudioSource} which is associated with this game object.
	 */
	public AudioSource getAudioSource()
	{ return this.audioSource; }

	@Override
	public void free()
	{
		audioSource.free();
	}
}
