package at.flockenberger.sirius.engine.camera;

import at.flockenberger.sirius.utillity.SUtils;

public class GUICamera extends Camera
{

	@Override
	public void recalculateMatrices(int width, int height)
	{
		projection = SUtils.orthographic(0f, width, height, 0f, -1f, 1f);

	}
}
