package sirius;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;
import java.net.URISyntaxException;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import at.flockenberger.sirius.engine.BaseDraw;
import at.flockenberger.sirius.engine.SpriteBatch;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.Font;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.resource.ImageResource;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.engine.resource.URLResource;
import at.flockenberger.sirius.engine.texture.Texture;
import at.flockenberger.sirius.engine.texture.TextureRegion;
import at.flockenberger.sirius.game.Game;

public class SpriteBatchTest extends Game
{

	public SpriteBatchTest(int width, int height, String title)
	{
		super(width, height, title);
	}

	public static void main(String[] args)
	{
		Game game = new SpriteBatchTest(800, 600, "Sirius Game");
		// game.setFullscreen();
		game.start();

	}

	private Texture tex, tex2;
	private TextureRegion tile;
	private SpriteBatch batch;
	private Font font;
	private float panX, panY, rot, zoom = 1f;
	private final float MOVE_SPEED = 10f;
	private final float ZOOM_SPEED = 0.025f;
	private final float ROT_SPEED = 0.05f;
	private BaseDraw draw;

	public void init()
	{
		super.init();

		// Load some textures

		ImageResource t1 = ResourceManager.get().loadImageResource("tiles", "/tiles.png");
		ImageResource t2 = ResourceManager.get().loadImageResource("ptsans", "/ptsans_00.png");
		URLResource r1 = ResourceManager.get().loadURLResource("font", "/ptsans.fnt");
		URLResource r2 = ResourceManager.get().loadURLResource("font", "/ptsans_00.png");

		tex = new Texture(t1.getImage());
		tex2 = new Texture(t2.getImage());
		tile = new TextureRegion(tex, 128, 64, 64, 64);

		try
		{
			font = new Font(r1.getURL(), r2.getURL());
		} catch (IOException | URISyntaxException e)
		{
			e.printStackTrace();
		}

		glClearColor(0.5f, .5f, .5f, 1f);
		// batch = new SpriteBatch();
		draw = new BaseDraw(Window.getActiveWidth(), Window.getActiveHeight());
	}

	public void render()
	{
		super.render();

		// drawGame();
		// drawHUD();
		draw.setPenColor(Color.WHITE);
		draw.drawCircle(0, 0, 100, 200);

	//	System.out.println(getFPS());
	}

	@Override
	public void resize()
	{
		GL20.glViewport(0, 0, Window.getActiveWidth(), Window.getActiveHeight());
		// batch.resize(Window.getActiveWidth(), Window.getActiveHeight());
	}
}
