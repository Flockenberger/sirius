package at.flockenberger.sirius.engine.graphic;

import java.nio.file.Path;

import org.lwjgl.glfw.GLFWImage;
import at.flockenberger.sirius.engine.Window;

/**
 * <h1>Icon</1h><br>
 * The {@link Icon} class represents an icon for any window.<br>
 * It is also the super class for the mouse {@link Cursor}. The {@link Icon}
 * extends the {@link Image} class and all its features.
 * 
 * <br>
 * Note:<br>
 * When modifying the image the icon will not be updated automatically! To force
 * an update you have to call the {@link #update()} method and reset the icon of
 * the window using the {@link Window#setIcon(Icon)} method-
 * 
 * @author Florian Wagner
 * @see Image
 * @see Window
 */
public class Icon extends Image
{

	private static final long serialVersionUID = -421500034550411938L;
	protected GLFWImage.Buffer glfwImageBuffer;
	protected GLFWImage glfwImage;

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
		super(imagePath);
		update();
	}

	/**
	 * Creates a new WindowIcon.<br>
	 * 
	 * @param img an image to (copy) use as this icon
	 */
	public Icon(Image img)
	{
		super(img);
		update();
	}

	/**
	 * This method must be called to update the icon after changes have been made to
	 * the image itself.<br>
	 */
	public void update()
	{
		glfwImage = GLFWImage.malloc();
		glfwImage.set(getWidth(), getHeight(), getPixelData());
		glfwImageBuffer = GLFWImage.malloc(1);
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
		super.free();
		glfwImageBuffer.free();
		glfwImage.free();
	}

}
