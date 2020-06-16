package at.flockenberger.sirius.engine.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.sun.media.sound.WaveFileReader;

import at.flockenberger.sirius.engine.audio.Audio;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>AudioResource</h1><br>
 * 
 * 
 * @author Florian Wagner
 */
@SuppressWarnings("restriction")
public class AudioResource extends ResourceBase
{

	private Audio audio;

	public AudioResource(InputStream location)
	{
		super(location);
	}

	public Audio getAudio()
	{ return audio; }

	@Override
	public void load()
	{

		audio = create(resourceStream);

	}

	/**
	 * Creates a Audio container from the specified url
	 * 
	 * @param path URL to file
	 * @return Audio containing data, or null if a failure occured
	 */
	private Audio create(InputStream path)
	{
		try
		{
			// due to an issue with AudioSystem.getAudioInputStream
			// and mixing unsigned and signed code
			// we will use the reader directly
			WaveFileReader wfr = new WaveFileReader();
			return create(wfr.getAudioInputStream(new BufferedInputStream(path)));
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("Unable to create from: " + path + ", " + e.getMessage());
			return null;
		}
	}

	/**
	 * Creates a Audio container from the specified stream
	 * 
	 * @param ais AudioInputStream to read from
	 * @return Audio containing data, or null if a failure occured
	 */
	private Audio create(AudioInputStream ais)
	{
		// get format of data
		AudioFormat audioformat = ais.getFormat();

		// get channels
		at.flockenberger.sirius.engine.audio.AudioFormat channels = at.flockenberger.sirius.engine.audio.AudioFormat.STEREO16;
		if (audioformat.getChannels() == 1)
		{
			if (audioformat.getSampleSizeInBits() == 8)
			{
				channels = at.flockenberger.sirius.engine.audio.AudioFormat.MONO8;
			} else if (audioformat.getSampleSizeInBits() == 16)
			{
				channels = at.flockenberger.sirius.engine.audio.AudioFormat.MONO16;
			} else
			{
				assert false : "Illegal sample size";
			}
		} else if (audioformat.getChannels() == 2)
		{
			if (audioformat.getSampleSizeInBits() == 8)
			{
				channels = at.flockenberger.sirius.engine.audio.AudioFormat.STEREO8;
			} else if (audioformat.getSampleSizeInBits() == 16)
			{
				channels = at.flockenberger.sirius.engine.audio.AudioFormat.STEREO16;
			} else
			{
				assert false : "Illegal sample size";
			}
		} else
		{
			assert false : "Only mono or stereo is supported";
		}

		// read data into buffer
		ByteBuffer buffer = null;
		try
		{
			int available = ais.available();
			if (available <= 0)
			{
				available = ais.getFormat().getChannels() * (int) ais.getFrameLength()
						* ais.getFormat().getSampleSizeInBits() / 8;
			}
			byte[] buf = new byte[ais.available()];
			int read = 0, total = 0;
			while ((read = ais.read(buf, total, buf.length - total)) != -1 && total < buf.length)
			{
				total += read;
			}
			buffer = convertAudioBytes(buf, audioformat.getSampleSizeInBits() == 16,
					audioformat.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
		} catch (IOException ioe)
		{
			return null;
		}

		// create our result
		Audio Audio = new Audio(buffer, channels, (int) audioformat.getSampleRate());

		// close stream
		try
		{
			ais.close();
		} catch (IOException ioe)
		{
		}

		return Audio;
	}

	private ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order)
	{
		ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
		dest.order(ByteOrder.nativeOrder());
		ByteBuffer src = ByteBuffer.wrap(audio_bytes);
		src.order(order);
		if (two_bytes_data)
		{
			ShortBuffer dest_short = dest.asShortBuffer();
			ShortBuffer src_short = src.asShortBuffer();
			while (src_short.hasRemaining())
				dest_short.put(src_short.get());
		} else
		{
			while (src.hasRemaining())
				dest.put(src.get());
		}
		dest.rewind();
		return dest;
	}
}
