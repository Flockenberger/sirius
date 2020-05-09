package at.flockenberger.sirius.engine.texture;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_PACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.nio.ByteBuffer;
import java.nio.file.Path;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL;

import at.flockenberger.sirius.engine.IBindable;
import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.graphic.Color;
import at.flockenberger.sirius.graphic.Image;

public class Texture extends Image implements ITextureBase, IBindable, IFreeable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7990433432552419455L;

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

	protected Texture()
	{
		super();
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Internally allocates a new pixel buffer with the size of
	 * <code> width * height * 4 </code>.<br>
	 * The pixels can then be filled/set using {@link #setPixel(int, int, Color)},
	 * {@link #setPixel(int, int, byte, byte, byte, byte)} or
	 * {@link #setRGB(int, int, int)}.
	 * 
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	public Texture(int width, int height)
	{
		super(width, height);
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * This constructor is a copy constructor. The underlying data will be copied.
	 * 
	 * @param img the image to copy
	 */
	public Texture(Image img)
	{
		super(img);
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Sets the width and height of the image as well as the raw pixel data.<br>
	 * No internal checking if width and height correspond to the given pixel data
	 * so make sure to do this beforehand.
	 * 
	 * @param width  the width of the image
	 * @param height the height of the image
	 * @param data   the pixel data of the image
	 */
	public Texture(int width, int height, ByteBuffer data)
	{
		super(width, height, data);
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Tries to load an {@link Image} from the given {@link Path}. <br>
	 * In addition the image is resized to the given width and height.
	 * 
	 * @param imagePath the path to the image to load
	 * @param reqWidth  the width the image should be resized to
	 * @param reqHeight the height the image should be resized to
	 */
	public Texture(Path imagePath, int reqWidth, int reqHeight)
	{
		super(imagePath, reqWidth, reqHeight);
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Tries to load an {@link Image} from the given {@link Path}. <br>
	 * In addition the image is resized to the given width and height.
	 * 
	 * @param imagePath the path to the image to load
	 * @param reqWidth  the width the image should be resized to
	 * @param reqHeight the height the image should be resized to
	 */
	public Texture(String imagePath, int reqWidth, int reqHeight)
	{
		super(imagePath, reqWidth, reqHeight);
		initTexture();

	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Tries to load an {@link Image} from the given {@link Path}.
	 * 
	 * @param imagePath the path to the image to load
	 */
	public Texture(Path imagePath)
	{
		super(imagePath);
		initTexture();

	}

	public Texture(int width, int height, int filter, int wrap)
	{
		super(width, height);
		initTexture(filter, wrap);
	}

	protected void initTexture(int filter, int wrap)
	{
		glEnable(getTarget());
		id = glGenTextures();
		bind();

		setFilter(filter);
		setWrap(wrap);

		upload(GL_RGBA, getPixelData());

		EXTFramebufferObject.glGenerateMipmapEXT(getTarget());
	}

	protected void initTexture()
	{
		glEnable(getTarget());
		id = glGenTextures();
		bind();

		setFilter(DEFAULT_FILTER);
		setWrap(DEFAULT_WRAP);

		upload(GL_RGBA, getPixelData());

		EXTFramebufferObject.glGenerateMipmapEXT(getTarget());
	}

	public int getTarget()
	{
		return GL_TEXTURE_2D;
	}

	protected void setUnpackAlignment()
	{
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glPixelStorei(GL_PACK_ALIGNMENT, 1);
	}

	/**
	 * Uploads image data with the dimensions of this Texture.
	 * 
	 * @param dataFormat the format, e.g. GL_RGBA
	 * @param data       the byte data
	 */
	public void upload(int dataFormat, ByteBuffer data)
	{
		bind();
		setUnpackAlignment();
		glTexImage2D(getTarget(), 0, GL_RGBA, getWidth(), getHeight(), 0, dataFormat, GL_UNSIGNED_BYTE, data);
	}

	/**
	 * Uploads a sub-image within this texture.
	 * 
	 * @param x          the destination x offset
	 * @param y          the destination y offset, with lower-left origin
	 * @param width      the width of the sub image data
	 * @param height     the height of the sub image data
	 * @param dataFormat the format of the sub image data, e.g. GL_RGBA
	 * @param data       the sub image data
	 */
	public void upload(int x, int y, int width, int height, int dataFormat, ByteBuffer data)
	{
		bind();
		setUnpackAlignment();
		glTexSubImage2D(getTarget(), 0, x, y, width, height, dataFormat, GL_UNSIGNED_BYTE, data);
	}

	public void setFilter(int filter)
	{
		setFilter(filter, filter);
	}

	public void setFilter(int minFilter, int magFilter)
	{
		bind();
		glTexParameteri(getTarget(), GL_TEXTURE_MIN_FILTER, minFilter);
		glTexParameteri(getTarget(), GL_TEXTURE_MAG_FILTER, magFilter);
	}

	public void setWrap(int wrap)
	{
		bind();
		glTexParameteri(getTarget(), GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(getTarget(), GL_TEXTURE_WRAP_T, wrap);
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
		return super.getWidth();
	}

	@Override
	public int getHeight()
	{
		return super.getHeight();
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
			glDeleteTextures(id);
			id = 0;
		}
	}

	@Override
	public void bind()
	{
		if (!valid())
			throw new IllegalStateException("trying to bind a texture that was disposed");
		glBindTexture(getTarget(), id);

	}

	@Override
	public void unbind()
	{
		glBindTexture(getTarget(), 0);

	}

	@Override
	public int getID()
	{
		return id;
	}

	public static int toPowerOfTwo(int n)
	{
		return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
	}

	public static boolean isPowerOfTwo(int n)
	{
		return (n & -n) == n;
	}

	public static boolean isNPOTSupported()
	{
		return GL.getCapabilities().GL_ARB_texture_non_power_of_two;
	}

}
