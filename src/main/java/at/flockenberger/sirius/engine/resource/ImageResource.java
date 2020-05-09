package at.flockenberger.sirius.engine.resource;

import java.nio.file.Path;

import at.flockenberger.sirius.engine.graphic.Image;

/**
 * </h1>ImageResource</h1><br>
 * The ImageResource class holds a loaded image which has been loaded into
 * sirius.<br>
 *
 * 
 * @author Florian Wagner
 * @see ResourceManager
 * @see Image
 */
public class ImageResource extends ResourceBase {
	private Image tex;

	/**
	 * Creates a new ImageResource<br>
	 * 
	 * @param location The location of the resource on the hard drive
	 */
	protected ImageResource(Path location) {
		super(location);
	}

	/**
	 * @return the loaded image
	 */
	public Image getImage() {
		return tex;
	}

	@Override
	public void load() {
		tex = new Image(resourceLocation);
	}
}
