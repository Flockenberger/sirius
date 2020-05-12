package at.flockenberger.sirius.game.application;

import at.flockenberger.sirius.engine.graphic.Color;

public abstract class Game extends AbstractGame
{
	public Game(int width, int height, String title)
	{
		super(width, height, title);
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
			if (window.askClose())
			{
				running = false;
			}

			/* Get delta time and update the accumulator */
			delta = timer.getDelta();
			accumulator += delta;

			/* Handle input */
			input();

			/* Update game and timer UPS if enough time has passed */
			while (accumulator >= interval)
			{
				update();
				timer.updateUPS();
				accumulator -= interval;
			}
			
			/* Calculate alpha value for interpolation */
			alpha = accumulator / interval;
			
			/* Render game and update timer FPS */
			render(alpha);
			timer.updateFPS();
			
			/* Update timer */
			timer.update();
			renderer.begin(DEFAULT_CAM);
			renderer.drawText(String.valueOf(timer.getFPS()), 0, 0, Color.BLACK);
			renderer.end();
			/* Update window to show the new screen */
			window.update();
			
			/* Synchronize if v-sync is disabled */
			if (!window.isVSyncEnabled())
			{
				sync(TARGET_FPS);
			}
		}
	}

}
