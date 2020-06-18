package at.flockenberger.sirius.engine.postprocess;

import java.util.HashSet;
import java.util.Set;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.allocate.Allocateable;

public class PostProcessor extends Allocateable implements PostProcessingPipeline
{

	public Set<PostProcessingFilter> filterList;

	@Override
	public void free()
	{
		if (filterList != null)
			filterList.clear();
		filterList = null;
	}

	@Override
	public void init()
	{
		filterList = new HashSet<>();
	}

	@Override
	public void applyFilter(PostProcessingFilter filter)
	{
		if (!filterList.contains(filter))
			filterList.add(filter);
	}

	@Override
	public void removeFilter(PostProcessingFilter filter)
	{
		if (filterList.contains(filter))
			filterList.remove(filter);
	}

	@Override
	public void postProcess()
	{
		Sirius.renderer.begin();

		for (PostProcessingFilter filter : filterList)
		{
			filter.update();
			Sirius.renderer.switchProgram(filter, true);
			Sirius.renderer.draw(Sirius.game.getFBO().getTexture(), 0, 0, 0, 0, Window.getActiveWidth(),
					Window.getActiveHeight(), 1, 1, 0);
		}
		Sirius.renderer.end();

	}

}
