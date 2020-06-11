package at.flockenberger.sirius.engine.resource;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import at.flockenberger.sirius.audio.Audio;
import at.flockenberger.sirius.audio.AudioManager;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Image;
import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.exceptions.AudioNotSupportedException;
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

	public DataResource loadDataResource(String name, String p)
	{
		if (getResource(name) != null)
			return (DataResource) getResource(name);

		DataResource resouce = null;
		try
		{
			resouce = new DataResource(Paths.get(ResourceManager.class.getResource(p).toURI()));
		} catch (URISyntaxException e)
		{
			SLogger.getSystemLogger().except(e);
		}
		res.put(name, resouce);
		return resouce;
	}

	/**
	 * Loads an {@link AudioResource} from the disk.<br>
	 * This method automatically adds the loaded audio to the {@link AudioManager}.
	 * 
	 * @param name the cache name for this resource
	 * @param p    the path for this resource
	 * @return an already cached {@link AudioResource} (if the name was already
	 *         cached) or a newly allocated one
	 */
	public AudioResource loadAudioResource(String name, String p)
	{
		if (getResource(name) != null)
			return (AudioResource) getResource(name);

		Optional<String> ext = SUtils.getFileExtention(p);
		if (ext.get() != null)
		{
			if (ext.get().equalsIgnoreCase(Audio.SUPPORTED_FORMAT))
			{
				AudioResource resouce = null;
				try
				{
					resouce = new AudioResource(Paths.get(ResourceManager.class.getResource(p).toURI()));
				} catch (URISyntaxException e)
				{
					SLogger.getSystemLogger().except(e);
				}
				res.put(name, resouce);
				Sirius.audioManager.addAudio(resouce.getAudio());

				return resouce;
			} else
			{
				SLogger.getSystemLogger().except(new AudioNotSupportedException(
						"The given audio extension: " + ext.get() + " is not supported!"));
				return null;
			}
		} else
			return null;

	}

	public MapResource loadMapResource(String name, String p)
	{
		if (getResource(name) != null)
			return (MapResource) getResource(name);

		MapResource resouce = null;
		try
		{
			resouce = new MapResource(Paths.get(ResourceManager.class.getResource(p).toURI()));
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

	public ShaderResource loadShaderResource(String name, String path)
	{
		if (getResource(name) != null)
			return (ShaderResource) getResource(name);

		ShaderResource resouce = null;
		try
		{
			resouce = new ShaderResource(Paths.get(ResourceManager.class.getResource(path).toURI()));
		} catch (URISyntaxException e)
		{
			SLogger.getSystemLogger().except(e);
		}

		res.put(name, resouce);
		return resouce;
	}

	/**
	 * Returns an {@link Image} which has already been loaded and cached.<br>
	 * If the {@link Image} was not previously loaded it will throw an error.
	 * 
	 * @param cache the cached resource name
	 * @return the found image or null
	 */
	public Image getImage(String cache)
	{
		ResourceBase res = getResource(cache);
		if (res == null)
		{
			SLogger.getSystemLogger().warn("Image " + cache + " was not found!");
			return null;
		}

		if (!(res instanceof ImageResource))
		{
			SLogger.getSystemLogger().warn("Found Resource is not an ImageResource!");
		}

		return ((ImageResource) res).getImage();
	}

	/**
	 * Returns an {@link ByteBuffer} which has already been loaded and cached.<br>
	 * If the {@link ByteBuffer} was not previously loaded it will throw an error.
	 * 
	 * @param cache the cached resource name
	 * @return the found ByteBuffer or null
	 */
	public ByteBuffer getData(String cache)
	{
		ResourceBase res = getResource(cache);
		if (res == null)
		{
			SLogger.getSystemLogger().warn("DataResource " + cache + " was not found!");
			return null;
		}

		if (!(res instanceof DataResource))
		{
			SLogger.getSystemLogger().warn("Found Resource is not an DataResource!");
		}

		return ((DataResource) res).getData();
	}

	/**
	 * Returns an {@link VorbisTrack} which has already been loaded and cached.<br>
	 * If the {@link VorbisTrack} was not previously loaded it will throw an error.
	 * 
	 * @param cache the cached resource name
	 * @return the found audio or null
	 */
	public Audio getAudio(String cache)
	{
		ResourceBase res = getResource(cache);
		if (res == null)
		{
			SLogger.getSystemLogger().warn("AudioResource " + cache + " was not found!");
			return null;
		}

		if (!(res instanceof AudioResource))
		{
			SLogger.getSystemLogger().warn("Found Resource is not an AudioResource!");
		}

		return ((AudioResource) res).getAudio();
	}

	/**
	 * Returns an {@link org.mapeditor.core.Map} which has already been loaded and
	 * cached.<br>
	 * If the {@link org.mapeditor.core.Map} was not previously loaded it will throw
	 * an error.
	 * 
	 * @param cache the cached resource name
	 * @return the found map or null
	 */
	public org.mapeditor.core.Map getMap(String cache)
	{
		ResourceBase res = getResource(cache);
		if (res == null)
		{
			SLogger.getSystemLogger().warn("Map " + cache + " was not found!");
			return null;
		}

		if (!(res instanceof MapResource))
		{
			SLogger.getSystemLogger().warn("Found Resource is not an MapResource!");
		}

		return ((MapResource) res).getMap();
	}

	/**
	 * Returns an {@link URL} which has been previously loaded and cached using
	 * {@link #loadURLResource(String, String)}.
	 * 
	 * @param cache the string under which the resource has been loaded
	 * @return the found {@link URL} or null
	 * @throws MalformedURLException
	 */
	public URL getURL(String cache) throws MalformedURLException
	{
		ResourceBase res = getResource(cache);
		if (res == null)
		{
			SLogger.getSystemLogger().warn("URL " + cache + " was not found!");
			return null;
		}

		if (!(res instanceof URLResource))
		{
			SLogger.getSystemLogger().warn("Found Resource is not an URLResource!");
		}

		return ((URLResource) res).getURL();
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
