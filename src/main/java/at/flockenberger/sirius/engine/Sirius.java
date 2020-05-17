package at.flockenberger.sirius.engine;

import at.flockenberger.sirius.engine.allocate.Allocator;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.game.application.LayerStack;
import at.flockenberger.sirius.utillity.Timer;

public class Sirius
{
	public static Renderer renderer;
	public static Timer timer;
	public static LayerStack layerStack;
	public static Icon icon;
	public static Window window;
	public static PostProcessor postProcessor;
	protected static Allocator allocator = Allocator.DefaultAllocator();

	public Sirius(int width, int height, String title)
	{
		timer = Timer.getTimer();

		renderer = allocator.allocate(Renderer.class);
		layerStack = allocator.allocate(LayerStack.class);
		postProcessor = allocator.allocate(PostProcessor.class);
		window = new Window(width, height, title);

	}
	
	public static void free() {
		allocator.free();
		window.free();
	}
	
	public static void init() {
		allocator.init();
	}
}
