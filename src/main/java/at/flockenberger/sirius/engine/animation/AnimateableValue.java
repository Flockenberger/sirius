package at.flockenberger.sirius.engine.animation;

/**
 * <h1>AnimateableValue</h1><br>
 * An AnimateableValue should be implemented by any type or object which should
 * be able to be animated by the {@link Animation} class.
 * 
 * @author Florian Wagner
 * 
 * @param <T> the type of the object to animate
 */
public interface AnimateableValue<T>
{
	/**
	 * @return the current, actual, value
	 */
	public T get();

	/**
	 * Sets the target for this animateable value.
	 * 
	 * @param target the target value
	 */
	public void target(T target);

	/**
	 * Sets the actual and the target to the same value.<br>
	 * 
	 * @param value the value to set both target and actual values
	 */
	public void set(T value);

	/**
	 * Sets the actual value of this value.
	 * 
	 * @param value the value to set
	 */
	public void value(T value);

	/**
	 * Update this value. <br>
	 * 
	 * @param dt the delta value to update. Usually this is the delta time of one
	 *           frame
	 */
	public void update(float dt);
}
