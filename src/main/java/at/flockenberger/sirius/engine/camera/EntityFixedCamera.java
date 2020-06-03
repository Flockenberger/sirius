package at.flockenberger.sirius.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.render.RenderSettings;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.game.entity.Entity;
import at.flockenberger.sirius.utillity.SUtils;

public class EntityFixedCamera extends Entity implements ICamera
{
	protected Matrix4f projection;
	private Matrix4f view;
	private Matrix4f viewProj;
	private float m_AspectRatio;
	private float m_ZoomLevel;
	private Entity player;

	public EntityFixedCamera(Entity p)
	{
		super();
		projection = new Matrix4f();
		view = new Matrix4f();
		viewProj = new Matrix4f();
		this.player = p;
	}

	public void setEntity(Entity p)
	{
		this.player = p;
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
		m_ZoomLevel = Sirius.renderSettings.getFloat(RenderSettings.ZOOM);
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

	}

	@Override
	public void update()
	{
		update(Sirius.timer.getDelta());
	}

	@Override
	public void free()
	{

	}

	@Override
	public void update(float delta)
	{
		if (player != null)
		{
			Vector2f pos = player.getPosition();
			position.set(-pos.x, -pos.y);
		}
	}

}
