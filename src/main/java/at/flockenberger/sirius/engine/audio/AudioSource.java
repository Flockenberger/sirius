package at.flockenberger.sirius.engine.audio;

import org.joml.Vector2f;
import org.lwjgl.openal.AL11;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.utillity.exceptions.AudioNotInitializedException;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>AudioSource</h1><br>
 * The {@link AudioSource} class is able to play sounds which have been loaded
 * using the {@link AudioManager}. <br>
 * 
 * @author Florian Wagner
 *
 */
public class AudioSource implements IFreeable
{
	/**
	 * the source id for OpenAL
	 */
	protected final int id;
	/**
	 * the gain of the audio
	 */
	protected float gain;

	/**
	 * the pitch of the audio
	 */
	protected float pitch;

	/**
	 * the looping property of this audio source
	 */
	protected boolean looping;

	/**
	 * the position of the audio
	 */
	protected Vector2f position;

	/**
	 * the rolloff of this audio source
	 */
	protected float rolloff;

	/**
	 * the reference distance of this audio source
	 */
	protected float referenceDistance;

	/**
	 * the max distance of this audio source
	 */
	protected float maxDistance;

	/**
	 * Creates a new {@link AudioSource} object.
	 */
	public AudioSource()
	{
		this(AL11.alGenSources());
	}

	/**
	 * Creates a new audio source and sets its position.
	 * 
	 * @param position the position of the audio source
	 */
	public AudioSource(Vector2f position)
	{
		this(AL11.alGenSources());
		this.position = position;
	}

	/**
	 * Internal constructor.<br>
	 * Sets the id of this audio source
	 * 
	 * @param id the id for this audio source
	 */
	private AudioSource(int id)
	{
		this.id = id;
		setGain(1);
		setPitch(1);
		setPosition(new Vector2f(0));
		setRolloff(5);
		setReferenceDistance(1);
		setMaxDistance(10);
	}

	/**
	 * Pauses the current audio playback.<br>
	 * To resume the current audio playback use {@link #resume()}
	 */
	public void pause()
	{
		if (isPlaying())
		{
			AL11.alSourcePause(this.id);
		}
	}

	/**
	 * Stops the current audio playback.
	 */
	public void stop()
	{
		AL11.alSourceStop(this.id);
	}

	/**
	 * Resumes the last audio playback
	 */
	public void resume()
	{
		AL11.alSourcePlay(this.id);
	}

	/**
	 * @return true if this source is currently playing a audio otherwise false
	 */
	public boolean isPlaying()
	{ return AL11.alGetSourcei(this.id, AL11.AL_SOURCE_STATE) == AL11.AL_PLAYING; }

	/**
	 * @return the gain parameter of this audio
	 */
	public float getGain()
	{ return gain; }

	/**
	 * Sets the gain parameter for this audio
	 * 
	 * @param gain the gain
	 */
	public void setGain(float gain)
	{
		this.gain = gain;
		AL11.alSourcef(this.id, AL11.AL_GAIN, this.gain);
	}

	/**
	 * @return the pitch parameter for this audio
	 */
	public float getPitch()
	{ return pitch; }

	/**
	 * Sets the pitch parameter for this audio
	 * 
	 * @param pitch the pitch
	 */
	public void setPitch(float pitch)
	{
		this.pitch = pitch;
		AL11.alSourcef(this.id, AL11.AL_PITCH, this.pitch);
	}

	/**
	 * @return the position for this audio
	 */
	public Vector2f getPosition()
	{ return position; }

	/**
	 * Sets the position for this audio
	 * 
	 * @param position the position
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
		AL11.alSource3f(this.id, AL11.AL_POSITION, this.position.x, this.position.y, 0);
	}

	/**
	 * Sets the looping property for this source. <br>
	 * If it is set to true the sound will loop over and over.
	 * 
	 * @param looping the looping property
	 */
	public void setLooping(boolean looping)
	{
		this.looping = looping;
		AL11.alSourcei(this.id, AL11.AL_LOOPING, this.looping ? 1 : 0);
	}

	/**
	 * @return true if this source is set to loop the sound over otherwise false
	 */
	public boolean isLooping()
	{ return this.looping; }

	/**
	 * @return the rolloff value of this audio source
	 */
	public float getRolloff()
	{ return rolloff; }

	/**
	 * Sets the rolloff value of this audio source
	 * 
	 * @param rolloff the rolloff value
	 */
	public void setRolloff(float rolloff)
	{
		this.rolloff = rolloff;
		AL11.alSourcef(this.id, AL11.AL_ROLLOFF_FACTOR, this.rolloff);

	}

	/**
	 * @return the reference distance where this source starts to get quieter
	 */
	public float getReferenceDistance()
	{ return referenceDistance; }

	/**
	 * Sets the reference distance where this source starts to get quieter.
	 * 
	 * @param referenceDistance the reference distance
	 */
	public void setReferenceDistance(float referenceDistance)
	{
		this.referenceDistance = referenceDistance;
		AL11.alSourcef(this.id, AL11.AL_REFERENCE_DISTANCE, this.referenceDistance);
	}

	/**
	 * @return the maximum distance this audio source can still be heard from
	 */
	public float getMaxDistance()
	{ return maxDistance; }

	/**
	 * Sets the max distance where this audio source can still be heard from
	 * 
	 * @param maxDistance the max distance
	 */
	public void setMaxDistance(float maxDistance)
	{
		this.maxDistance = maxDistance;
		AL11.alSourcef(this.id, AL11.AL_MAX_DISTANCE, this.maxDistance);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void free()
	{
		stop();
		AL11.alDeleteSources(this.id);
	}

	/**
	 * Plays the given {@link Audio} <code> audio </code>.<br>
	 * The {@link Audio} has to be added to the {@link AudioManager} beforehand!
	 * 
	 * @param audio the {@link Audio} to play
	 */
	public void play(Audio audio)
	{
		if (audio.bufferID == Audio.AUDIO_NOT_INITIALIZED)
		{
			SLogger.getSystemLogger().except(
					new AudioNotInitializedException("The given Audio has not been added to the AudioManager!"));
		} else
		{
			stop();
			AL11.alSourcei(id, AL11.AL_BUFFER, audio.bufferID);
			resume();
		}
	}
}
