package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Renderer;

/**
 * <h1>LayerBase</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public abstract class LayerBase implements IFreeable
{
	protected String name;

	public LayerBase(String layerName)
	{
		this.name = layerName;
	}

	/**
	 * Called when the layer is attached.<br>
	 * Initializations can be done here.
	 */
	public abstract void attach();

	/**
	 * Called when the layer is detached. <br>
	 * Any closing operations can be done here. <br>
	 * Optionally you can free any resources using {@link #free()} here aswell.
	 * {@link #free()} is only called when the {@link LayerStack} is being freed
	 * aswell.
	 */
	public abstract void detach();

	/**
	 * Called when the layer is being updated. <br>
	 * 
	 * @param ft the delta time
	 */
	public abstract void onUpdate(float ft);

	/**
	 * Called when the layer is being updated. <br>
	 */
	public abstract void onUpdate();

	/**
	 * Called to handle user input.
	 */
	public abstract void onInput();

	/**
	 * Called after {@link #onUpdate(float)} / {@link #onUpdate()} in the rendering
	 * pipeline.
	 * 
	 */
	public abstract void onRender(Renderer render);

	/**
	 * Called after {@link #onUpdate(float)} / {@link #onUpdate()} in the rendering
	 * pipeline.
	 * 
	 * @param alpha the alpha value of this render
	 */
	public abstract void onRender(Renderer render, float alpha);

}
