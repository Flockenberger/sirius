package at.flockenberger.sirius.engine.resource;

import java.nio.file.Path;

import at.flockenberger.sirius.engine.render.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.render.gl.shader.VertexShader;
import at.flockenberger.sirius.utillity.SUtils;

public class ShaderResource extends ResourceBase
{
	private String code;

	public ShaderResource(Path location)
	{
		super(location);

	}

	public VertexShader getAsVertexShader()
	{
		return new VertexShader(code);
	}

	public FragmentShader getAsFragmentShader()
	{
		return new FragmentShader(code);
	}

	@Override
	public void load()
	{
		code = SUtils.readFile(resourceLocation);
	}

}
