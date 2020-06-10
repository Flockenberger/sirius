package at.flockenberger.sirius.engine.resource;

import java.nio.file.Path;

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
	protected Path resourceLocation;

	/**
	 * Creates a new ResourceBase.<br>
	 * This constructor automatically calls the {@link #load()} method to load the
	 * resource from the given location.
	 * 
	 * @param location the location of the resource on the hard drive
	 */
	public ResourceBase(Path location)
	{
		this.resourceLocation = location;
		load();
	}

	/**
	 * Loads the resource from the location.<br>
	 * It is up to the child classes and their implementaiton on how they handle
	 * loading!<br>
	 */
	public abstract void load();

}
