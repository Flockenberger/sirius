package sirius;

import at.flockenberger.sirius.engine.graphic.Cursor;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.Game;
import at.flockenberger.sirius.game.application.LayerStack;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestGame extends Game
{

	public TestGame(int width, int height, String title)
	{
		super(width, height, title);
	}

	private static Icon icon;

	public static void main(String[] args)
	{
		SLogger.getSystemLogger().enableDebugOutput();
		SLogger.getSystemLogger().suppressWarnings();

		Game game = new TestGame(800, 600, "Sirius Game");

		// this only works because loadGameResources is called in the
		// games constructor!
		icon = new Icon(ResourceManager.get().getImage("icon").resize(32, 32));
		game.setGameIcon(icon);
	//	game.setFullscreen();
		game.start();

	}

	@Override
	public void initGame(LayerStack gameLayers)
	{
		TestLayer layer = new TestLayer("TestLayer");
		gameLayers.addLayerActive(layer);
		Mouse.setCursor(new Cursor(icon));

	}

	@Override
	public void loadGameResources()
	{
		ResourceManager.get().loadImageResource("icon", "/cursor.png");
		ResourceManager.get().loadImageResource("font", "/ptsans_00.png");
		ResourceManager.get().loadURLResource("font_url", "/ptsans.fnt");
	}

}
