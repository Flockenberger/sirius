package at.flockenberger.sirius.engine.graphic;

import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import org.lwjgl.glfw.GLFWImage;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Icon</1h><br>
 * The {@link Icon} class represents an icon for any window.<br>
 * It is also the super class for the mouse {@link Cursor}.
 * 
 * 
 * @author Florian Wagner
 * @see Image
 * @see Window
 */
public class Icon implements Serializable, IFreeable
{

	private static final long serialVersionUID = 198567101908440954L;
	protected GLFWImage.Buffer glfwImageBuffer;
	protected GLFWImage glfwImage;
	protected int width;
	protected int height;
	protected ByteBuffer buffer;

	protected Icon()
	{

	}

	/**
	 * Creates a new WindowIcon.<br>
	 * The Path must be a valid path to an image file.
	 * 
	 * @param imagePath a path to a valid image
	 */
	public Icon(Path imagePath)
	{
		Image img = Image.read(imagePath);
		SUtils.checkNull(img, "Image");

		this.width = img.getWidth();
		this.height = img.getHeight();
		this.buffer = img.getPixelData();

		update();
	}

	public Icon(Icon cpy)
	{
		this.width = cpy.width;
		this.height = cpy.height;
		this.buffer = cpy.buffer;
	}

	/**
	 * Creates a new WindowIcon.<br>
	 * 
	 * @param img an image to (copy) use as this icon
	 */
	public Icon(Image img)
	{
		SUtils.checkNull(img, "Image");
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.buffer = img.getPixelData();

		update();
	}

	public Icon set(Image img)
	{
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.buffer = img.getPixelData();
		update();
		return this;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * This method must be called to update the icon after changes have been made to
	 * the image itself.<br>
	 */
	public void update()
	{
		if (glfwImage == null)
			glfwImage = GLFWImage.malloc();
		
		glfwImage.set(getWidth(), getHeight(), buffer);
		
		if (glfwImageBuffer == null)
		{
			glfwImageBuffer = GLFWImage.malloc(1);
		}
		glfwImageBuffer.put(0, glfwImage);
		
	}

	/**
	 * @return a {@link Buffer} object that GLFW can read.
	 */
	public GLFWImage.Buffer getIcon()
	{
		return glfwImageBuffer;
	}

	@Override
	public void free()
	{
		glfwImageBuffer.free();
		glfwImage.free();
	}

}
