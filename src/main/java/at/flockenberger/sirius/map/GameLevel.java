package at.flockenberger.sirius.map;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.utillity.SUtils;

public class GameLevel
{

	private int mapWidth;
	private int mapHeight;
	private List<org.mapeditor.core.MapLayer> mapLayers;
	private Map map;
	// private TileSet tiles;
	private java.util.Map<Tile, Texture> tileCache;

	public GameLevel(Map map)
	{
		tileCache = new HashMap<Tile, Texture>();

		// ResourceManager rm = ResourceManager.get();
		// rm.loadImageResource(imageName, "/" + imagePath);
		// Texture tx = Texture.createTexture(rm.getImage(imageName));
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		mapLayers = map.getLayers();

		// tiles = new TileSet(tx, tileWidth, tileHeight, map.getWidth(),
		// map.getHeight());
		this.map = map;
	}

	@SuppressWarnings("deprecation")
	public void drawLevel()
	{

		for (org.mapeditor.core.MapLayer layer : mapLayers)
		{
			TileLayer l = (TileLayer) layer;

			Sirius.renderer.begin();
			for (int y = 0, yy = 0; y < l.getHeight(); y++, yy += map.getTileHeight())

				for (int x = 0, xx = 0; x < l.getWidth(); x++, xx += map.getTileWidth())
				{
					Tile t = l.getTileAt(x, y);
					if (t != null)
					{
						BufferedImage img = t.getImage();
						// int index = data[(x + y * layer.getWidth())];
						// if (index > 0)
						// index -= 1;
						// TextureRegion tex = tiles.get(index);

						Texture tex;

						if (tileCache.containsKey(t))
						{
							tex = tileCache.get(t);
						} else
						{
							tex = Texture.createTexture(img.getWidth(), img.getHeight(), SUtils.image2Buffer(img));
							tileCache.put(t, tex);
						}
						if (tex != null)
						{
							// UV uv = tex.getTexture().getUV();
							// uv.u1 = tex.getRegionX() / (float) tex.getTexture().getWidth();
							// uv.v1 = (tex.getRegionY() + tex.getRegionHeight()) / (float)
							// tex.getTexture().getHeight();
							// uv.u2 = (tex.getRegionX() + tex.getRegionWidth()) / (float)
							// tex.getTexture().getWidth();
							// uv.v2 = tex.getRegionY() / (float) tex.getTexture().getHeight();
							// float scale = 1f;

							Sirius.renderer.draw(tex, xx, yy, map.getTileWidth() / 2f, map.getTileHeight() / 2f,
									map.getTileWidth(), map.getTileHeight(), 1, 1, 0);

						}

					}
				}
			Sirius.renderer.end();

		}

	}

	public int getMapWidth()
	{
		return mapWidth;
	}

	public int getMapHeight()
	{
		return mapHeight;
	}

}
