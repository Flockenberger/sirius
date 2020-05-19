package at.flockenberger.sirius.map;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.graphic.texture.UV;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.map.tilted.MapLayer;
import at.flockenberger.sirius.map.tilted.MapTileSet;
import at.flockenberger.sirius.map.tilted.TiledMap;

public class GameLevel
{
	private TileSet tiles;
	private int mapWidth;
	private int mapHeight;
	private MapLayer[] mapLayers;

	public GameLevel(TiledMap map)
	{
		int tileWidth = map.getTilewidth();
		int tileHeight = map.getTileheight();
		MapTileSet tileSet = map.getTilesets()[0];
		String imagePath = tileSet.getImage();
		String imageName = tileSet.getName();
		ResourceManager rm = ResourceManager.get();
		rm.loadImageResource(imageName, "/" + imagePath);

		Texture tx = Texture.createTexture(rm.getImage(imageName));
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		mapLayers = map.getLayers();
		tiles = new TileSet(tx, tileWidth, tileHeight, mapLayers[0].getWidth(), mapLayers[0].getHeight());

	}

	public TileSet getTileSet()
	{
		return tiles;
	}

	public void drawLevel()
	{

		for (MapLayer layer : mapLayers)
		{

			int[] data = layer.getData();
			Sirius.renderer.begin();
			for (int y = 0, yy = 0; y < layer.getHeight(); y++, yy += tiles.getTileHeight())

				for (int x = 0, xx = 0; x < layer.getWidth(); x++, xx += tiles.getTileWidth())
				{

					int index = data[(x + y * layer.getWidth())];
					if (index > 0)
						index -= 1;
					TextureRegion tex = tiles.get(index);

					if (tex != null)
					{
						UV uv = tex.getTexture().getUV();
						uv.u1 = tex.getRegionX() / (float) tex.getTexture().getWidth();
						uv.v1 = (tex.getRegionY() + tex.getRegionHeight()) / (float) tex.getTexture().getHeight();
						uv.u2 = (tex.getRegionX() + tex.getRegionWidth()) / (float) tex.getTexture().getWidth();
						uv.v2 = tex.getRegionY() / (float) tex.getTexture().getHeight();
						float scale = 1f;

						Sirius.renderer.draw(tex, xx, yy, tiles.getTileWidth() / 2f, tiles.getTileHeight() / 2f,
								tiles.getTileWidth(), tiles.getTileHeight(), scale, scale, 0);
						// Sirius.renderer.drawTextureRegion(tex, xx, yy);
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
