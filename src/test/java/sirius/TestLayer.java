package sirius;

import java.awt.Font;

import at.flockenberger.sirius.engine.BaseDraw;
import at.flockenberger.sirius.engine.Camera;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.animation.AnimationMode;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.Sprite;
import at.flockenberger.sirius.engine.graphic.text.SiriusFont;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.LayerBase;
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
	SiriusFont font;
	SimpleParticleEmitter emitter;
	private float stateTime;
	Sprite sp;

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
		font = new SiriusFont(Font.getFont(Font.SANS_SERIF));
		tiles = Texture.createTexture(ResourceManager.get().loadImageResource("tiles", "/tiles.png").getImage());
		cam = new Camera();
		sp = new Sprite(tex);
		walkSheet = Texture.createTexture(ResourceManager.get().getImage("sheet"));

		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
		{
			for (int j = 0; j < FRAME_COLS; j++)
			{
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.01f, walkFrames, AnimationMode.REVERSED);
		stateTime = 0f;
		sp.setPosition(50, 50);

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
		// system.update();
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
		render.clear(Color.PINK);

		render.updateMatrix(cam);

		render.begin();
		for (int i = 0; i < Window.getActiveWidth(); i += 34)
			for (int j = 0; j < Window.getActiveHeight(); j += 34)
				render.drawTexture(tex, i, j);
		sp.draw(render);
		render.end();
		/*
		 * render.begin(); render.drawTexture(tiles, 100, 100);
		 * 
		 * render.end();
		 * 
		 * render.begin(); render.drawText("Hello World", 10, 10, Color.RED);
		 * render.end();
		 * 
		 * stateTime += Timer.getTimer().getDelta(); TextureRegion currentFrame =
		 * walkAnimation.getKeyFrame(stateTime, true); Window.setActiveWindowIcon(new
		 * Icon(currentFrame.getRegionTexture()));
		 * 
		 * render.begin(); render.drawTextureRegion(currentFrame, 50, 50); // Draw
		 * current frame at (50, 50) render.end();
		 */

	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{

	}

}
