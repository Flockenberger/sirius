package at.flockenberger.sirius.utillity.exceptions;

public class AudioNotSupportedException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5661518751330541283L;

	public AudioNotSupportedException(String message)
	{
		super(message);
	}

	public AudioNotSupportedException()
	{
		super();
	}
}
