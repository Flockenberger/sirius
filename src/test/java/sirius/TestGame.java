package sirius;

import at.flockenberger.sirius.audio.AudioManager;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.Cursor;
import at.flockenberger.sirius.engine.graphic.Icon;
import at.flockenberger.sirius.engine.input.Mouse;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.SiriusGame;
import at.flockenberger.sirius.game.application.LayerStack;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestGame extends SiriusGame
{

	public TestGame(int width, int height, String title)
	{
		super(width, height, title);
	}

	private static Icon icon;

	public static void main(String[] args)
	{
		SLogger.getSystemLogger().enableDebugOutput();
		//SLogger.getSystemLogger().suppressWarnings();

		SiriusGame game = new TestGame(800, 600, "Sirius Game");

		// this only works because loadGameResources is called in the
		// games constructor!
		icon = new Icon(ResourceManager.get().getImage("icon").resize(32, 32));
		game.setGameIcon(icon);
		// game.setFullscreen();
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
		ResourceManager resMan = Sirius.resMan;

		AudioManager audioManager;
		audioManager = Sirius.audioManager;

		resMan.loadImageResource("icon", "/cursor.png");
		resMan.loadImageResource("font", "/ptsans_00.png");
		resMan.loadURLResource("font_url", "/ptsans.fnt");
		resMan.loadImageResource("companion", "/companion_0.2.png");
		resMan.loadImageResource("companion", "/companion_0.2.png");
		resMan.loadImageResource("texture", "/texture.jpg");
		resMan.loadImageResource("sheet", "/sprite-animation1.png");
		resMan.loadImageResource("tiles", "/tiles.png");
		resMan.loadImageResource("mc", "/MC_0.2.png");
		resMan.loadAudioResource("bdo", "/bounce.wav");
		
	}

}
