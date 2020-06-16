package at.flockenberger.sirius.engine.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import at.flockenberger.sirius.engine.render.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.render.gl.shader.Shader;
import at.flockenberger.sirius.engine.render.gl.shader.VertexShader;
import at.flockenberger.sirius.utillity.logging.SLogger;

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

	public ShaderResource(InputStream location)
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
		try
		{
			InputStreamReader isReader = new InputStreamReader(resourceStream);
			// Creating a BufferedReader object
			BufferedReader reader = new BufferedReader(isReader);
			StringBuffer sb = new StringBuffer();
			String str;
			while ((str = reader.readLine()) != null)
			{
				sb.append(str + System.lineSeparator());
			}
			code = sb.toString();
			reader.close();
			isReader.close();
		} catch (IOException e)
		{
			SLogger.getSystemLogger().except(e);
		}
	}

}
