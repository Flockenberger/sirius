package at.flockenberger.sirius.utillity.exceptions;

public class SiriusException extends RuntimeException
{
	private static final long serialVersionUID = -3613195426708110913L;

	public SiriusException()
	{
		super("Error during sirius runtime!");
	}

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later
	 *                retrieval by the {@link #getMessage()} method.
	 */
	public SiriusException(String message)
	{
		super(message);
	}
}
