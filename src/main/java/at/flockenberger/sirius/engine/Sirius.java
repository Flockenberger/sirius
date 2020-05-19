package at.flockenberger.sirius.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.flockenberger.sirius.engine.allocate.Allocator;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.graphic.text.SiriusFont;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.game.application.AbstractGame;
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
	public static SiriusFont fontDefault;
	public static AbstractGame game;
	public static RenderSettings renderSettings;
	private final static GsonBuilder gsonB = new GsonBuilder();
	public static Gson gson;

	protected static Allocator allocator = Allocator.DefaultAllocator();

	public Sirius(int width, int height, String title, AbstractGame g)
	{
		timer = Timer.getTimer();
		renderer = allocator.allocate(Renderer.class);
		layerStack = allocator.allocate(LayerStack.class);
		postProcessor = allocator.allocate(PostProcessor.class);
		window = new Window(width, height, title);
		renderSettings = new RenderSettings();
		gsonB.setPrettyPrinting();
		gson = gsonB.create();
		game = g;
	}

	public static void free()
	{
		allocator.free();
		window.free();
	}

	public static void init()
	{
		allocator.init();
		fontDefault = new SiriusFont();

	}
}
