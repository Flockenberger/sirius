package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.Renderer;

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

	}

	@Override
	public void attach()
	{

	}

	@Override
	public void detach()
	{

	}

	@Override
	public void onUpdate(float ft)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void onInput()
	{

	}

	@Override
	public void onRender(Renderer render)
	{

	}

	@Override
	public void onRender(Renderer render, float alpha)
	{

	}

}
