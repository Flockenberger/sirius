package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.graphic.text.Text;

public abstract class Game extends AbstractGame
{
	private Text debugText;
	private Runtime rt = Runtime.getRuntime();

	public Game(int width, int height, String title)
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
				update();
				Sirius.timer.updateUPS();
				accumulator -= interval;
			}

			/* Calculate alpha value for interpolation */
			alpha = accumulator / interval;
			// fbo.bind();
			render(alpha);

			applyPostProcessing(Sirius.postProcessor);
			// fbo.unbind();

			// drawFBO();

			Sirius.timer.updateFPS();

			/* Update timer */
			Sirius.timer.update();
			Sirius.renderer.updateMatrix(DEFAULT_CAM);
			Sirius.renderer.begin();
			long total = rt.totalMemory();
			long free = rt.freeMemory();
			debugText.setText("FPS: " + String.valueOf(Sirius.timer.getFPS()) + "Free Memory: " + free + ", Total Memory: " + total);
			debugText.draw();
			Sirius.renderer.end();
			/* Update window to show the new screen */
			Sirius.window.update();

			/* Synchronize if v-sync is disabled */
			if (!Sirius.window.isVSyncEnabled())
			{
				sync(TARGET_FPS);
			}
		}
	}

}
