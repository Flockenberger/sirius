package at.flockenberger.sirius.engine.postprocess;

import at.flockenberger.sirius.engine.IFreeable;

public interface PostProcessingPipeline extends IFreeable
{

	public void applyFilter(PostProcessingFilter filter);

	public void removeFilter(PostProcessingFilter filter);

	public void postProcess();
}
