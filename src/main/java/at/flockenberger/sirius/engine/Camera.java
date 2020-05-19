package at.flockenberger.sirius.engine;

import org.joml.Matrix4f;

import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.game.Entity;
import at.flockenberger.sirius.utillity.SUtils;

public class Camera extends Entity implements ICamera
{
	protected Matrix4f projection;
	private Matrix4f view;
	private Matrix4f viewProj;
	private float m_AspectRatio;
	private float m_ZoomLevel;

	public Camera()
	{
		super();
		projection = new Matrix4f();
		view = new Matrix4f();
		viewProj = new Matrix4f();
	}

	@Override
	public Matrix4f getProjectionMatrix()
	{
		return projection;
	}

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
		m_ZoomLevel = 1000f;
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
		float mul = 1;
		float val = 10;

		if (Keyboard.isShiftDown())
			mul = 10;
		if (Keyboard.isKeyPressed(KeyCode.W))
		{
			position.y += mul * val;
		}
		if (Keyboard.isKeyPressed(KeyCode.A))
		{
			position.x += mul * val;

		}
		if (Keyboard.isKeyPressed(KeyCode.S))
		{
			position.y -= mul * val;

		}
		if (Keyboard.isKeyPressed(KeyCode.D))
		{
			position.x -= mul * val;

		}
		if (Keyboard.isKeyPressed(KeyCode.F))
		{
			rotation.x += SUtils.degToRad(mul * val);
		}
		
		m_ZoomLevel -= Mouse.getDeltaScrollY();

	}

	@Override
	public void update()
	{

	}

}
