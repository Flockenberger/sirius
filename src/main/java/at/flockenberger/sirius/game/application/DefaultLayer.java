package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.render.Renderer;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * </h1>DefaultLayer</h1><br>
 * The {@link DefaultLayer} prevents unwanted nullpointer exceptions when no
 * layer was added to the game.
 * 
 * @author Florian Wagner
 *
 */
public class DefaultLayer extends LayerBase
{

	public DefaultLayer(String layerName)
	{
		super(layerName);

	}

	@Override
	public void free()
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ free");

	}

	@Override
	public void attach()
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ attach");

	}

	@Override
	public void detach()
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ detach");
	}

	@Override
	public void onUpdate(float ft)
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ onUpdate");
	}

	@Override
	public void onUpdate()
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ onUpdate");
	}

	@Override
	public void onInput()
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ onInput");
	}

	@Override
	public void onRender(Renderer render)
	{
		onRender(render, 1f);
	}

	@Override
	public void onRender(Renderer render, float alpha)
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ onRender");

	}
	
	@Override
	public void onPostProcess(PostProcessor pp)
	{
		SLogger.getSystemLogger().debug("DefaultLayer: method stub @ onPostProcess");
	}

}
