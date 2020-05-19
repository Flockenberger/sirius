package sirius;

import at.flockenberger.sirius.engine.BaseDraw;
import at.flockenberger.sirius.engine.Camera;
import at.flockenberger.sirius.engine.RenderSettings;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.postprocess.TestFilter;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.map.GameLevel;
import at.flockenberger.sirius.utillity.logging.SLogger;

public class TestLayer extends LayerBase
{

	Texture tex;
	Texture tiles;
	Camera cam;
	BaseDraw draw;
	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet;
	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 6, FRAME_ROWS = 5;
	SimpleParticleEmitter emitter;
	TestFilter filter;
	
	GameLevel level;
	
	private Text text;

	private float stateTime;

	public TestLayer(String layerName)
	{
		super(layerName);
	}

	@Override
	public void free()
	{
		tex.free();
		tiles.free();
		walkSheet.free();
		walkAnimation.free();

	}

	@Override
	public void attach()
	{
		SLogger.getSystemLogger().debug("TestLayer attached!");
		ResourceManager.get().loadImageResource("texture", "/texture.jpg");
		ResourceManager.get().loadImageResource("sheet", "/sprite-animation1.png");
		tex = Texture.createTexture(ResourceManager.get().getImage("texture").resize(32, 32));
		tiles = Texture.createTexture(ResourceManager.get().loadImageResource("tiles", "/tiles.png").getImage());
		cam = new Camera();
		walkSheet = Texture.createTexture(ResourceManager.get().getImage("sheet"));

		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
		{
			for (int j = 0; j < FRAME_COLS; j++)
			{
				walkFrames[index++] = tmp[j][i];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.001f, walkFrames);
		stateTime = 0f;
		text = new Text("Hello Woorld");
		filter = new TestFilter();
		
		ResourceManager.get().loadMapResource("mapTest", "/gameart2d-desert.json");
		level = new GameLevel(ResourceManager.get().getMap("mapTest"));
	}

	@Override
	public void detach()
	{
		SLogger.getSystemLogger().debug("TestLayer detached!");
	}

	@Override
	public void onUpdate(float ft)
	{

	}

	@Override
	public void onUpdate()
	{
		cam.update();
	}

	@Override
	public void onInput()
	{

		cam.input();
	}

	@Override
	public void onRender(Renderer render)
	{
		onRender(render, 1f);
	}

	@Override
	public void onRender(Renderer render, float alpha)
	{
		render.clear(Sirius.renderSettings.getColor(RenderSettings.BACKGROUND));

		render.updateMatrix(cam);
		int kk = 0;
	
//		render.begin();
//		int size = 32;
//		int hSize = size / 2;
//		for (int i = 0; i < Window.getActiveWidth(); i += size)
//			for (int j = 0; j < Window.getActiveHeight(); j += size)
//			{
//				render.draw(tex, i, j, hSize, hSize, size, size, 1, 1, (float) i + j);
//				kk++;
//			}
//
//		render.end();
//		text.setPosition(0, -text.getTextHeight());
//		text.setText("Drawing: " + kk + "Quads");
//		text.draw();
		level.drawLevel();
		
		// render.begin();
		// render.draw(tiles, 0, 0, 182, 128, 256, 256, 1, 1,
		// (float)Math.sin(Sirius.timer.getTime())*90);

		// render.end();

		// render.begin();
		// render.drawText("Hello World", 10, 10, Color.RED);
		// render.end();

		stateTime += Sirius.timer.getDelta();
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		render.begin();
		render.drawTextureRegion(currentFrame, 50, 50); // Draw
		render.end();
	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{
		// pp.applyFilter(filter);
	}

}
