package at.flockenberger.sirius.engine.graphic;

import java.nio.ByteBuffer;
import java.nio.file.Path;

/**
 * <h1>IImage</h1><br>
 * Base Interface for all images in Cardinal.<br>
 * 
 * 
 * @author Florian Wagner
 *
 */
public interface IImage
{
	/**
	 * @return the width of the image
	 */
	public int getWidth();

	/**
	 * @return the height of the image
	 */
	public int getHeight();

	/**
	 * @return the raw pixel data of the image
	 */
	public ByteBuffer getPixelData();

	/**
	 * Called to load an Image from the given path.
	 * 
	 * @param imagePath the path to the image to load
	 */
	public void load(Path imagePath);

}
