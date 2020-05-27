package at.flockenberger.sirius.utillity.exceptions;

public class SiriusStubException extends Exception
{

	private static final long serialVersionUID = -2196465611246424681L;

	public SiriusStubException(Throwable cause)
	{
		super(cause);
	}

	public SiriusStubException(String message)
	{
		super(message);
	}

	public SiriusStubException()
	{
		super("Called a not-implemented stub method!");
	}
}
