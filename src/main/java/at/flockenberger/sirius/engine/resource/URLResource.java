package at.flockenberger.sirius.engine.resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class URLResource extends ResourceBase
{

	/**
	 * Creates a new ImageResource<br>
	 * 
	 * @param location The location of the resource on the hard drive
	 */
	protected URLResource(Path location)
	{
		super(location);
	}

	public URL getURL() throws MalformedURLException
	{
		return this.resourceLocation.toUri().toURL();
	}

	@Override
	public void load()
	{

	}
}
