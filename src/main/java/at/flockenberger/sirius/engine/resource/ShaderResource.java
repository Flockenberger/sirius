package at.flockenberger.sirius.engine.resource;

import java.nio.file.Path;

import at.flockenberger.sirius.engine.render.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.render.gl.shader.Shader;
import at.flockenberger.sirius.engine.render.gl.shader.VertexShader;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>ShaderResource</h1><br>
 * The {@link ShaderResource} class is able to load {@link Shader} from a
 * file.<br>
 * With the convenience methods {@link #getAsFragmentShader()} and
 * {@link #getAsVertexShader()} a {@link VertexShader} or {@link FragmentShader}
 * can directly be accessed.
 * 
 * @author Florian Wagner
 *
 */
public class ShaderResource extends ResourceBase
{
	private String code;

	public ShaderResource(Path location)
	{
		super(location);

	}

	/**
	 * @return the loaded code as {@link VertexShader}
	 */
	public VertexShader getAsVertexShader()
	{ return new VertexShader(code); }

	/**
	 * @return the loaded code as {@link FragmentShader}
	 */
	public FragmentShader getAsFragmentShader()
	{ return new FragmentShader(code); }

	@Override
	public void load()
	{
		code = SUtils.readFile(resourceLocation);
	}

}
