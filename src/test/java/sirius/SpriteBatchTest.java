package sirius;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.IOException;
import java.net.URISyntaxException;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import at.flockenberger.sirius.engine.SpriteBatch;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.input.KeyCode;
import at.flockenberger.sirius.engine.input.Keyboard;
import at.flockenberger.sirius.engine.resource.ImageResource;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.engine.resource.URLResource;
import at.flockenberger.sirius.engine.texture.Texture;
import at.flockenberger.sirius.engine.texture.TextureRegion;
import at.flockenberger.sirius.game.Game;
import at.flockenberger.sirius.graphic.Color;
import at.flockenberger.sirius.graphic.text.Font;

public class SpriteBatchTest extends Game
{

	public SpriteBatchTest(int width, int height, String title)
	{
		super(width, height, title);
		// TODO Auto-generated constructor stub
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

		// glClearColor(0.5f, .5f, .5f, 1f);
		batch = new SpriteBatch();

	}

	void drawGame()
	{
		// get the instance of the view matrix for our batch
		Matrix4f view = batch.getViewMatrix();

		// reset the matrix to identity, i.e. "no camera transform"
		view.identity();

		// scale the view
		if (zoom != 1f)
		{
			view.scale(new Vector3f(zoom, zoom, 1f));
		}

		// pan the camera by translating the view matrix
		view.translate(new Vector3f(panX, panY, 0));

		// after translation, we can rotate...
		if (rot != 0f)
		{
			// we want to rotate by a center origin point, so first we translate
			view.translate(new Vector3f(Window.getActiveWidth() / 2, Window.getActiveHeight() / 2, 0));

			// then we rotate
			view.rotate(rot, new Vector3f(0, 0, 1));

			// then we translate back
			view.translate(new Vector3f(-Window.getActiveWidth() / 2, -Window.getActiveHeight() / 2, 0));
		}

		// apply other transformations here...

		// update the new view matrix
		batch.updateUniforms();

		// start the sprite batch
		batch.begin();

		// draw a tile from our sprite sheet
		batch.draw(tile, 10, 10);

		batch.draw(tile, 10, 100, 128, 128); // we can stretch it with a new width/height

		// we can also draw a region of a Texture on the fly like so:
		batch.drawRegion(tex, 0, 0, 32, 32, // srcX, srcY, srcWidth, srcHeight
				10, 250, 32, 32); // dstX, dstY, dstWidth, dstHeight

		// tint batch red
		batch.setColor(Color.RED);
		batch.draw(tex2, 0, 0, Window.getActiveWidth(), Window.getActiveHeight());

		// reset color
		batch.setColor(Color.WHITE);

		// finish the sprite batch and push the tiles to the GPU
		batch.end();
	}

	void drawHUD()
	{
		// draw the text with identity matrix, i.e. no camera transformation
		batch.getViewMatrix().identity();
		batch.updateUniforms();

		batch.begin();
		// ... render any hud elements
		font.drawText(batch, "Control camera with WASD, UP/DOWN and LEFT/RIGHT", 10, 10);
		batch.end();
	}

	public void render()
	{
		super.render();

		if (Keyboard.isKeyPressed(KeyCode.A))
			panX -= MOVE_SPEED;
		if (Keyboard.isKeyPressed(KeyCode.D))
			panX += MOVE_SPEED;
		if (Keyboard.isKeyPressed(KeyCode.W))
			panY -= MOVE_SPEED;
		if (Keyboard.isKeyPressed(KeyCode.S))
			panY += MOVE_SPEED;

		if (Keyboard.isKeyPressed(KeyCode.UP))
			zoom += ZOOM_SPEED;
		if (Keyboard.isKeyPressed(KeyCode.DOWN))
			zoom -= ZOOM_SPEED;

		zoom = Math.max(0.15f, zoom);

		if (Keyboard.isKeyPressed(KeyCode.LEFT))
			rot -= ROT_SPEED;
		if (Keyboard.isKeyPressed(KeyCode.RIGHT))
			rot += ROT_SPEED;

		drawGame();
		drawHUD();

	}

	@Override
	public void resize()
	{
		batch.resize(Window.getActiveWidth(), Window.getActiveHeight());
	}
}
