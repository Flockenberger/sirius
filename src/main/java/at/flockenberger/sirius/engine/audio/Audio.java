package at.flockenberger.sirius.engine.audio;

import java.nio.ByteBuffer;

/**
 * </h1>Audio</h1><br>
 * Represents an audio file.<br>
 * It stores data such as the encoded audio, the audio format and the sample
 * rate.
 * 
 * @author Florian Wagner
 *
 */
public class Audio
{

	/**
	 * default id for non-initialized audio files.
	 */
	public static final int AUDIO_NOT_INITIALIZED = -1;

	/**
	 * supported audio format -> wav files
	 */
	public static final String SUPPORTED_FORMAT = "wav";
	/**
	 * the encoded audio as bytes
	 */
	protected ByteBuffer audioData;

	/**
	 * the audio format of this track (mono, stereo)
	 */
	protected AudioFormat audioFormat;

	/**
	 * the sample rate for this audio track
	 */
	protected int sampleRate;

	/**
	 * the buffer id from OpenAL.<br>
	 * If this value is -1 then this Audio has not been added to the audio manager.
	 */
	protected int bufferID = AUDIO_NOT_INITIALIZED;

	/**
	 * Creates a new {@link Audio}. <br>
	 * 
	 * @param audioData  the audio data
	 * @param format     the audio format
	 * @param sampleRate the sample rate of this audio
	 */
	public Audio(ByteBuffer audioData, AudioFormat format, int sampleRate)
	{
		this.audioData = audioData;
		this.audioFormat = format;
		this.sampleRate = sampleRate;
	}

}
