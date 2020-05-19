package at.flockenberger.sirius.engine.gl;

import static org.lwjgl.opengl.ARBFramebufferObject.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.ARBFramebufferObject.GL_RENDERBUFFER;
import static org.lwjgl.opengl.ARBFramebufferObject.glBindFramebuffer;
import static org.lwjgl.opengl.ARBFramebufferObject.glBindRenderbuffer;
import static org.lwjgl.opengl.ARBFramebufferObject.glCheckFramebufferStatus;
import static org.lwjgl.opengl.ARBFramebufferObject.glDeleteFramebuffers;
import static org.lwjgl.opengl.ARBFramebufferObject.glDeleteRenderbuffers;
import static org.lwjgl.opengl.ARBFramebufferObject.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.ARBFramebufferObject.glGenFramebuffers;
import static org.lwjgl.opengl.ARBFramebufferObject.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.graphic.texture.Texture;

/**
 * <h1>FBO</h1><br>
 * FBO stands for Frame-Buffer-Object.
 * 
 * @author Florian Wagner
 *
 */
public class FBO extends GLObject
{

	public static boolean isSupported()
	{
		return GL.getCapabilities().GL_EXT_framebuffer_object;
	}

	int colorRenderBuffer;
	int depthRenderBuffer;
	int fbo;
	boolean resetFramebuffer;
	int width;
	int height;
	Texture colorTex;

	public FBO(int width, int height)
	{
		this.width = width;
		this.height = height;

	}

	public void createFramebufferObject()
	{
		colorRenderBuffer = glGenRenderbuffers();
		depthRenderBuffer = glGenRenderbuffers();
		fbo = glGenFramebuffers();

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		glBindRenderbuffer(GL_RENDERBUFFER, colorRenderBuffer);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);

		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, colorRenderBuffer);
		genTexture();
		//glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer);

	//	GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);
	//	glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer);
		int fboStatus = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		if (fboStatus != GL_FRAMEBUFFER_COMPLETE)
		{
			throw new AssertionError("Could not create FBO: " + fboStatus);
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	private void genTexture()
	{

		colorTex = Texture.createEmptyTexture(width, height);
		colorTex.bind();
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D,
				colorTex.getID(), 0);
		colorTex.unbind();
		
	}

	public Texture getTexture()
	{
		return colorTex;
	}

	public void resizeFramebufferTexture()
	{
		glDeleteRenderbuffers(depthRenderBuffer);
		glDeleteRenderbuffers(colorRenderBuffer);
		glDeleteFramebuffers(fbo);
		createFramebufferObject();
	}

	public void update()
	{
		if (resetFramebuffer)
		{
			resizeFramebufferTexture();
			resetFramebuffer = false;
		}
	}

	@Override
	public void free()
	{

	}

	@Override
	public void bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}

	@Override
	public void unbind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		// glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
		// glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
		// GL_COLOR_BUFFER_BIT, GL_NEAREST);

	}

	@Override
	public int getID()
	{
		return fbo;
	}

	public int getColorRenderBuffer()
	{
		return colorRenderBuffer;
	}

	public void setColorRenderBuffer(int colorRenderBuffer)
	{
		this.colorRenderBuffer = colorRenderBuffer;
	}

	public int getDepthRenderBuffer()
	{
		return depthRenderBuffer;
	}

	public void setDepthRenderBuffer(int depthRenderBuffer)
	{
		this.depthRenderBuffer = depthRenderBuffer;
	}

	public boolean isResetFramebuffer()
	{
		return resetFramebuffer;
	}

	public void setResetFramebuffer(boolean resetFramebuffer)
	{
		this.resetFramebuffer = resetFramebuffer;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

}
