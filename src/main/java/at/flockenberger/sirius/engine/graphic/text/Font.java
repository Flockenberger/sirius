package at.flockenberger.sirius.engine.graphic.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.SpriteBatch;
import at.flockenberger.sirius.engine.texture.Texture;
import at.flockenberger.sirius.engine.texture.TextureRegion;

public class Font implements IFreeable
{

	// TODO: fix up baseLine, ascent, descent, etc.
	int lineHeight;
	int baseLine;
	int descent;
	int pages;
	Glyph[] glyphs;
	TextureRegion[] texturePages;

	public Font(URL fontDef, URL texture) throws IOException, URISyntaxException {
			this(fontDef, new Texture(Paths.get(texture.toURI())));
		}

	public Font(URL fontDef, Texture texture) throws IOException {
			this(fontDef.openStream(), new TextureRegion(texture));
		}

	public Font(URL fontDef, TextureRegion texture) throws IOException {
			this(fontDef.openStream(), texture);
		}

	public Font(InputStream fontDef, TextureRegion texture) throws IOException {
			this(fontDef, new TextureRegion[] { texture });
		}

	public Font(InputStream fontDef, TextureRegion[] texturePages) throws IOException {
			this(fontDef, Charset.defaultCharset(), texturePages);
		}

	public Font(InputStream fontDef, Charset charset, TextureRegion[] texturePages) throws IOException {
			this.texturePages = texturePages;
			parseFont(fontDef, charset);
		}

	public int getLineHeight()
	{
		return lineHeight;
	}

	public TextureRegion[] getTexturePages()
	{
		return texturePages;
	}	
	
	
	public void drawText(SpriteBatch batch, CharSequence text, int x, int y)
	{
		drawText(batch, text, x, y, 0, text.length());
	}

	public void drawText(SpriteBatch batch, CharSequence text, int x, int y, int start, int end)
	{
		Glyph lastGlyph = null;
		for (int i = start; i < end; i++)
		{
			char c = text.charAt(i);
			// TODO: make unsupported glyphs a bit cleaner...
			if (c > glyphs.length || c < 0)
				continue;
			Glyph g = glyphs[c];
			if (g == null)
				continue;

			if (lastGlyph != null)
				x += lastGlyph.getKerning(c);

			lastGlyph = g;
			batch.draw(g.region, x + g.offX, y + g.offY, g.width, g.height);
			x += g.advX;
		}
	}
	
	
	
	public int getBaseline()
	{
		return baseLine;
	}

	public int getWidth(CharSequence text)
	{
		return getWidth(text, 0, text.length());
	}

	public int getWidth(CharSequence text, int start, int end)
	{
		Glyph lastGlyph = null;
		int width = 0;
		for (int i = start; i < end; i++)
		{
			char c = text.charAt(i);
			// TODO: make unsupported glyphs a bit cleaner...
			if (c > glyphs.length || c < 0)
				continue;
			Glyph g = glyphs[c];
			if (g == null)
				continue;

			if (lastGlyph != null)
				width += lastGlyph.getKerning(c);

			lastGlyph = g;
			// width += g.width + g.xoffset;

			// width += g.width + g.xoffset;
			width += g.advX;
		}
		return width;
	}

	private static String parse(String line, String tag)
	{
		tag += "=";
		int start = line.indexOf(tag);
		if (start == -1)
			return "";
		int end = line.indexOf(' ', start + tag.length());
		if (end == -1)
			end = line.length();
		return line.substring(start + tag.length(), end);
	}

	private static int parseInt(String line, String tag) throws IOException
	{
		try
		{
			return Integer.parseInt(parse(line, tag));
		} catch (NumberFormatException e)
		{
			throw new IOException("data for " + tag + " is corrupt/missing: " + parse(line, tag));
		}
	}

	protected void parseFont(InputStream fontFile, Charset charset) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(fontFile, charset), 512);
		String info = br.readLine();
		String common = br.readLine();
		lineHeight = parseInt(common, "lineHeight");
		baseLine = parseInt(common, "base");
		pages = parseInt(common, "pages");

		// ignore file name, let user specify texture ...

		String line = "";

		ArrayList<Glyph> glyphsList = null;

		int maxCodePoint = 0;

		while (true)
		{
			line = br.readLine();
			if (line == null)
				break;
			if (line.startsWith("chars"))
			{
				// System.out.println(line);
				int count = parseInt(line, "count");
				glyphsList = new ArrayList<Glyph>(count);
				continue;
			}
			if (line.startsWith("kernings "))
				break;
			if (!line.startsWith("char "))
				continue;

			Glyph glyph = new Glyph();

			StringTokenizer tokens = new StringTokenizer(line, " =");
			tokens.nextToken();
			tokens.nextToken();
			int ch = Integer.parseInt(tokens.nextToken());
			if (ch > Character.MAX_VALUE)
				continue;
			if (glyphsList == null) // incase some doofus deleted a line in the font def
				glyphsList = new ArrayList<Glyph>();
			glyphsList.add(glyph);
			glyph.character = ch;
			if (ch > maxCodePoint)
				maxCodePoint = ch;
			tokens.nextToken();
			glyph.x = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.y = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.width = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.height = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.offX = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.offY = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			glyph.advX = Integer.parseInt(tokens.nextToken());
			// page ID
			tokens.nextToken();
			glyph.page = Integer.parseInt(tokens.nextToken());

			if (glyph.page > texturePages.length)
				throw new IOException(
						"not enough texturePages supplied; glyph " + glyph.character + " expects page index " + glyph.page);
			glyph.updateRegion(texturePages[glyph.page]);

			if (glyph.width > 0 && glyph.height > 0)
				descent = Math.min(baseLine + glyph.offY, descent);
		}

		glyphs = new Glyph[maxCodePoint + 1];
		for (Glyph g : glyphsList)
			glyphs[g.character] = g;

		int kernCount = parseInt(line, "count");
		while (true)
		{
			line = br.readLine();
			if (line == null)
				break;
			if (!line.startsWith("kerning "))
				break;

			StringTokenizer tokens = new StringTokenizer(line, " =");
			tokens.nextToken();
			tokens.nextToken();
			int first = Integer.parseInt(tokens.nextToken());
			tokens.nextToken();
			int second = Integer.parseInt(tokens.nextToken());
			if (first < 0 || first > Character.MAX_VALUE || second < 0 || second > Character.MAX_VALUE)
				continue;

			Glyph glyph = glyphs[first];
			tokens.nextToken();
			int offset = Integer.parseInt(tokens.nextToken());

			if (glyph.kerning == null)
			{
				glyph.kerning = new int[maxCodePoint + 1];
			}
			glyph.kerning[second] = offset;

		}
		try
		{
			fontFile.close();
			br.close();
		} catch (IOException e)
		{
			// silent
		}
	}

	@Override
	public void free()
	{
		for (TextureRegion t : getTexturePages())
			t.getTexture().free();
	}

}
