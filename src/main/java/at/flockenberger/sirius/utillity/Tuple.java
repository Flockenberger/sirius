package at.flockenberger.sirius.utillity;

/**
 * <h1>Tuple</h1><br>
 * Simple helper class to return a tuple of any values.
 * 
 * @author Florian Wagner
 *
 * @param <V> first value type of the tuple
 * @param <K> second value type of the tuple
 */
public class Tuple<V, K>
{
	/**
	 * first value of the tuple
	 */
	public V a;
	
	/**
	 * second value of the tuple
	 */
	public K b;
	
	public Tuple(V _a, K _b) {
		a = _a;
		b = _b;
	}
}
