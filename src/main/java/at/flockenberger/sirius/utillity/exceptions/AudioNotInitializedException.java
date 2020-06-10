package at.flockenberger.sirius.utillity.exceptions;

public class AudioNotInitializedException extends RuntimeException
{
	private static final long serialVersionUID = -2444015239219263621L;

	public AudioNotInitializedException()
	{
		super();
	}

	public AudioNotInitializedException(String message)
	{
		super(message);
	}
}
