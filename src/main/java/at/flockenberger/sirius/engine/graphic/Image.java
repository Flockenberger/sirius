package at.flockenberger.sirius.engine.graphic;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.system.MemoryStack;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>Image</h1><br>
 * The {@link Image} class stores the raw image {@link #data} as well as the
 * {@link #width} and {@link #height}.<br>
 * An image can be loaded using the {@link #load(Path)} method. Optionally
 * through the constructor {@link #Image(int, int, ByteBuffer)} the image data
 * can be set manually. Other than that the image is a read-only class.<br>
 * <br>
 * The image data itself is stored inside a {@link ByteBuffer} as follows:<br>
 * <code> data[0] = red<br> data[1] = green<br> data[2] = blue<br> data[3] = alpha<br>
 * every pixel consumes 4 bytes. The color per pixel is <b> not </b> stored as RGBA value!</code>
 * 
 * 
 * @author Florian Wagner
 *
 */
public class Image implements IImage, Serializable, Cloneable, IFreeable
{
	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -2236129011198920689L;
	/**
	 * The width of this image.<br>
	 * If no image was loaded a default of -1 will be assigned.
	 */
	protected int width = -1;
	/**
	 * The height of this image.<br>
	 * If no image was loaded a default of -1 will be assigned.
	 */
	protected int height = -1;

	/**
	 * The raw pixel data of this image.
	 */
	protected ByteBuffer data;

	/**
	 * Writes this image to the given {@link Path} <code> out </code> in the given
	 * {@link ImageFormat}.
	 * 
	 * @param out  the path to write the file to
	 * @param type the type of image
	 * @return true if the file was saved successfully otherwise false
	 */
	public boolean write(String out, ImageFormat type)
	{
		return write(Paths.get(out), type);
	}

	/**
	 * Writes this image to the given {@link Path} <code> out </code> in the given
	 * {@link ImageFormat}.
	 * 
	 * @param out  the path to write the file to
	 * @param type the type of image
	 * @return true if the file was saved successfully otherwise false
	 */
	public boolean write(Path out, ImageFormat type)
	{
		String filename = out.toAbsolutePath().toString();

		switch (type)
		{
		case BMP:
			return STBImageWrite.stbi_write_bmp(filename, getWidth(), getHeight(), 4, data);

		case PNG:
			return STBImageWrite.stbi_write_png(filename, getWidth(), getHeight(), 4, data, 0);

		case JPG:
			return STBImageWrite.stbi_write_jpg(filename, getWidth(), getHeight(), 4, data, 100);

		case TGA:
			return STBImageWrite.stbi_write_tga(filename, getWidth(), getHeight(), 4, data);

		default:
			return STBImageWrite.stbi_write_png(filename, getWidth(), getHeight(), 4, data, 0);

		}
	}

	/**
	 * Writes the given image to the given {@link Path} <code> out </code> in the
	 * given {@link ImageFormat}.
	 * 
	 * @param img  the image to write
	 * @param out  the path to write the file to
	 * @param type the type of image
	 * @return true if the file was saved successfully otherwise false
	 */
	public static boolean write(Image img, Path out, ImageFormat format)
	{
		if (SUtils.checkNull(img, "Image"))
			return false;
		if (SUtils.checkNull(out, "Path"))
			return false;
		return img.write(out, format);
	}

	/**
	 * Loads an {@link Image} from disk.<br>
	 * Note: While a non-null {@link Image} will be returned the contents of this
	 * image may be empty upon failure of reading the given path.
	 * 
	 * @param imagePath the path to the image
	 * @return the read image
	 */
	public static Image read(Path imagePath)
	{
		SUtils.checkNull(imagePath, "Path");
		return new Image(imagePath);
	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * This constructor should only be called when you load an image using
	 * {@link #load(Path)} later on.<br>
	 * It is guaranteed that {@link #data} is never null!
	 */
	protected Image()
	{
		data = BufferUtils.createByteBuffer(0);
	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Internally allocates a new pixel buffer with the size of
	 * <code> width * height * 4 </code>.<br>
	 * The pixels can then be filled/set using {@link #setPixel(int, int, Color)},
	 * {@link #setPixel(int, int, byte, byte, byte, byte)} or
	 * {@link #setRGBA(int, int, int)}.
	 * 
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	public Image(int width, int height)
	{
		this.width = width;
		this.height = height;
		data = BufferUtils.createByteBuffer(width * height * 4);
	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * This constructor is a copy constructor. The underlying data will be copied.
	 * 
	 * @param img the image to copy
	 */
	public Image(Image img)
	{

		this.data = SUtils.clone(img.getPixelData());
		this.height = img.getHeight();
		this.width = img.getWidth();
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
	public Image(int width, int height, ByteBuffer data)
	{
		this.width = width;
		this.height = height;
		this.data = data;
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
	public Image(Path imagePath, int reqWidth, int reqHeight)
	{
		load(imagePath);
		resize(reqWidth, reqHeight);
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
	public Image(String imagePath, int reqWidth, int reqHeight)
	{
		this(Paths.get(imagePath), reqWidth, reqHeight);
	}

	/**
	 * Instantiates a new {@link Image}. <br>
	 * Tries to load an {@link Image} from the given {@link Path}.
	 * 
	 * @param imagePath the path to the image to load
	 */
	public Image(Path imagePath)
	{
		this();
		load(imagePath);
	}

	/**
	 * Resizes this image to the given width and height.
	 * 
	 * @param newWidth  the new width of this image
	 * @param newHeight the new height of this image
	 * @return this image, resized
	 */
	public Image resize(int newWidth, int newHeight)
	{
		if (newWidth > getWidth() || newHeight > getHeight())
			throw new IllegalArgumentException("New width and height must be smaller than current!");
		STBImageResize.stbir_resize_uint8(this.data, getWidth(), getHeight(), 0, this.data, newWidth, newHeight, 0, 4);
		this.width = newWidth;
		this.height = newHeight;
		return this;
	}

	/**
	 * Returns a sub region of this {@link Image}.<br>
	 * The bounds are given with <code> <x1, w, y1, h> </code>.
	 * 
	 * This methods then returns a new {@link Image} with the sub region as its
	 * image.
	 * 
	 * @param x1 the upper left x bound of the image region
	 * @param y1 the upper left y bound of the image region
	 * @param w  the width of the new image region
	 * @param h  the height of the new image region
	 * @return a new image with the sub region as its data
	 */
	public Image getSubImage(int x1, int y1, int w, int h)
	{
		SUtils.checkIndex(x1 + w, getWidth() + 1);
		SUtils.checkIndex(y1 + h, getHeight() + 1);

		int x2 = x1 + w;
		int y2 = y1 + h;
		Image image = new Image(w, h);
		for (int x = x1, xx = 0; x < x2; x++, xx++)
			for (int y = y1, yy = 0; y < y2; y++, yy++)
				image.setRGBA(xx, yy, getRGBA(x, y));
		return image;
	}

	/**
	 * @return a copy of this image
	 */
	@Override
	public Object clone()
	{
		return new Image(this);
	}

	/**
	 * Returns the pixel at the given x and y coordinate.
	 * 
	 * @param x the x coordinate of this pixel
	 * @param y the y coordinate of this pixel
	 * @return the color value of this pixel
	 */
	public Color getPixel(int x, int y)
	{
		SUtils.checkIndex(x, getWidth());
		SUtils.checkIndex(y, getHeight());
		int[] data = new int[4];
		getPixelDataRaw(x, y, data);

		return new Color(data[0], data[1], data[2], data[3]);
	}

	/**
	 * Returns the RGBA value of the pixel at the coordinates given with <x, y>.
	 * 
	 * @param x the x coordinate of this pixel
	 * @param y the y coordinate of this pixel@param x
	 * @return the RGBA value of this pixel
	 */
	public int getRGBA(int x, int y)
	{
		SUtils.checkIndex(x, getWidth());
		SUtils.checkIndex(y, getHeight());
		int[] data = new int[4];
		getPixelDataRaw(x, y, data);
		return (data[3] << 24) | (data[0] << 16) | (data[1] << 8) | data[2];
	}

	/**
	 * Retrieves the raw pixel data and stores it inside the <code> dst </code>
	 * array.<br>
	 * The destination array must be at least length 4!. The retrieved data is
	 * stored inside of the first 4 slots of the array. Any existing data is
	 * Overridden! <br>
	 * <p>
	 * Note: data is stored as follows:<br>
	 * dst[0] = red channel<br>
	 * dst[1] = green channel<br>
	 * dst[2] = blue channel<br>
	 * dst[3] = alpha channel<br>
	 * </p>
	 * 
	 * @param x   the x coordinate of the pixel to get
	 * @param y   the y coordinate of the pixel to get
	 * @param dst the destination array to store the retrieved data to
	 * 
	 */
	public void getPixelDataRaw(int x, int y, int[] dst)
	{
		if (dst.length < 4)
			SLogger.getSystemLogger()
					.except(new IllegalArgumentException("Destination array must be at least size 4!"));
		int r = this.data.get(4 * (y * getWidth() + x) + 0) & 0xFF;
		int g = this.data.get(4 * (y * getWidth() + x) + 1) & 0xFF;
		int b = this.data.get(4 * (y * getWidth() + x) + 2) & 0xFF;
		int a = this.data.get(4 * (y * getWidth() + x) + 3) & 0xFF;
		dst[0] = r;
		dst[1] = g;
		dst[2] = b;
		dst[3] = a;
	}

	/**
	 * Sets the given pixel defined with <x, y> coordinates to the given rgba color
	 * value.
	 * 
	 * @param x    the x coordinate of the pixel
	 * @param y    the y coordinate of the pixel
	 * @param rgba the rgba color value to set
	 */
	public void setRGBA(int x, int y, int rgba)
	{
		SUtils.checkIndex(x, getWidth());
		SUtils.checkIndex(y, getHeight());

		byte a = (byte) ((rgba >> 24) & 0xff);
		byte r = (byte) ((rgba >> 16) & 0xff);
		byte g = (byte) ((rgba >> 8) & 0xff);
		byte b = (byte) (rgba & 0xff);
		setPixel(x, y, r, g, b, a);
	}

	/**
	 * Sets the given pixel defined with <x, y> coordinates to the given color.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param c the color to set
	 */
	public void setPixel(int x, int y, Color c)
	{
		SUtils.checkIndex(x, getWidth());
		SUtils.checkIndex(y, getHeight());

		byte r = (byte) (c.getRed() >= 1.0 ? 255 : (c.getRed() <= 0.0 ? 0 : (int) Math.floor(c.getRed() * 255.0)));
		byte g = (byte) (c.getGreen() >= 1.0 ? 255
				: (c.getGreen() <= 0.0 ? 0 : (int) Math.floor(c.getGreen() * 255.0)));
		byte b = (byte) (c.getBlue() >= 1.0 ? 255 : (c.getBlue() <= 0.0 ? 0 : (int) Math.floor(c.getBlue() * 255.0)));
		byte a = (byte) (c.getAlpha() >= 1.0 ? 255
				: (c.getAlpha() <= 0.0 ? 0 : (int) Math.floor(c.getAlpha() * 255.0)));

		setPixel(x, y, r, g, b, a);
	}

	/**
	 * Sets the given pixel defined with <x, y> coordinates to the given color
	 * defined by <r, g, b, a>.
	 * 
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param r the red component of the color
	 * @param g the green component of the color
	 * @param b the blue component of the color
	 * @param a the alpha component of the color
	 */
	public void setPixel(int x, int y, byte r, byte g, byte b, byte a)
	{
		this.data.put(4 * (y * getWidth() + x) + 0, r);
		this.data.put(4 * (y * getWidth() + x) + 1, g);
		this.data.put(4 * (y * getWidth() + x) + 2, b);
		this.data.put(4 * (y * getWidth() + x) + 3, a);
	}

	/**
	 * Trims this image to only opaque pixels. Essentially cropping out any
	 * transparent pixels.
	 * 
	 * @return a new cropped Image
	 */
	public Image trimImage()
	{
		int width = getWidth();
		int height = getHeight();
		int top = height / 2;
		int bottom = top;
		int left = width / 2;
		int right = left;

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)

				if (getRGBA(x, y) != 0)
				{
					top = Math.min(top, y);
					bottom = Math.max(bottom, y);

					left = Math.min(left, x);
					right = Math.max(right, x);
				}

		return getSubImage(left, top, right - left, bottom - top);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth()
	{ return this.width; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight()
	{ return this.height; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ByteBuffer getPixelData()
	{

		return this.data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(Path imagePath)
	{
		if (SUtils.checkNull(imagePath, "Path"))
			return;

		String input = null;
		try
		{
			input = imagePath.toString();

			STBImage.stbi_set_flip_vertically_on_load(true);

			try (MemoryStack stack = MemoryStack.stackPush())
			{
				IntBuffer comp = stack.mallocInt(1);
				IntBuffer w = stack.mallocInt(1);
				IntBuffer h = stack.mallocInt(1);

				this.data = STBImage.stbi_load(input, w, h, comp, 4);
				if (this.data == null)
				{
					SLogger.getSystemLogger().error("Could not load image: " + input);
					return;
				}

				this.width = w.get();
				this.height = h.get();
			}

		} catch (Exception e)
		{
			SLogger.getSystemLogger().except(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "Image [width=" + width + ", height=" + height + ", dataSize: " + data.capacity() + "]";
	}

	@Override
	public void free()
	{
		SUtils.closeDirectBuffer(data);
	}

}