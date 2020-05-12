package at.flockenberger.sirius;

import org.joml.Matrix4f;

public interface ICamera
{

	public Matrix4f getProjectionMatrix();

	public Matrix4f getViewMatrix();
}
