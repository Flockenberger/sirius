package at.flockenberger.sirius.game.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>LayerStack</h1><br>
 * The {@link LayerStack} handles all {@link LayerBase} that have been added to
 * the program.<br>
 * The Layers are split into two kinds. <br>
 * Normal Layers <br>
 * Overlay Layer <br>
 * Overlay layers will always be on top of normal layers. They should be used
 * for rendering GUI to the screen.
 * 
 * @author Florian Wagner
 *
 */
public class LayerStack extends LayerBase
{
	/**
	 * A {@link Map} of layers that can be accessed using a {@link String}
	 */
	private Map<String, LayerBase> layers;
	private DefaultLayer defLayer;
	/**
	 * The currently active layer
	 */
	private LayerBase currentLayer;

	/**
	 * Creates a new LayerStack.
	 */
	public LayerStack()
	{
		super("_");
		layers = new HashMap<>();
		defLayer = new DefaultLayer("default");
		addLayerActive(defLayer);
	}

	/**
	 * Pushes a {@link LayerBase} to the stack. <br>
	 * Sets this layer to the currently active layer.
	 * 
	 * @param name  the name of the layer
	 * @param layer the {@link LayerBase} to push
	 */
	public void addLayerActive(LayerBase layer)
	{
		SUtils.checkNull(layer, "Layer");

		if (currentLayer != null)
			currentLayer.detach();

		layers.put(layer.name, layer);
		currentLayer = layer;
		currentLayer.attach();
	}

	/**
	 * Adds the given layer to the layers
	 * 
	 * @param layer the layer to add
	 */
	public void addLayer(LayerBase layer)
	{
		SUtils.checkNull(layer, "Layer");
		layers.put(layer.name, layer);
	}

	/**
	 * Sets the given layer active. <br>
	 * If it does not already exist in the layers it will be added using
	 * {@link #addLayerActive(LayerBase)} and set active.
	 * 
	 * @param layer the layer to set active
	 */
	public boolean setActive(LayerBase layer)
	{
		SUtils.checkNull(layer, "Layer");
		if (!setActive(layer.name))
			addLayerActive(layer);
		return true;
	}

	/**
	 * Sets the given layer active. <br>
	 * 
	 * @param layer the layer to set active
	 * @return true if the layer was set to active otherwise false
	 */
	public boolean setActive(String name)
	{
		SUtils.checkNull(name, "Layer Name");
		if (layers.containsKey(name))
		{
			currentLayer.detach();
			currentLayer = layers.get(name);
			currentLayer.attach();
			return true;
		}
		return false;
	}

	/**
	 * @return the internal {@link List} of layers
	 */
	public Map<String, LayerBase> stack()
	{
		return this.layers;
	}

	/**
	 * Called to free any resources. <br>
	 * Also calls {@link LayerBase#free()} on all attached layers
	 */
	@Override
	public void free()
	{
		for (LayerBase l : layers.values())
		{
			l.free();
		}
	}

	@Override
	public void attach()
	{
		currentLayer.attach();
	}

	@Override
	public void detach()
	{
		currentLayer.detach();
	}

	@Override
	public void onUpdate(float ft)
	{
		currentLayer.onUpdate(ft);
	}

	@Override
	public void onUpdate()
	{
		currentLayer.onUpdate();
	}

	@Override
	public void onInput()
	{
		currentLayer.onInput();
	}

	@Override
	public void onRender(Renderer render)
	{
		currentLayer.onRender(render);
	}

	@Override
	public void onRender(Renderer render, float alpha)
	{
		currentLayer.onRender(render, alpha);
	}

}
