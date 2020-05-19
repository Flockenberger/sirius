package at.flockenberger.sirius.engine;

import at.flockenberger.sirius.utillity.SUtils;

public class GUICam extends Camera
{

	@Override
	public void recalculateMatrices(int width, int height)
	{
		projection = SUtils.orthographic(0f, width, height, 0f, -1f, 1f);
		
	}
}
