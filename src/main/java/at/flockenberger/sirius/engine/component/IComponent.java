package at.flockenberger.sirius.engine.component;

/**
 * <h1>IComponent</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public interface IComponent
{

	/**
	 * 
	 */
	public void update();

	/**
	 * 
	 * @param dt
	 */
	public void update(float dt);

}
