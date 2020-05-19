package at.flockenberger.sirius.map;

import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;

public class TileSet
{

	private TextureRegion[] tiles;
	private TextureRegion[][] tt;
	private Texture texture;

	private int tileWidth;
	private int tileHeight;

	public TileSet(Texture tx, int tileWidth, int tileHeight, int tw, int th)
	{
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.texture = tx;
		tt = TextureRegion.splitTileSet(tx, tileWidth, tileHeight);
		int ww = tt.length;
		int hh = tt[0].length;

		tiles = new TextureRegion[ww * hh];
		int index = 0;

		for (int i = 0; i < hh; i++)
		{
			for (int j = 0; j< ww; j++)
			{
				tiles[index++] = tt[j][i];
			}
		}

	}

	public int getTileWidth()
	{
		return tileWidth;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}

	public TextureRegion get(int index)
	{
		if (index < 0)
			index = 0;
		if (index >= 0 && index < tiles.length)
			return tiles[index];
		return null;
	}

	public int getWidth()
	{
		return tt.length;
	}

	public TextureRegion getTT(int x, int y)
	{
		if (x >= 0 && x < tt.length)
			if (y >= 0 && y < tt[x].length)
				return tt[x][y];
		return null;
	}

	public int getTileCount()
	{
		return tiles.length;
	}
}
