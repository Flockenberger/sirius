package at.flockenberger.sirius.engine.allocate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class Allocator 
{

	private Map<Class<?>, Allocateable> allocs;

	private static Allocator DEFAULT_ALLOCATOR;

	public static Allocator DefaultAllocator()
	{
		if (DEFAULT_ALLOCATOR == null)
			DEFAULT_ALLOCATOR = new Allocator();
		return DEFAULT_ALLOCATOR;
	}
	
	private Allocator()
	{
		this.allocs = new HashMap<Class<?>, Allocateable>();
	}
	
	public <T extends Allocateable> void alloc(Type t){
		try
		{
			Class.forName(t.getTypeName());
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	public <T extends Allocateable> T allocate(Class<T> cls)
	{
		try
		{
			T obj = cls.newInstance();
			
			allocs.put(cls, obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e)
		{
			SLogger.getSystemLogger().except(e);
		}
		return null;
	}

	public <T extends Allocateable> void init(Class<T> cls)
	{
		SUtils.checkNull(cls, "Class");

		if (allocs.containsKey(cls))
		{
			Allocateable alloc = allocs.get(cls);
			if (alloc != null)
				alloc.init();
		} else
		{
			allocate(cls);
			init(cls);
		}
	}

	public <T extends Allocateable > void free(Class<T> cls)
	{
		SUtils.checkNull(cls, "Class");

		if (allocs.containsKey(cls))
		{
			Allocateable alloc = allocs.get(cls);
			if (alloc != null)
				alloc.free();
		}
	}

	public void init()
	{
		for (Allocateable alloc : allocs.values())
		{
			if (alloc != null)
				alloc.init();
		}
	}

	public void free()
	{
		for (Allocateable alloc : allocs.values())
		{
			if (alloc != null)
				alloc.free();
		}
	}
}
