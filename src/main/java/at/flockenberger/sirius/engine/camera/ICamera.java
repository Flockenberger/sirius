package at.flockenberger.sirius.engine.camera;

import org.joml.Matrix4f;

import at.flockenberger.sirius.engine.render.Renderer;

/**
 * <h1>ICamera</h1><br>
 * A simple base interface for any kind of camera.<br>
 * 
 * @author Florian Wagner
 *
 */
public interface ICamera
{

	/**
	 * @return the projection matrix for this camera
	 */
	public Matrix4f getProjectionMatrix();

	/**
	 * @return the view matrix for this camera
	 */
	public Matrix4f getViewMatrix();

	/**
	 * This method is used in the internal renderer to upload to the shader.
	 * 
	 * @return the combined view and projection matrix
	 */
	public Matrix4f getViewProjectionMatrix();

	/**
	 * Called by the render engine {@link Renderer} to update the view and
	 * projection matrices. <br>
	 * This method important to update the new dimensions when the framebuffer is
	 * resized.
	 * 
	 * @param width  the width of the view
	 * @param height the height of the view
	 */
	public void recalculateMatrices(int width, int height);
	
	
}
