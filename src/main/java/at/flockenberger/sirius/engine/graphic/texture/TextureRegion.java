package at.flockenberger.sirius.engine.graphic.texture;

import at.flockenberger.sirius.engine.graphic.Image;

public class TextureRegion implements ITextureBase
{
	protected Texture texture;
	protected UV uv;
	protected int regionWidth;
	protected int regionHeight;

	public TextureRegion()
	{
		texture = Texture.createTexture(1, 1);
		uv = new UV();
	}

	public TextureRegion(Texture texture)
	{
		this(texture, 0, 0);
	}

	public TextureRegion(Texture texture, int x, int y)
	{
		this(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	public TextureRegion(Texture texture, int x, int y, int width, int height)
	{
		set(texture, x, y, width, height);
	}

	public TextureRegion(Texture texture, float u, float v, float u2, float v2)
	{
		set(texture, u, v, u2, v2);
	}

	public TextureRegion(TextureRegion region, int x, int y, int width, int height)
	{
		set(region, x, y, width, height);
	}

	public void set(TextureRegion region)
	{
		set(region, region.getRegionX(), region.getRegionY(), region.regionWidth, region.regionHeight);

	}

	public void set(Texture texture, int x, int y, int width, int height)
	{
		set(texture, x / (float) texture.getWidth(), y / (float) texture.getHeight(),
				(x + width) / (float) texture.getWidth(), (y + height) / (float) texture.getHeight());
		regionWidth = Math.round(width);
		regionHeight = Math.round(height);
	}

	public void set(Texture texture, float u, float v, float u2, float v2)
	{
		this.texture = texture;
		this.uv = new UV(u, v, u2, v2);

		regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
		regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());
	}

	public void set(TextureRegion region, int x, int y, int width, int height)
	{
		set(region.texture, x + region.getRegionX(), y + region.getRegionY(), width, height);
	}

	public int getRegionX()
	{
		return Math.round(uv.getU1() * texture.getWidth());
	}

	public int getRegionY()
	{
		return Math.round(uv.getV1() * texture.getHeight());
	}

	public void setRegion(float u1, float v1, float u2, float v2)
	{
		uv.setU1(u1);
		uv.setV1(v1);
		uv.setU2(u2);
		uv.setV2(v2);
	}

	public UV getUv()
	{
		return uv;
	}

	public int getRegionWidth()
	{
		return regionWidth;
	}

	public int getRegionHeight()
	{
		return regionHeight;
	}

	public void flip(boolean x, boolean y)
	{
		if (x)
		{
			float temp = uv.getU1();
			uv.setU1(uv.getU2());
			uv.setU2(temp);
		}
		if (y)
		{
			float temp = uv.getV1();
			uv.setV1(uv.getV2());
			uv.setV2(temp);
		}
	}

	/**
	 * @return the texture
	 */
	public Texture getTexture()
	{
		return texture;
	}

	public Texture getRegionTexture()
	{
		return Texture.createTexture(getRegionImage());
	}

	public Image getRegionImage()
	{
		return texture.getSubImage(getRegionX(), getRegionY(), regionWidth, regionHeight);
	}

	/**
	 * Returns the width (in pixels) of this region.
	 * 
	 * @return the width of this region
	 */
	@Override
	public int getWidth()
	{
		return regionWidth;
	}

	/**
	 * Returns the height (in pixels) of this region.
	 * 
	 * @return the height of this region
	 */
	@Override
	public int getHeight()
	{
		return regionHeight;
	}

	@Override
	public UV getUV()
	{
		return this.uv;
	}

	@Override
	public int getID()
	{
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * Helper function to create tiles out of this TextureRegion starting from the
	 * top left corner going to the right and ending at the bottom right corner.
	 * Only complete tiles will be returned so if the region's width or height are
	 * not a multiple of the tile width and height not all of the region will be
	 * used. This will not work on texture regions returned form a TextureAtlas that
	 * either have whitespace removed or where flipped before the region is split.
	 * 
	 * @param tileWidth  a tile's width in pixels
	 * @param tileHeight a tile's height in pixels
	 * @return a 2D array of TextureRegions indexed by [row][column].
	 */
	public TextureRegion[][] split(int tileWidth, int tileHeight)
	{
		int x = getRegionX();
		int y = getRegionY();
		int width = regionWidth;
		int height = regionHeight;

		int rows = height / tileHeight;
		int cols = width / tileWidth;

		int startX = x;
		TextureRegion[][] tiles = new TextureRegion[rows][cols];
		for (int row = 0; row < rows; row++, y += tileHeight)
		{
			x = startX;
			for (int col = 0; col < cols; col++, x += tileWidth)
			{
				tiles[row][col] = new TextureRegion(texture, x, y, tileWidth, tileHeight);
			}
		}

		return tiles;
	}

	/**
	 * Helper function to create tiles out of the given {@link Texture} starting
	 * from the top left corner going to the right and ending at the bottom right
	 * corner. Only complete tiles will be returned so if the texture's width or
	 * height are not a multiple of the tile width and height not all of the texture
	 * will be used.
	 * 
	 * @param texture    the Texture
	 * @param tileWidth  a tile's width in pixels
	 * @param tileHeight a tile's height in pixels
	 * @return a 2D array of TextureRegions indexed by [row][column].
	 */
	public static TextureRegion[][] split(Texture texture, int tileWidth, int tileHeight)
	{
		TextureRegion region = new TextureRegion(texture);
		return region.split(tileWidth, tileHeight);
	}

}
