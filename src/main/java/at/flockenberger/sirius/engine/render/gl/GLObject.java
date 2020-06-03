package at.flockenberger.sirius.engine.render.gl;

import at.flockenberger.sirius.engine.IBindable;
import at.flockenberger.sirius.engine.IFreeable;

/**
 * <h1>GLObject</h1><br>
 * Base class for opengl buffer and array objects such as {@link FBO},
 * {@link VAO} and {@link VBO}.
 * 
 * @author Florian Wagner
 *
 */
public abstract class GLObject implements IFreeable, IBindable
{
	protected int id;
	/**
	 * @return the id of this object
	 */
	public abstract int getID();
}
