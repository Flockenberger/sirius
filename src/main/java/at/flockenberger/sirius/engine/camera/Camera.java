package at.flockenberger.sirius.engine.camera;

import org.joml.Matrix4f;

import at.flockenberger.sirius.engine.collision.BoundingBox;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.GameObject;
import at.flockenberger.sirius.utillity.SUtils;

public class Camera extends GameObject implements ICamera
{
	protected Matrix4f projection;
	protected Matrix4f view;
	protected Matrix4f viewProj;

	protected float m_AspectRatio;
	protected float m_ZoomLevel;

	public Camera()
	{
		super();
		projection = new Matrix4f();
		view = new Matrix4f();
		viewProj = new Matrix4f();
		m_ZoomLevel = 1000f;

	}

	public void setZoom(float zoom)
	{ this.m_ZoomLevel = zoom; }

	@Override
	public Matrix4f getProjectionMatrix()
	{ return projection; }

	@Override
	public Matrix4f getViewMatrix()
	{
		updateViewMatrix();
		return view;
	}

	@Override
	public Matrix4f getViewProjectionMatrix()
	{
		updateViewMatrix();
		viewProj.identity();
		view.mul(projection, viewProj);
		return viewProj;
	}

	public void recalculateMatrices(int width, int height)
	{
		m_AspectRatio = width / (float) height;
		projection = SUtils.orthographic(-m_AspectRatio * m_ZoomLevel, m_AspectRatio * m_ZoomLevel, m_ZoomLevel,
				-m_ZoomLevel, -1, 1);

	}

	public void updateViewMatrix()
	{
		view = new Matrix4f();
		view.identity();
		view.translate(position.x, position.y, 0);
		view.scale(scale.x, scale.y, 0);
		view.rotateXYZ(0, rotation.y, rotation.x);
		view.transpose();
	}

	@Override
	public void render(Renderer render)
	{

	}

	@Override
	public void input()
	{

		m_ZoomLevel -= Mouse.get().getDeltaScrollY();

	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void free()
	{

	}

	@Override
	public void update(float delta)
	{

	}

	@Override
	public BoundingBox getBoundingBox()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
