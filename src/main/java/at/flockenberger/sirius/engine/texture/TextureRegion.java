package at.flockenberger.sirius.engine.texture;

public class TextureRegion implements ITextureBase
{
	protected Texture texture;
	protected UV uv;
	protected int regionWidth;
	protected int regionHeight;

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

	public void flip(boolean x, boolean y)
	{
		if (x)
		{
			float temp = uv.getU1();
			uv.setU1(uv.getU2());
			;
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
}
