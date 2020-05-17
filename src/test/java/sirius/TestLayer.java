package sirius;

import java.awt.Font;

import at.flockenberger.sirius.engine.BaseDraw;
import at.flockenberger.sirius.engine.Camera;
import at.flockenberger.sirius.engine.Renderer;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.engine.animation.AnimationMode;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.Image;
import at.flockenberger.sirius.engine.graphic.text.SiriusFont;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.particle.SimpleParticleEmitter;
import at.flockenberger.sirius.engine.postprocess.PostProcessor;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.game.application.LayerBase;
import at.flockenberger.sirius.utillity.Timer;
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
		for (int i = 0; i < Window.getActiveWidth(); i += 32)
			for (int j = 0; j < Window.getActiveHeight(); j += 32)
				render.draw(tex, i, j, 16, 16, 32, 32, 1, 1, (float) Math.sin(Sirius.timer.getTime()) * 90);
		render.end();

		// render.begin();
		// render.draw(tiles, 0, 0, 182, 128, 256, 256, 1, 1,
		// (float)Math.sin(Sirius.timer.getTime())*90);

		// render.end();

		// render.begin();
		// render.drawText("Hello World", 10, 10, Color.RED);
		// render.end();

		// stateTime += Timer.getTimer().getDelta();
		// TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		//
		// Image tmp = currentFrame.getRegionImage();
		// Window.setActiveWindowIcon(Sirius.icon.set(tmp));
		// tmp.free();
		// tmp = null;

		// render.begin();
		// render.drawTextureRegion(currentFrame, 50, 50); // Draw
		// render.end();
	}

	@Override
	public void onPostProcess(PostProcessor pp)
	{

	}

}
