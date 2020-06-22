package at.flockenberger.sirius.engine.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.allocate.Allocateable;
import at.flockenberger.sirius.engine.camera.ICamera;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.Image;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.render.gl.VAO;
import at.flockenberger.sirius.engine.render.gl.VBO;
import at.flockenberger.sirius.engine.render.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.render.gl.shader.ShaderProgram;
import at.flockenberger.sirius.engine.render.gl.shader.VertexShader;
import at.flockenberger.sirius.game.entity.Entity;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>Renderer</h1><br>
 * 
 * @author Florian Wagner
 *
 */
public class Renderer extends Allocateable
{

	private VAO vao;
	private VBO vbo;

	private ShaderProgram defaultProgram;
	private ShaderProgram customProgram;

	private FloatBuffer vertices;
	private boolean useCustomProgram = false;

	private int numVertices;
	private boolean drawing;

	private int width;
	private int height;
	private Texture whiteTexture;

	private Texture curTex;

	/* Texture coordinates */
	private float s1 = 0f;
	private float t1 = 1f;

	private float s2 = 1f;
	private float t2 = 0f;

	private Color WHITE = Color.WHITE;
	private Matrix4f model = new Matrix4f();

	private ShapeType shapeType = ShapeType.TRIANGLE;
	private Color shapeColor = Color.WHITE;

	public Texture getWhiteTexture()
	{ return this.whiteTexture; }

	/** Initializes the renderer */
	@Override
	public void init()
	{
		/* Setup shader programs */
		setupShaderProgram();

		/* Enable blending */
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL11.GL_TEXTURE_2D);

		Image image = new Image(1, 1);
		image.setPixel(0, 0, Color.WHITE);
		whiteTexture = Texture.createTexture(image);
	}

	public void useCustomProgram(boolean use)
	{

		this.useCustomProgram = use;
		if (customProgram == null)
			this.useCustomProgram = false;
	}

	public void switchProgram(ShaderProgram pr, boolean custom)
	{
		this.customProgram = pr;
		if (this.customProgram != null)
		{
			if (!this.customProgram.isCreated())
			{
				this.customProgram.createProgram();
			}
		}
		useCustomProgram(custom);
	}

	/**
	 * Clears the drawing area.
	 */
	public void clear(Color c)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public void updateMatrix(ICamera cam)
	{
		cam.recalculateMatrices(width, height);
		defaultProgram.setUniformMatrix(defaultProgram.getUniformLocation("projView"), cam.getViewProjectionMatrix());

	}

	/**
	 * Begin rendering.
	 */
	public void begin()
	{

		/*
		 * long window = GLFW.glfwGetCurrentContext();
		 * GLFW.glfwSetFramebufferSizeCallback(window, (id, width, height) -> {
		 * this.width = width; this.height = height; cam.recalculateMatrices(width,
		 * height); int _uniViewProj = program.getUniformLocation("projView");
		 * program.setUniformMatrix(_uniViewProj, cam.getViewProjectionMatrix());
		 * glViewport(0, 0, width, height);
		 * 
		 * });
		 */

		if (drawing)
		{
			end();
		}
		drawing = true;
		numVertices = 0;
		shapeType = ShapeType.TRIANGLE;

	}

	public boolean isDrawing()
	{ return drawing; }

	public void end()
	{
		if (drawing)
		{
			drawing = false;
			flush();
		}
	}

	private void switchTexture(Texture texture)
	{
		if (texture == null)
		{
			if (curTex != null)
				curTex.unbind();
			curTex = whiteTexture;
		}

		if ((curTex == null && texture != null) || (curTex != null && !curTex.equals(texture) && texture != null))
		{
			flush();
			curTex = texture;
			curTex.bind();

		}
	}

	private void updateBuffers()
	{
		if (vao != null)
			vao.bind();
		else
		{
			vbo.bind(GL_ARRAY_BUFFER);
			specifyVertexAttributes();
		}
	}

	private void bindAndUploadData()
	{
		/* Upload the new vertex data */
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);
	}

	private void useProgram()
	{
		if (!useCustomProgram)
			defaultProgram.useProgram();
		else
			customProgram.useProgram();
	}

	private void reset()
	{
		/* Clear vertex data for next batch */
		vertices.clear();
		numVertices = 0;
		this.shapeColor = Color.WHITE;
	}

	/**
	 * Flushes the data to the GPU to let it get rendered.
	 */
	public void flush()
	{
		if (numVertices > 0)
		{
			vertices.flip();

			updateBuffers();
			useProgram();

			bindAndUploadData();

			glDrawArrays(shapeType.getType(), 0, numVertices);
			reset();

		}
	}

	/**
	 * Draws the currently bound texture on specified coordinates.
	 *
	 * @param texture Used for getting width and height of the texture
	 * @param x       X position of the texture
	 * @param y       Y position of the texture
	 */
	public void drawTexture(Texture texture, float x, float y)
	{
		drawTexture(texture, x, y, WHITE);
	}

	public void drawEntity(Entity entity)
	{
		drawEntity(entity, entity.getTexture());
	}

	public void drawEntity(Entity entity, Texture t)
	{
		draw(t, entity.getPosition().x, entity.getPosition().y, t.getWidth() / 2f, t.getHeight() / 2f,
				entity.getWidth(), entity.getHeight(), entity.getScale().x, -entity.getScale().y, 0);
	}

	public void drawEntity(Entity entity, TextureRegion t)
	{
		draw(t, entity.getPosition().x, entity.getPosition().y, t.getWidth() / 2f, t.getHeight() / 2f,
				entity.getWidth(), entity.getHeight(), entity.getScale().x, -entity.getScale().y, 0);
	}

	/**
	 * Draws the currently bound texture on specified coordinates.
	 *
	 * @param texture  Used for getting width and height of the texture
	 * @param position the position of the texture to draw
	 */
	public void drawTexture(Texture texture, Vector2f position)
	{
		drawTexture(texture, position.x, position.y);
	}

	/**
	 * Draws the currently bound texture on specified coordinates and with specified
	 * color.
	 *
	 * @param texture Used for getting width and height of the texture
	 * @param x       X position of the texture
	 * @param y       Y position of the texture
	 * @param c       The color to use
	 */
	public void drawTexture(Texture texture, float x, float y, Color c)
	{
		/* Vertex positions */
		s1 = 0f;
		t1 = 1f;

		s2 = 1f;
		t2 = 0f;
		switchTexture(texture);
		drawTextureRegion(x, y, x + texture.getWidth(), y + texture.getHeight(), s1, t1, s2, t2, c);
	}

	public void drawColor(float x, float y, float sizeX, float sizeY, Color c)
	{

		switchTexture(null);
		drawTextureRegion(x, y, x + sizeX, y + sizeY, s1, t1, s2, t2, c);
	}

	/**
	 * Draws a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param texture   Used for getting width and height of the texture
	 * @param x         X position of the texture
	 * @param y         Y position of the texture
	 * @param regX      X position of the texture region
	 * @param regY      Y position of the texture region
	 * @param regWidth  Width of the texture region
	 * @param regHeight Height of the texture region
	 */
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight)
	{
		drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight, WHITE);
	}

	public void drawTextureRegion(TextureRegion region, Vector2f vec)
	{
		drawTextureRegion(region, vec.x, vec.y);
	}

	public void drawTextureRegion(TextureRegion region, float x, float y)
	{

		drawTextureRegion(region.getTexture(), x, y, region.getRegionX(), region.getRegionY(), region.getWidth(),
				region.getHeight());

	}

	/**
	 * Draws a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param texture   Used for getting width and height of the texture
	 * @param x         X position of the texture
	 * @param y         Y position of the texture
	 * @param regX      X position of the texture region
	 * @param regY      Y position of the texture region
	 * @param regWidth  Width of the texture region
	 * @param regHeight Height of the texture region
	 * @param c         The color to use
	 */
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight, Color c)
	{

		/* Texture coordinates */
		s1 = regX / texture.getWidth();
		t1 = (regY + regHeight) / texture.getHeight();
		s2 = (regX + regWidth) / texture.getWidth();
		t2 = regY / texture.getHeight();

		switchTexture(texture);
		drawTextureRegion(x, y, x + regWidth, y + regHeight, s1, t1, s2, t2, c);
	}

	/**
	 * Draws a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param x1 Bottom left x position
	 * @param y1 Bottom left y position
	 * @param x2 Top right x position
	 * @param y2 Top right y position
	 * @param s1 Bottom left s coordinate
	 * @param t1 Bottom left t coordinate
	 * @param s2 Top right s coordinate
	 * @param t2 Top right t coordinate
	 */
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2)
	{
		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, WHITE);
	}

	/**
	 * Draws a texture region with the currently bound texture on specified
	 * coordinates.
	 *
	 * @param x1 Bottom left x position
	 * @param y1 Bottom left y position
	 * @param x2 Top right x position
	 * @param y2 Top right y position
	 * @param s1 Bottom left s coordinate
	 * @param t1 Bottom left t coordinate
	 * @param s2 Top right s coordinate
	 * @param t2 Top right t coordinate
	 * @param c  The color to use
	 */
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2,
			Color c)
	{
		if (vertices.remaining() < 7 * 6)
		{
			flush();
		}
		float r, g, b, a;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		a = c.getAlpha();

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
		vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

		numVertices += 6;
	}

	public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height,
			float scaleX, float scaleY, float rotation, Color c)
	{
		draw(region.getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, c);
	}

	public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height,
			float scaleX, float scaleY, float rotation)
	{
		draw(region.getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, WHITE);
	}

	public void draw(Texture region, float x, float y, float originX, float originY, float width, float height,
			float scaleX, float scaleY, float rotation)
	{
		draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation, WHITE);
	}

	public void draw(Texture region, float x, float y, float originX, float originY, float width, float height,
			float scaleX, float scaleY, float rotation, Color c)
	{

		switchTexture(region);
		if (vertices.remaining() < 7 * 6)
		{
			flush();
		}

		float r, g, b, a;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		a = c.getAlpha();

		// bottom left and top right corner points relative to origin
		final float worldOriginX = x + originX;
		final float worldOriginY = y + originY;
		float fx = -originX;
		float fy = -originY;
		float fx2 = width - originX;
		float fy2 = height - originY;

		// scale
		if (scaleX != 1 || scaleY != 1)
		{
			fx *= scaleX;
			fy *= scaleY;
			fx2 *= scaleX;
			fy2 *= scaleY;
		}

		// construct corner points, start from top left and go counter clockwise
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;

		float x1;
		float y1;
		float x2;
		float y2;
		float x3;
		float y3;
		float x4;
		float y4;

		// rotate
		if (rotation != 0)
		{
			final float cos = (float) Math.cos(SUtils.degToRad(rotation));
			final float sin = (float) Math.sin(SUtils.degToRad(rotation));

			x1 = cos * p1x - sin * p1y;
			y1 = sin * p1x + cos * p1y;

			x2 = cos * p2x - sin * p2y;
			y2 = sin * p2x + cos * p2y;

			x3 = cos * p3x - sin * p3y;
			y3 = sin * p3x + cos * p3y;

			x4 = x1 + (x3 - x2);
			y4 = y3 - (y2 - y1);
		} else
		{
			x1 = p1x;
			y1 = p1y;

			x2 = p2x;
			y2 = p2y;

			x3 = p3x;
			y3 = p3y;

			x4 = p4x;
			y4 = p4y;
		}

		x1 += worldOriginX;
		y1 += worldOriginY;
		x2 += worldOriginX;
		y2 += worldOriginY;
		x3 += worldOriginX;
		y3 += worldOriginY;
		x4 += worldOriginX;
		y4 += worldOriginY;
		float u = 0;
		float v = 1;
		float u2 = 1;
		float v2 = 0;

		if (region != null)
		{
			u = region.getUV().getU1();
			v = region.getUV().getV1();
			u2 = region.getUV().getU2();
			v2 = region.getUV().getV2();

		}

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(u).put(v);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(u).put(v2);
		vertices.put(x4).put(y4).put(r).put(g).put(b).put(a).put(u2).put(v);

		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(u).put(v2);
		vertices.put(x3).put(y3).put(r).put(g).put(b).put(a).put(u2).put(v2);
		vertices.put(x4).put(y4).put(r).put(g).put(b).put(a).put(u2).put(v);

		numVertices += 6;

	}

	// =========================
	// Shape rendering
	// =========================

	public void beginShape(ShapeType type)
	{
		if (drawing)
		{
			end();
		}
		drawing = true;
		numVertices = 0;
		shapeType = type;
		switchTexture(null);
	}

	public void beginShape()
	{
		this.beginShape(ShapeType.TRIANGLE);
	}

	public void color(Color c)
	{
		this.shapeColor = c;

	}

	private void color(float red, float green, float blue, float alpha)
	{
		this.shapeColor.set(red, green, blue, alpha);
	}

	public void vertex(float x, float y, float z)
	{
		vertex(x, y);
	}

	public void vertex(float x, float y)
	{
		vertices.put(x).put(y)/**/
				.put(shapeColor.getRed()).put(shapeColor.getGreen())/**/
				.put(shapeColor.getBlue()).put(shapeColor.getAlpha())/**/
				.put(1).put(1);

		numVertices++;
	}

	public final void line(float x, float y, float z, float x2, float y2, float z2)
	{
		line(x, y, z, x2, y2, z2, shapeColor, shapeColor);
	}

	public final void line(float x, float y, float x2, float y2)
	{
		line(x, y, 0.0f, x2, y2, 0.0f, shapeColor, shapeColor);
	}

	public final void line(Vector2f v0, Vector2f v1)
	{
		line(v0.x, v0.y, 0.0f, v1.x, v1.y, 0.0f, shapeColor, shapeColor);
	}

	public final void line(float x, float y, float x2, float y2, Color c1, Color c2)
	{
		line(x, y, 0.0f, x2, y2, 0.0f, c1, c2);
	}

	public void line(float x, float y, float z, float x2, float y2, float z2, Color c1, Color c2)
	{
		color(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
		vertex(x, y);
		color(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
		vertex(x2, y2);
	}

	public void rect(float x, float y, float width, float height)
	{
		if (shapeType == ShapeType.LINE)
		{

			vertex(x, y, 0);
			vertex(x + width, y, 0);

			vertex(x + width, y, 0);
			vertex(x + width, y + height, 0);

			vertex(x + width, y + height, 0);
			vertex(x, y + height, 0);

			vertex(x, y + height, 0);
			vertex(x, y, 0);
		} else
		{

			vertex(x, y, 0);
			vertex(x + width, y, 0);
			vertex(x + width, y + height, 0);

			vertex(x + width, y + height, 0);
			vertex(x, y + height, 0);
			vertex(x, y, 0);
		}
	}

	public void circle(float x, float y, float radius, Color c)
	{

		circle(x, y, radius, Math.max(1, (int) (6 * (float) Math.cbrt(radius))), c);

	}

	public void circle(float x, float y, float radius)
	{
		circle(x, y, radius, Math.max(1, (int) (6 * (float) Math.cbrt(radius))), shapeColor);

	}

	public void circle(float x, float y, float radius, int segments, Color c)
	{
		color(c);
		if (segments <= 0)
			throw new IllegalArgumentException("segments must be > 0.");

		float angle = (float) (2 * Math.PI / segments);
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		float cx = radius, cy = 0;
		if (shapeType == ShapeType.LINE)
		{
			for (int i = 0; i < segments; i++)
			{
				vertex(x + cx, y + cy, 0);
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				vertex(x + cx, y + cy, 0);
			}
			// Ensure the last segment is identical to the first.
			vertex(x + cx, y + cy, 0);
		} else
		{
			segments--;
			for (int i = 0; i < segments; i++)
			{
				vertex(x, y, 0);
				vertex(x + cx, y + cy, 0);
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				vertex(x + cx, y + cy, 0);
			}
			// Ensure the last segment is identical to the first.
			vertex(x, y, 0);
			vertex(x + cx, y + cy, 0);
		}

		cx = radius;
		cy = 0;
		vertex(x + cx, y + cy, 0);
	}

	public void circle(float x, float y, float radius, int segments)
	{
		circle(x, y, radius, segments, shapeColor);
	}

	public void arc(float x, float y, float radius, float start, float degrees)
	{
		arc(x, y, radius, start, degrees, Math.max(1, (int) (6 * (float) Math.cbrt(radius) * (degrees / 360.0f))));
	}

	public void arc(float x, float y, float radius, float start, float degrees, int segments)
	{
		if (segments <= 0)
			throw new IllegalArgumentException("segments must be > 0.");
		float theta = (float) ((2 * Math.PI * (degrees / 360.0f)) / segments);
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		float cx = (float) (radius * Math.cos(Math.toRadians(degrees)));
		float cy = (float) (radius * Math.sin(Math.toRadians(degrees)));

		if (shapeType == ShapeType.LINE)
		{

			vertex(x, y, 0);
			vertex(x + cx, y + cy, 0);
			for (int i = 0; i < segments; i++)
			{
				vertex(x + cx, y + cy, 0);
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				vertex(x + cx, y + cy, 0);
			}
			vertex(x + cx, y + cy, 0);
		} else
		{

			for (int i = 0; i < segments; i++)
			{
				vertex(x, y, 0);
				vertex(x + cx, y + cy, 0);
				float temp = cx;
				cx = cos * cx - sin * cy;
				cy = sin * temp + cos * cy;
				vertex(x + cx, y + cy, 0);
			}
			vertex(x, y, 0);
			vertex(x + cx, y + cy, 0);
		}

		cx = 0;
		cy = 0;

		vertex(x + cx, y + cy, 0);
	}

	public void endShape()
	{
		end();
		shapeType = ShapeType.TRIANGLE;
	}

	/**
	 * Dispose renderer and clean up its used data.
	 */
	@Override
	public void free()
	{
		MemoryUtil.memFree(vertices);

		if (vao != null)
		{
			vao.free();
		}
		vbo.free();
		defaultProgram.free();
		if (customProgram != null)
			customProgram.free();

	}

	/** Setups the default shader program. */
	private void setupShaderProgram()
	{

		/* Generate Vertex Array Object */
		vao = new VAO();
		vao.bind();

		/* Generate Vertex Buffer Object */
		vbo = new VBO();
		vbo.bind(GL_ARRAY_BUFFER);

		/* Create FloatBuffer */
		vertices = MemoryUtil.memAllocFloat(4096 * Float.BYTES);

		/* Upload null data to allocate storage for the VBO */
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		/* Initialize variables */
		numVertices = 0;
		drawing = false;

		/* Load shaders */
		VertexShader vertexShader;
		FragmentShader fragmentShader;

		vertexShader = Sirius.resMan.loadShaderResource("vertexShader", "/shader2D.vert").getAsVertexShader();
		fragmentShader = Sirius.resMan.loadShaderResource("fragmentShader", "/shader2D.frag").getAsFragmentShader();

		/* Create shader program */
		defaultProgram = new ShaderProgram(vertexShader, fragmentShader);
		defaultProgram.bindFragmentDataLocation(0, "fragColor");
		defaultProgram.createProgram();
		defaultProgram.useProgram();

		/* Get width and height of framebuffer */
		long window = GLFW.glfwGetCurrentContext();

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer widthBuffer = stack.mallocInt(1);
			IntBuffer heightBuffer = stack.mallocInt(1);
			GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
			width = widthBuffer.get();
			height = heightBuffer.get();
		}

		/* Specify Vertex Pointers */
		specifyVertexAttributes();

		/* Set texture uniform */
		int uniTex = defaultProgram.getUniformLocation("texImage");
		defaultProgram.setUniform(uniTex, 0);

		model.identity();
		int uniModel = defaultProgram.getUniformLocation("model");
		defaultProgram.setUniformMatrix(uniModel, model);

	}

	/**
	 * Specifies the vertex pointers.
	 */
	private void specifyVertexAttributes()
	{
		/* Specify Vertex Pointer */
		int posAttrib = defaultProgram.getAttributeLocation("position");
		defaultProgram.enableVertexAttribute(posAttrib);
		defaultProgram.pointVertexAttribute(posAttrib, 2, 8 * Float.BYTES, 0);

		/* Specify Color Pointer */
		int colAttrib = defaultProgram.getAttributeLocation("color");
		defaultProgram.enableVertexAttribute(colAttrib);
		defaultProgram.pointVertexAttribute(colAttrib, 4, 8 * Float.BYTES, 2 * Float.BYTES);

		/* Specify Texture Pointer */
		int texAttrib = defaultProgram.getAttributeLocation("texcoord");
		defaultProgram.enableVertexAttribute(texAttrib);
		defaultProgram.pointVertexAttribute(texAttrib, 2, 8 * Float.BYTES, 6 * Float.BYTES);
	}

}
