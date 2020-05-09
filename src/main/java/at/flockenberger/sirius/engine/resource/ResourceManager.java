package at.flockenberger.sirius.engine.resource;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>ResourceManage</h1><br>
 * The {@link ResourceManager} handles all loading and storing of resources that
 * can be used inside sirius.<br>
 * 
 * @author Florian Wagner
 *
 */
public class ResourceManager
{

	private Map<String, ResourceBase> res;

	private static ResourceManager GLOBAL_MANAGER;

	public static ResourceManager get()
	{
		if (GLOBAL_MANAGER == null)
			GLOBAL_MANAGER = new ResourceManager();
		return GLOBAL_MANAGER;
	}

	private ResourceManager()
	{
		res = new HashMap<String, ResourceBase>();
	}

	public URLResource loadURLResource(String name, String p)
	{
		if (getResource(name) != null)
			return (URLResource) getResource(name);

		URLResource resouce = null;
		try
		{
			resouce = new URLResource(Paths.get(ResourceManager.class.getResource(p).toURI()));
		} catch (URISyntaxException e)
		{
			SLogger.getSystemLogger().except(e);
		}
		res.put(name, resouce);
		return resouce;
	}

	/**
	 * Loads an image from the hard drive.<br>
	 * 
	 * @param name the name to store (cache) the resource in the
	 *             {@link ResourceManager}.
	 * @param p    the location of the resource on the hard drive
	 * @return a new {@link ImageResource} object with the loaded image
	 */
	public ImageResource loadImageResource(String name, String p)
	{

		if (getResource(name) != null)
			return (ImageResource) getResource(name);

		ImageResource resouce = null;
		try
		{
			resouce = new ImageResource(Paths.get(ResourceManager.class.getResource(p).toURI()));
		} catch (URISyntaxException e)
		{
			SLogger.getSystemLogger().except(e);
		}
		res.put(name, resouce);
		return resouce;
	}

	/**
	 * Retrieves a cached {@link ImageResource} from the
	 * {@link ResourceManager}.<br>
	 * 
	 * @param name the name under which the resource has been cached
	 * @return the found {@link ImageResource} or null if it has not been found
	 */
	public ResourceBase getResource(String name)
	{

		if (SUtils.checkNull(name, "String"))
			return null;

		if (res.get(name) == null)
		{
			SLogger.getSystemLogger().warn("No cached resource for '" + name + "' has been found!");
			return null;
		}

		return (ResourceBase) res.get(name);
	}

}
