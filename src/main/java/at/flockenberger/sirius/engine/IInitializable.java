package at.flockenberger.sirius.engine;

/**
 * <h1>IInitializable</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public interface IInitializable
{

	/**
	 * Initializes any resources that this class might need.<br>
	 * Should be the first call for the object.
	 */
	public void init();
}
