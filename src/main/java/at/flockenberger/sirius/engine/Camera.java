package at.flockenberger.sirius.engine;

import org.joml.Matrix4f;

import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.game.Entity;
import at.flockenberger.sirius.utillity.SUtils;

public class Camera extends Entity implements ICamera
{
	private Matrix4f projection;
	private Matrix4f view;
	private Matrix4f viewProj;

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
		projection = SUtils.orthographic(0f, width, height, 0f, -10f, 10f);
	}

	public void updateViewMatrix()
	{
		view = new Matrix4f();
		view.identity();
		view.translate(position.x, position.y, 0);
		view.scale(scale);
		view.rotateXYZ(rotation);
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
		if (Keyboard.isShiftDown())
			mul = 10;
		if (Keyboard.isKeyPressed(KeyCode.W))
		{
			position.y += mul * 1;
		}
		if (Keyboard.isKeyPressed(KeyCode.A))
		{
			position.x += mul * 1;

		}
		if (Keyboard.isKeyPressed(KeyCode.S))
		{
			position.y -= mul * 1;

		}
		if (Keyboard.isKeyPressed(KeyCode.D))
		{
			position.x -= mul * 1;

		}

	}

	@Override
	public void update()
	{

	}

}
