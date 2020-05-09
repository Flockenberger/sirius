package at.flockenberger.sirius.engine.graphic;

import java.nio.file.Path;

import org.lwjgl.glfw.GLFWImage;

import at.flockenberger.sirius.engine.Window;

/**
 * <h1>WindowIcon</1h><br>
 * The {@link WindowIcon} class represents an icon for any window.<br>
 * The {@link WindowIcon} extends the {@link Image} class and all its features.
 * 
 * <br>
 * Note:<br>
 * When modifying the image the icon will not be updated automatically! To force
 * an update you have to call the {@link #update()} method and reset the icon of
 * the window using the {@link Window#setIcon(WindowIcon)} method-
 * 
 * @author Florian Wagner
 * @see Image
 * @see Window
 */
public class WindowIcon extends Image {

	private static final long serialVersionUID = -421500034550411938L;
	private GLFWImage.Buffer images;

	/**
	 * Creates a new WindowIcon.<br>
	 * The Path must be a valid path to an image file.
	 * 
	 * @param imagePath a path to a valid image
	 */
	public WindowIcon(Path imagePath) {
		super(imagePath);
		update();
	}
	
	/**
	 * Creates a new WindowIcon.<br>
	 * 
	 * @param img an image to (copy) use as this icon
	 */
	public WindowIcon(Image img) {
		super(img);
		update();
	}

	/**
	 * This method must be called to update the icon after changes have been made to
	 * the image itself.<br>
	 */
	public void update() {
		GLFWImage image = GLFWImage.malloc();
		image.set(getWidth(), getHeight(), getPixelData());
		images = GLFWImage.malloc(1);
		images.put(0, image);
	}

	/**
	 * @return a {@link Buffer} object that GLFW can read.
	 */
	public GLFWImage.Buffer getIcon() {
		return images;
	}

}
