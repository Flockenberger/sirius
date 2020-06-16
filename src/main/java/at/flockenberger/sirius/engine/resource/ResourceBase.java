package at.flockenberger.sirius.engine.resource;

import java.io.InputStream;

/**
 * </h1>ResourceBase</h1><br>
 * The base class for all resources this engine needs.<br>
 * 
 * 
 * @author Florian Wagner
 *
 */
public abstract class ResourceBase
{

	/**
	 * the path from the resource to load
	 */
	protected InputStream resourceStream;

	/**
	 * Creates a new ResourceBase.<br>
	 * This constructor automatically calls the {@link #load()} method to load the
	 * resource from the given location.
	 * 
	 * @param location the location of the resource on the hard drive
	 */
	public ResourceBase(InputStream location)
	{
		this.resourceStream = location;

		// null check, only load resource if we can actually find it
		if (this.resourceStream != null)
			load();

	}

	/**
	 * Loads the resource from the location.<br>
	 * It is up to the child classes and their implementaiton on how they handle
	 * loading!<br>
	 */
	public abstract void load();

}
