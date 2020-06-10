package at.flockenberger.sirius.audio;

import org.joml.Vector2f;
import org.lwjgl.openal.AL11;

public class AudioListener
{
	private Vector2f position;
	private Vector2f velocity;
	private AudioDistanceFunction distancefnct;

	public AudioListener()
	{
		this(new Vector2f(0), new Vector2f(0));
	}

	public AudioListener(Vector2f pos, Vector2f vel)
	{
		this(pos, vel, AudioDistanceFunction.EXPONENT_CLAMPED);
	}

	public AudioListener(Vector2f pos, Vector2f vel, AudioDistanceFunction fnct)
	{
		this.position = pos;
		this.velocity = vel;
		this.distancefnct = fnct;
	}

	public Vector2f getPosition()
	{ return position; }

	public void setPosition(Vector2f position)
	{
		this.position = position;
		AL11.alListener3f(AL11.AL_POSITION, position.x, position.y, 0);

	}

	public Vector2f getVelocity()
	{ return velocity; }

	public void setVelocity(Vector2f velocity)
	{
		this.velocity = velocity;
		AL11.alListener3f(AL11.AL_VELOCITY, velocity.x, velocity.y, 0);
	}

	public AudioDistanceFunction getDistanceFunction()
	{ return distancefnct; }

	public void setDistanceFunction(AudioDistanceFunction distancefnct)
	{
		this.distancefnct = distancefnct;
		AL11.alDistanceModel(distancefnct.getDF());
	}

}
