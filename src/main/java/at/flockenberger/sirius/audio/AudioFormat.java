package at.flockenberger.sirius.audio;

/**
 * <h1>AudioFormat</h1><br>
 * The {@link AudioFormat} to describe how many channels (mono, stereo) are
 * needed.
 * 
 * @author Florian Wagner
 *
 */
public enum AudioFormat
{
	/**
	 * Mono8 audio format
	 */
	MONO8(0x1100),

	/**
	 * Mono16 audio format
	 */
	MONO16(0x1101),

	/**
	 * Stereo8 audio format
	 */
	STEREO8(0x1102),

	/**
	 * Stereo16 audio format
	 */
	STEREO16(0x1103);

	private int _format;

	AudioFormat(int _f)
	{
		this._format = _f;
	}

	/*
	 * @return the int value for this audio format to be used by OpenAL
	 */
	public int getFormat()
	{ return this._format; }
}
