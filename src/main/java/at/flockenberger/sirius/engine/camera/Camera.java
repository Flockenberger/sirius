package at.flockenberger.sirius.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import at.flockenberger.sirius.engine.collision.BoundingBox;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.GameObject;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Camera</h1><br>
 * The {@link Camera} is, as the name suggests, the base-camera class for the
 * Sirius engine.<br>
 * It extends {@link GameObject} and implements the {@link ICamera} interface.
 * 
 * @author Florian Wagner
 * @see GameObject
 * @see ICamera
 */
public class Camera extends GameObject implements ICamera
{
	/**
	 * the projection matrix
	 */
	protected Matrix4f projection;

	/**
	 * the view matrix
	 */
	protected Matrix4f view;

	/**
	 * the combined projection and view matrix
	 */
	protected Matrix4f viewProj;

	/**
	 * the aspect ratio
	 */
	protected float aspectRatio;

	/**
	 * the zoom level
	 */
	protected float zoomLevel;

	/**
	 * Constructor<br>
	 * Creates a new Camera Object.
	 */
	public Camera()
	{
		super();
		projection = new Matrix4f();
		view = new Matrix4f();
		viewProj = new Matrix4f();
		zoomLevel = 1000f;

	}

	/**
	 * Sets the zoom level for this camera
	 * 
	 * @param zoom the zoom to set
	 */
	public void setZoom(float zoom)
	{ this.zoomLevel = zoom; }

	/**
	 * @return the zoom level for this camera
	 */
	public float getZoom()
	{ return this.zoomLevel; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Matrix4f getProjectionMatrix()
	{ return projection; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Matrix4f getViewMatrix()
	{
		updateViewMatrix();
		return view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Matrix4f getViewProjectionMatrix()
	{
		updateViewMatrix();
		viewProj.identity();
		view.mul(projection, viewProj);
		return viewProj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recalculateMatrices(int width, int height)
	{
		aspectRatio = width / (float) height;
		projection = SUtils.orthographic(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, zoomLevel, -zoomLevel, -1,
				1);

	}

	/**
	 * Called internally to recalculate the view matrix of this camera.<br>
	 * It sets the position, scale and rotatio
	 */
	public void updateViewMatrix()
	{
		view = new Matrix4f();
		view.identity();
		view.translate(position.x, position.y, 0);
		view.scale(scale.x, scale.y, 0);
		view.rotateXYZ(0, rotation.y, rotation.x);
		view.transpose();
	}

	/**
	 * <b>This method is empty</b><br>
	 * {@inheritDoc}
	 */
	@Override
	public void render(Renderer render)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void input()
	{

		zoomLevel -= Mouse.get().getDeltaScrollY();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update()
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void free()
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta)
	{

	}

	/**
	 * <b> This method returns a NullPointer as the camera has no bounding
	 * box</b><br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public BoundingBox getBoundingBox()
	{ return null; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCollision(GameObject e)
	{
		// stub
	}

	@Override
	public Vector2f getPosition()
	{ return position; }

	@Override
	public Vector2f getRotation()
	{ return rotation; }

	@Override
	public Vector2f getScale()
	{ return scale; }

}
