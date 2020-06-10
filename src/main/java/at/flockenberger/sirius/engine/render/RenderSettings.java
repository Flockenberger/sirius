package at.flockenberger.sirius.engine.render;

import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.utillity.BaseContainer;

public class RenderSettings extends BaseContainer
{

	private static final long serialVersionUID = -3603031163877115366L;
	public static final String BACKGROUND = "rd_background";
	public static final String ZOOM = "rd_zoom";

	public RenderSettings()
	{
		putDefaults();
	}

	private void putDefaults()
	{
		setColor(BACKGROUND, Color.BRIGHT_PURPLE);
		setFloat(ZOOM, 150f);
	}
}
