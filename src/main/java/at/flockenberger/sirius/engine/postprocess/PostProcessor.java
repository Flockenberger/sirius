package at.flockenberger.sirius.engine.postprocess;

import java.util.ArrayList;
import java.util.List;

import at.flockenberger.sirius.engine.allocate.Allocateable;

public class PostProcessor extends Allocateable implements PostProcessingPipeline
{

	public List<PostProcessingFilter> filterList;

	@Override
	public void free()
	{
		filterList.clear();
		filterList = null;
	}

	public void init()
	{
		filterList = new ArrayList<>();
	}

	public void applyFilter(PostProcessingFilter filter)
	{
		if (!filterList.contains(filter))
			filterList.add(filter);
	}

	public void removeFilter(PostProcessingFilter filter)
	{
		if (filterList.contains(filter))
			filterList.remove(filter);
	}

	public void postProcess()
	{
		
	}
}
