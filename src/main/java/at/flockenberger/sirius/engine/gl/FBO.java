package at.flockenberger.sirius.engine.gl;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glCheckFramebufferStatusEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glDeleteFramebuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import at.flockenberger.sirius.engine.IBindable;
import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Window;
import at.flockenberger.sirius.engine.texture.ITextureBase;
import at.flockenberger.sirius.engine.texture.Texture;
import at.flockenberger.sirius.engine.texture.UV;
import at.flockenberger.sirius.utillity.exceptions.SiriusException;

/**
 * <h1>FBO</h1><br>
 * FBO stands for Frame-Buffer-Object.
 * 
 * @author Florian Wagner
 *
 */
public class FBO implements ITextureBase, IBindable, IFreeable
{

	public static boolean isSupported()
	{
		return GL.getCapabilities().GL_EXT_framebuffer_object;
	}

	/** The ID of the FBO in use */
	protected int id;
	protected Texture texture;
	protected boolean ownsTexture;
	protected UV uv = new UV(0, 1, 1, 0);

	public FBO(Texture texture, boolean ownsTexture)
	{
		this.texture = texture;
		this.ownsTexture = ownsTexture;
		if (!isSupported())
		{
			throw new SiriusException("FBO extension not supported in hardware");
		}
		texture.bind();
		id = glGenFramebuffersEXT();
		glBindFramebufferEXT(GL_FRAMEBUFFER, id);
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture.getID(), 0);
		int result = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER);
		if (result != GL_FRAMEBUFFER_COMPLETE)
		{
			glBindFramebufferEXT(GL_FRAMEBUFFER, 0);
			glDeleteFramebuffers(id);
			throw new SiriusException("exception " + result + " when checking FBO status");
		}
		glBindFramebufferEXT(GL_FRAMEBUFFER, 0);
	}

	/**
	 * Advanced constructor which creates a frame buffer from a texture; the
	 * framebuffer does not "own" the texture and thus calling dispose() on this
	 * framebuffer will not destroy the texture.
	 * 
	 * @param texture the texture to use
	 */
	public FBO(Texture texture)
	{
		this(texture, false);
	}

	public FBO()
	{
		this(Texture.createTexture(Window.getActiveFrameBufferSize()[0], Window.getActiveFrameBufferSize()[1]), true);
	}

	@Override
	public void bind()
	{
		if (id == 0)
			throw new IllegalStateException("can't use FBO as it has been destroyed..");
		glViewport(0, 0, getWidth(), getHeight());
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, id);
	}

	@Override
	public void unbind()
	{
		if (id == 0)
			return;
		int[] size = Window.getActiveSize();

		glViewport(0, 0, size[0], size[1]);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}

	@Override
	public int getWidth()
	{
		return texture.getWidth();
	}

	@Override
	public int getHeight()
	{
		return texture.getHeight();

	}

	@Override
	public UV getUV()
	{
		return uv;
	}

	@Override
	public Texture getTexture()
	{
		return texture;
	}

	@Override
	public int getID()
	{
		return id;
	}

	@Override
	public void free()
	{
		if (id == 0)
			return;
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glDeleteFramebuffersEXT(id);
		if (ownsTexture)
			texture.free();
		id = 0;
	}

}
