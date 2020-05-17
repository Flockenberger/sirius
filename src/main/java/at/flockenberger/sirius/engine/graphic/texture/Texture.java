package at.flockenberger.sirius.engine.graphic.texture;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import at.flockenberger.sirius.engine.IBindable;
import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.graphic.Image;

public class Texture extends Image implements ITextureBase, IBindable, IFreeable
{

	private int id;
	private UV uv = new UV();

	public static final int LINEAR = GL_LINEAR;
	public static final int NEAREST = GL_NEAREST;
	public static final int LINEAR_MIPMAP_LINEAR = GL_LINEAR_MIPMAP_LINEAR;
	public static final int LINEAR_MIPMAP_NEAREST = GL_LINEAR_MIPMAP_NEAREST;
	public static final int NEAREST_MIPMAP_NEAREST = GL_NEAREST_MIPMAP_NEAREST;
	public static final int NEAREST_MIPMAP_LINEAR = GL_NEAREST_MIPMAP_LINEAR;
	public static final int CLAMP = GL_CLAMP;
	public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
	public static final int REPEAT = GL_REPEAT;
	public static final int DEFAULT_FILTER = NEAREST;
	public static final int DEFAULT_WRAP = REPEAT;

	/** Creates a texture. */
	private Texture()
	{
		id = glGenTextures();
	}

	/**
	 * Creates a texture with specified width, height and data.
	 * 
	 * @param img the image to load into this texture.
	 * @return a new Texture fromm the specified image
	 */
	public static Texture createTexture(Image img)
	{
		return createTexture(img.getWidth(), img.getHeight(), img.getPixelData());
	}

	/**
	 * Creates a texture with specified width, height and data.
	 *
	 * @param width  Width of the texture
	 * @param height Height of the texture
	 * @param data   Picture Data in RGBA format
	 *
	 * @return Texture from the specified data
	 */
	public static Texture createTexture(int width, int height, ByteBuffer data)
	{
		Texture texture = new Texture();
		texture.setWidth(width);
		texture.setHeight(height);
		texture.data = data;

		texture.bind();

		texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

		return texture;
	}

	public static Texture createTexture(int i, int j)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(i * j);
		return createTexture(i, j, buffer);
	}

	/**
	 * Sets a parameter of the texture.
	 *
	 * @param name  Name of the parameter
	 * @param value Value to set
	 */
	public void setParameter(int name, int value)
	{
		glTexParameteri(GL_TEXTURE_2D, name, value);
	}

	/**
	 * Uploads image data with specified width and height.
	 *
	 * @param width  Width of the image
	 * @param height Height of the image
	 * @param data   Pixel data of the image
	 */
	public void uploadData(int width, int height, ByteBuffer data)
	{
		uploadData(GL_RGBA8, width, height, GL_RGBA, data);
	}

	/**
	 * Uploads image data with specified internal format, width, height and image
	 * format.
	 *
	 * @param internalFormat Internal format of the image data
	 * @param width          Width of the image
	 * @param height         Height of the image
	 * @param format         Format of the image data
	 * @param data           Pixel data of the image
	 */
	public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data)
	{
		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
	}

	/**
	 * Returns true if this texture is valid, aka it has not been disposed.
	 * 
	 * @return true if id!=0
	 */
	public boolean valid()
	{
		return id != 0;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	/**
	 * Sets the texture width.
	 *
	 * @param width The width to set
	 */
	public void setWidth(int width)
	{
		if (width > 0)
		{
			this.width = width;
		}
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the texture height.
	 *
	 * @param height The height to set
	 */
	public void setHeight(int height)
	{
		if (height > 0)
		{
			this.height = height;
		}
	}

	@Override
	public UV getUV()
	{
		return uv;
	}

	@Override
	public Texture getTexture()
	{
		return this;
	}

	@Override
	public void free()
	{
		if (valid())
		{
			unbind();
			glDeleteTextures(id);
			id = 0;
		}
	}

	@Override
	public void bind()
	{
		if (!valid())
			throw new IllegalStateException("trying to bind a texture that was disposed");
		glBindTexture(GL_TEXTURE_2D, id);

	}

	@Override
	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);

	}

	@Override
	public int getID()
	{
		return id;
	}

	public void setUV(UV uv2)
	{
		this.uv = uv2;
	}

}
