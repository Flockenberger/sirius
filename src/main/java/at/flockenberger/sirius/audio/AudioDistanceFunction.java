package at.flockenberger.sirius.audio;

import org.lwjgl.openal.AL11;

public enum AudioDistanceFunction
{
	/**
	 * exponential distance function
	 */
	EXPONENT(AL11.AL_EXPONENT_DISTANCE),

	/**
	 * clamped exponential distance function
	 */
	EXPONENT_CLAMPED(AL11.AL_EXPONENT_DISTANCE_CLAMPED),

	/**
	 * inverse distance function
	 */
	INVERSE(AL11.AL_INVERSE_DISTANCE),

	/**
	 * clamped inverse distance function
	 */
	INVERSE_CLAMPED(AL11.AL_INVERSE_DISTANCE_CLAMPED),

	/**
	 * linear distance function
	 */
	LINEAR(AL11.AL_LINEAR_DISTANCE),

	/**
	 * clamped linear distance function
	 */
	LINEAR_CLAMPED(AL11.AL_LINEAR_DISTANCE_CLAMPED);

	/**
	 * the int value id
	 */
	private final int _id;

	private AudioDistanceFunction(int id)
	{
		this._id = id;

	}

	/**
	 * @return the distance function id for OpenAL
	 */
	public int getDF()
	{ return this._id; }
}
