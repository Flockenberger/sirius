package at.flockenberger.sirius.graphic.text;

import at.flockenberger.sirius.engine.texture.TextureRegion;

/**
 * <h1>Glyph</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class Glyph
{
	protected int character;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int offY;
	protected int offX;
	protected int advX;
	protected int[] kerning;
	protected int page;
	protected TextureRegion region;

	public Glyph()
	{

	}

	/**
	 * Get the kerning offset between this character and the specified character.
	 * 
	 * @param c The other code point
	 * @return the kerning offset
	 */
	public int getKerning(int c)
	{
		if (kerning == null)
			return 0;
		return kerning[c];
	}

	/**
	 * Updates the texture region.
	 * 
	 * @param tex the texture region
	 */
	protected void updateRegion(TextureRegion tex)
	{
		if (region == null)
			region = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
		region.set(tex, x, y, width, height);
	}

}
