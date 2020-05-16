package alloc;

import at.flockenberger.sirius.engine.allocate.Allocator;

public class AllocTest
{

	public static void main(String[] args)
	{
		Allocator alloc = Allocator.DefaultAllocator();
		
		
		AllocClass alloclass = (AllocClass) alloc.allocate(AllocClass.class);
		alloc.init();
		alloc.alloc(AllocClass.class);
		
		System.out.println(alloclass.isInit());
		
	}

}
