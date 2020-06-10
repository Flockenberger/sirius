package at.flockenberger.sirius.engine.camera;

import org.joml.Vector2f;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.game.entity.Entity;

public class EntityFixedCamera extends Camera
{
	private Entity player;

	public EntityFixedCamera(Entity p)
	{
		super();
		this.player = p;
		this.m_ZoomLevel = 100;
	}

	public void setEntity(Entity p)
	{
		this.player = p;
	}

	@Override
	public void update()
	{
		update(Sirius.timer.getDelta());
		
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
