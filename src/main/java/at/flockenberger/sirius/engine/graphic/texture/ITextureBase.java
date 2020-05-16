package at.flockenberger.sirius.engine.graphic.texture;

/**
 * <h1>ITextureBase</1h><br>
 * Base-Interface for all textures of different kind and sub-classes.
 * 
 * @author Florian Wagner
 *
 */
public interface ITextureBase
{
	/**
	 * @return the width of the texture
	 */
	public int getWidth();

	/**
	 * @return the height of the texture
	 */
	public int getHeight();

	/**
	 * @return the {@link UV} coordinates of the texture
	 */
	public UV getUV();

	/**
	 * @return the texture itself
	 */
	public Texture getTexture();

	/**
	 * @return the opengl texture id
	 */
	public int getID();

}
