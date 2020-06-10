package at.flockenberger.sirius.engine.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.BufferUtils;

import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>DataResource</h1><br>
 * The {@link DataResource} class loads and stores any kind of data
 * resource.<br>
 * Meaning it loads a file byte-wise into a {@link ByteBuffer} which can be
 * retrieved using {@link #getData()}.
 * 
 * @author Florian Wagner
 *
 */
public class DataResource extends ResourceBase
{

	/**
	 * the data that is being loaded
	 */
	protected ByteBuffer data;

	public DataResource(Path location)
	{
		super(location);
	}

	@Override
	public void load()
	{
		if (Files.isReadable(resourceLocation))
		{
			try (SeekableByteChannel fc = Files.newByteChannel(resourceLocation))
			{
				data = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(data) != -1)
				{
					;
				}
			} catch (IOException e)
			{
				SLogger.getSystemLogger().except(e);
			}
		} else
		{
			try (InputStream source = DataResource.class.getClassLoader()
					.getResourceAsStream(resourceLocation.toString());
					ReadableByteChannel rbc = Channels.newChannel(source))
			{
				data = BufferUtils.createByteBuffer(1024);

				while (true)
				{
					int bytes = rbc.read(data);
					if (bytes == -1)
					{
						break;
					}
					if (data.remaining() == 0)
					{
						data = SUtils.resizeBuffer(data, data.capacity() * 3 / 2); // 50%
					}
				}
			} catch (IOException e)
			{
				SLogger.getSystemLogger().except(e);
			}
		}
		data.flip();

	}

	/**
	 * The loaded data.<br>
	 * Note: This method returns a nullpointer if the {@link #load()} method has not
	 * been called.
	 * 
	 * @return the loaded data or null
	 */
	public ByteBuffer getData()
	{ return data; }
}
