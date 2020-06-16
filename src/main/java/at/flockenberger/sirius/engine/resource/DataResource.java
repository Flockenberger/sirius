package at.flockenberger.sirius.engine.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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

	public DataResource(InputStream location)
	{
		super(location);
	}

	@Override
	public void load()
	{
		try
		{
			this.data = SUtils.IO.streamAsByteBuffer(resourceStream, 1024);
		} catch (IOException e)
		{
			SLogger.getSystemLogger().except(e);
		}
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
