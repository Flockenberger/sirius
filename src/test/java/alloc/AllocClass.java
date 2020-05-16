package alloc;

import at.flockenberger.sirius.engine.allocate.Allocateable;

public class AllocClass extends Allocateable
{

	private boolean wasInit = false;

	@Override
	protected void init()
	{
		System.out.println("init");
		wasInit = true;
	}

	@Override
	protected void free()
	{
		System.out.println("free");
	}

	public boolean isInit()
	{
		return wasInit;
	}
}
