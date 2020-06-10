package at.flockenberger.sirius.audio;

import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>AudioManager</h1><br>
 * The {@link AudioManager} manages {@link Audio} objcts by assigning them to
 * OpenAl buffers.
 * 
 * @author Florian Wagner
 *
 */
public class AudioManager implements IFreeable
{

	private List<Integer> audioBuffer;
	private long device;
	private long context;

	private AudioListener listener;

	public AudioManager()
	{
		audioBuffer = new ArrayList<>();
		listener = new AudioListener();
		
		this.device = alcOpenDevice((ByteBuffer) null);
		if (device == 0)
			SLogger.getSystemLogger().except(new IllegalStateException("Failed to open the default device."));

		this.context = alcCreateContext(device, (IntBuffer) null);
		if (context == 0)
			SLogger.getSystemLogger().except(new IllegalStateException("Failed to create an OpenAL context."));

		alcSetThreadContext(context);

		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		AL.createCapabilities(deviceCaps);

	}

	public AudioListener getListener()
	{ return this.listener; }

	public int addAudio(Audio audio)
	{
		int buffer = AL11.alGenBuffers();
		audioBuffer.add(buffer);
		audio.bufferID = buffer;
		AL11.alBufferData(buffer, audio.audioFormat.getFormat(), audio.audioData, audio.sampleRate);
		return buffer;
	}

	public boolean removeAudio(Audio audio)
	{
		int buffer = audio.bufferID;
		if (buffer == Audio.AUDIO_NOT_INITIALIZED)
			return false;

		removeBuffer(buffer);
		return true;
	}

	private void removeBuffer(int buffer)
	{
		AL11.alDeleteBuffers(buffer);
		audioBuffer.remove(Integer.valueOf(buffer));
	}

	@Override
	public void free()
	{
		for (int b : audioBuffer)
			AL11.alDeleteBuffers(b);

		alcSetThreadContext(NULL);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
}
