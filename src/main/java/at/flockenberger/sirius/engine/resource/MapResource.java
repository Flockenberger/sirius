package at.flockenberger.sirius.engine.resource;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.stream.JsonReader;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.map.tilted.TiledMap;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>MapResource</h1><br>
 * The {@link MapResource} loads a {@link TiledMap} from an file.<br>
 * The {@link TiledMap} is the raw representation of a "Tiled" map.
 * 
 * @author Florian Wagner
 *
 */
public class MapResource extends ResourceBase
{
	private TiledMap map;

	public MapResource(Path location)
	{
		super(location);
	}

	@Override
	public void load()
	{
		JsonReader reader = null;
		byte[] b = null;
		try
		{
			b = Files.readAllBytes(resourceLocation);
		} catch (IOException e)
		{
			SLogger.getSystemLogger().except(e);
		}
		String json = new String(b);

		map = Sirius.gson.fromJson(json, TiledMap.class);
	}

	/**
	 * @return the loaded {@link TiledMap}
	 */
	public TiledMap getMap()
	{
		return map;
	}
}
