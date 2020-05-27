package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.text.Text;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.UV;

public abstract class SiriusGame extends AbstractGame
{
	private Text debugText;
	private Runtime rt = Runtime.getRuntime();

	public SiriusGame(int width, int height, String title)
	{
		super(width, height, title);
		debugText = new Text();
	}

	@Override
	public void gameLoop()
	{
		float delta;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;
		float alpha;

		while (running)
		{
			/* Check if game should close */
			if (Sirius.window.askClose())
			{
				running = false;
			}

			/* Get delta time and update the accumulator */
			delta = Sirius.timer.getDelta();
			accumulator += delta;

			/* Handle input */
			input();

			/* Update game and timer UPS if enough time has passed */
			while (accumulator >= interval)
			{
				Sirius.timer.updateUPS();
				accumulator -= interval;
				update();
				update(Sirius.timer.getDelta());
			}

			/* Calculate alpha value for interpolation */
			alpha = accumulator / interval;

			fbo.bind();
			render(alpha);

			applyPostProcessing(Sirius.postProcessor);
			Sirius.postProcessor.postProcess();

			fbo.unbind();

			Sirius.renderer.updateMatrix(DEFAULT_CAM);
			Sirius.renderer.begin();

			Texture tex = fbo.getTexture();
			UV uv = tex.getUV();
			uv.u1 = 0;
			uv.v1 = 1;
			uv.u2 = 1;
			uv.v2 = 0;

			Sirius.renderer.draw(fbo.getTexture(), 0, 0, 0, 0, Window.getActiveWidth(), Window.getActiveHeight(), 1, 1,
					0);

			Sirius.renderer.end();
			Sirius.timer.updateFPS();

			/* Update timer */
			Sirius.timer.update();
			Sirius.renderer.updateMatrix(DEFAULT_CAM);
			Sirius.renderer.begin();

			long total = (long) (rt.totalMemory() / 1e6);
			long free = (long) (rt.freeMemory() / 1e6);
			long used = total - free;

			debugText.setText("FPS: " + String.valueOf(Sirius.timer.getFPS()) + ", UPS: " + Sirius.timer.getUPS()
					+ ", Used Memory: " + used + "MB, Free Memory: " + free + "MB, Total Memory: " + total + "MB");
			debugText.draw();
			
			Sirius.renderer.end();
			Sirius.window.update();

			if (!Sirius.window.isVSyncEnabled())
			{
				sync(TARGET_FPS);
			}

		}
	}

}
