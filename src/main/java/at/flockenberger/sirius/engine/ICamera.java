package at.flockenberger.sirius.engine;

import org.joml.Matrix4f;

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

}
