/*
 * The MIT License (MIT)
 *
 * Copyright © 2014-2018, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package at.flockenberger.sirius.engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

import java.awt.Font;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import at.flockenberger.sirius.engine.allocate.Allocateable;
import at.flockenberger.sirius.engine.gl.VAO;
import at.flockenberger.sirius.engine.gl.VBO;
import at.flockenberger.sirius.engine.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.gl.shader.ShaderProgram;
import at.flockenberger.sirius.engine.gl.shader.VertexShader;
import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.engine.graphic.text.SiriusFont;
import at.flockenberger.sirius.engine.graphic.texture.Texture;
import at.flockenberger.sirius.engine.graphic.texture.TextureRegion;
import at.flockenberger.sirius.engine.resource.ResourceManager;
import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.Timer;

public class Renderer extends Allocateable
{

	private VAO vao;
	private VBO vbo;

	private ShaderProgram program;
	private FloatBuffer vertices;

	private int numVertices;
	private boolean drawing;

	private int width;
	private int height;

	private Texture curTex;
	private SiriusFont font;

	/* Texture coordinates */
	private float s1 = 0f;
	private float t1 = 1f;

	private float s2 = 1f;
	private float t2 = 0f;
	private float r, g, b, a;

	private Color WHITE = Color.WHITE;
	private Matrix4f model = new Matrix4f();

	/** Initializes the renderer. */
	public void init()
	{
		/* Setup shader programs */
		setupShaderProgram();

		/* Enable blending */
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL11.GL_TEXTURE_2D);
		font = new SiriusFont(Font.getFont(Font.SANS_SERIF));
	}

	/**
	 * Clears the drawing area.
	 */
	public void clear(Color c)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}

	public void updateMatrix(Camera cam)
	{
		cam.recalculateMatrices(width, height);
		program.setUniformMatrix(program.getUniformLocation("projView"), cam.getViewProjectionMatrix());

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
			throw new IllegalStateException("Renderer is already drawing!");
		}
		drawing = true;
		numVertices = 0;
	}

	/**
	 * End rendering.
	 */
	public void end()
	{
		if (!drawing)
		{
			throw new IllegalStateException("Renderer isn't drawing!");
		}
		drawing = false;
		flush();
	}

	private void switchTexture(Texture texture)
	{
		if (curTex != texture)
		{
			flush();
			curTex = texture;
			if (curTex != null)
				curTex.bind();
		}
	}

	/**
	 * Flushes the data to the GPU to let it get rendered.
	 */
	public void flush()
	{
		if (numVertices > 0)
		{
			vertices.flip();

			if (vao != null)
			{
				vao.bind();
			} else
			{
				vbo.bind(GL_ARRAY_BUFFER);
				specifyVertexAttributes();
			}
			program.useProgram();

			/* Upload the new vertex data */
			vbo.bind(GL_ARRAY_BUFFER);
			vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

			/* Draw batch */
			glDrawArrays(GL_TRIANGLES, 0, numVertices);

			/* Clear vertex data for next batch */
			vertices.clear();
			// SLogger.getSystemLogger().debug("Drawing: " + numVertices / 6 + " quads!");
			numVertices = 0;
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

		if (curTex != null)
			curTex.unbind();

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
		drawTextureRegion(x, y, x + regWidth, y + regWidth, s1, t1, s2, t2, c);
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
	 * Calculates total width of a text.
	 *
	 * @param text The text
	 *
	 * @return Total width of the text
	 */
	public int getTextWidth(CharSequence text)
	{
		return font.getWidth(text);
	}

	/**
	 * Calculates total height of a text.
	 *
	 * @param text The text
	 *
	 * @return Total width of the text
	 */
	public int getTextHeight(CharSequence text)
	{
		return font.getHeight(text);
	}

	/**
	 * Draw text at the specified position.
	 *
	 * @param text Text to draw
	 * @param x    X coordinate of the text position
	 * @param y    Y coordinate of the text position
	 */
	public void drawText(CharSequence text, float x, float y)
	{
		font.drawText(this, text, x, y);
	}

	/**
	 * Draw text at the specified position and color.
	 *
	 * @param text Text to draw
	 * @param x    X coordinate of the text position
	 * @param y    Y coordinate of the text position
	 * @param c    Color to use
	 */
	public void drawText(CharSequence text, float x, float y, Color c)
	{
		font.drawText(this, text, x, y, c);
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

	/**
	 * Dispose renderer and clean up its used data.
	 */
	public void free()
	{
		MemoryUtil.memFree(vertices);

		if (vao != null)
		{
			vao.free();
		}
		vbo.free();
		program.free();

		// font.free();
		// debugFont.free();
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
		vertices = MemoryUtil.memAllocFloat(4096 * 2);

		/* Upload null data to allocate storage for the VBO */
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		/* Initialize variables */
		numVertices = 0;
		drawing = false;

		/* Load shaders */
		VertexShader vertexShader;
		FragmentShader fragmentShader;

		vertexShader = ResourceManager.get().loadShaderResource("vertexShader", "/shader2D.vert").getAsVertexShader();
		fragmentShader = ResourceManager.get().loadShaderResource("fragmentShader", "/shader2D.frag")
				.getAsFragmentShader();

		/* Create shader program */
		program = new ShaderProgram(vertexShader, fragmentShader);

		program.bindFragmentDataLocation(0, "fragColor");

		program.createProgram();
		program.useProgram();

		/* Delete linked shaders */
		vertexShader.free();
		fragmentShader.free();

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
		int uniTex = program.getUniformLocation("texImage");
		program.setUniform(uniTex, 0);
		model.identity();
		int uniModel = program.getUniformLocation("model");
		program.setUniformMatrix(uniModel, model);

	}

	/**
	 * Specifies the vertex pointers.
	 */
	private void specifyVertexAttributes()
	{
		/* Specify Vertex Pointer */
		int posAttrib = program.getAttributeLocation("position");
		program.enableVertexAttribute(posAttrib);
		program.pointVertexAttribute(posAttrib, 2, 8 * Float.BYTES, 0);

		/* Specify Color Pointer */
		int colAttrib = program.getAttributeLocation("color");
		program.enableVertexAttribute(colAttrib);
		program.pointVertexAttribute(colAttrib, 4, 8 * Float.BYTES, 2 * Float.BYTES);

		/* Specify Texture Pointer */
		int texAttrib = program.getAttributeLocation("texcoord");
		program.enableVertexAttribute(texAttrib);
		program.pointVertexAttribute(texAttrib, 2, 8 * Float.BYTES, 6 * Float.BYTES);
	}

}
