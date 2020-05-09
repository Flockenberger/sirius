package at.flockenberger.sirius.engine;

/**
 * <h1>IFreeable</h1><br>
 * Should be implemented by any object that needs to free resources at some
 * point.
 * 
 * @author Florian Wagner
 *
 */
public interface IFreeable
{

	/**
	 * Frees any resources that this object allocated.
	 */
	public void free();

}
