package at.flockenberger.sirius.game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.graphic.WindowIcon;

public class Game extends GameBase
{

	public Game(boolean windowedFullscreen, String title, WindowIcon icon)
	{
		super(windowedFullscreen, title, icon);
		// TODO Auto-generated constructor stub
	}

	public Game(boolean windowedFullscreen, String title)
	{
		super(windowedFullscreen, title);
		// TODO Auto-generated constructor stub
	}

	public Game(boolean windowedFullscreen)
	{
		super(windowedFullscreen);
		// TODO Auto-generated constructor stub
	}

	public Game(int width, int height, String title, WindowIcon icon)
	{
		super(width, height, title, icon);
		// TODO Auto-generated constructor stub
	}

	public Game(int width, int height, String title)
	{
		super(width, height, title);
		// TODO Auto-generated constructor stub
	}

	public Game(int width, int height)
	{
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void resize()  {
		glViewport(0, 0, Window.getActiveWidth(), Window.getActiveHeight());
	}
	
	@Override
	public void init()
	{
		// 2D games generally won't require depth testing
		glDisable(GL_DEPTH_TEST);

		// Enable blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Set clear to transparent black
		glClearColor(0f, 0f, 0f, 0f);
	}

}
