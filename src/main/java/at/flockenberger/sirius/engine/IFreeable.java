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
	 * Frees any resources that this object allocated.<br>
	 * This method needs to be explicitly called. At no point in time (yet) does the
	 * Sirius System free these resources.
	 */
	public void free();

}
