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
		filterList.clear();
		filterList = null;
	}

	public void init()
	{
		filterList = new HashSet<>();
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
		Sirius.renderer.begin();

		for (PostProcessingFilter filter : filterList)
		{
			filter.update();
			Sirius.renderer.switchProgram(filter, true);
			Sirius.renderer.draw(Sirius.game.fbo.getTexture(), 0, 0, 0, 0, Window.getActiveWidth(),
					Window.getActiveHeight(), 1, 1, 0);
		}
		Sirius.renderer.end();

	}

}
