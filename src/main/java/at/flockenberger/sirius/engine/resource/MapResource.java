package at.flockenberger.sirius.engine.resource;

import java.nio.file.Path;

import org.mapeditor.core.Map;

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
	private Map map;

	public MapResource(Path location)
	{
		super(location);
	}

	@Override
	public void load()
	{

		try
		{
			map = Sirius.mapReader.readMap(resourceLocation.toString());
		} catch (Exception e)
		{
			SLogger.getSystemLogger().except(e);
		}
	}

	/**
	 * @return the loaded {@link TiledMap}
	 */
	public Map getMap()
	{
		return map;
	}
}
