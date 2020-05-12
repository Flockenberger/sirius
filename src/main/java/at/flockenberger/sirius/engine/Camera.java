package at.flockenberger.sirius.engine;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import at.flockenberger.sirius.ICamera;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.game.Entity;
import at.flockenberger.sirius.utillity.SUtils;

public class Camera extends Entity implements ICamera
{
	private Matrix4f projection;
	private Matrix4f view;
	Vector3f forward = new Vector3f();
	Matrix3f normal = new Matrix3f();

	public Camera()
	{
		super();
		projection = new Matrix4f();
		view = new Matrix4f();
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

	public void recalculateMatrices(int width, int height)
	{
		projection = SUtils.orthographic(0f, width, height, 0f, -1f, 1f);
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
		if(Keyboard.isShiftDown())
			mul = 10;
		if (Keyboard.isKeyPressed(KeyCode.W))
		{
			position.y += mul*1;
		}
		if (Keyboard.isKeyPressed(KeyCode.A))
		{
			position.x -= mul*1;

		}
		if (Keyboard.isKeyPressed(KeyCode.S))
		{
			position.y -= mul*1;

		}
		if (Keyboard.isKeyPressed(KeyCode.D))
		{
			position.x += mul*1;

		}

	}

	@Override
	public void update()
	{

	}

}
