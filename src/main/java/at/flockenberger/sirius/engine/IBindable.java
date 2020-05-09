package at.flockenberger.sirius.engine;

/**
 * <h1>IBindable</h1><br>
 * This interface should be implemented by every opengl-bindable object.
 * 
 * @author Florian Wagner
 *
 */
public interface IBindable
{
	/**
	 * Binds the given opengl object.
	 */
	public void bind();

	/**
	 * Unbinds the given opengl object.
	 */
	public void unbind();

}
