package at.flockenberger.sirius.engine.postprocess;

import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.engine.render.gl.shader.FragmentShader;
import at.flockenberger.sirius.engine.render.gl.shader.VertexShader;

public class TestFilter extends PostProcessingFilter
{
	private String vcode = "attribute vec2 v_coord;\r\n" + "uniform sampler2D fbo_texture;\r\n"
			+ "varying vec2 f_texcoord;\r\n" + "\r\n" + "void main(void) {\r\n"
			+ "  gl_Position = vec4(v_coord, 0.0, 1.0);\r\n" + "  f_texcoord = (v_coord + 1.0) / 2.0;\r\n" + "}";
	private String fcode = "uniform sampler2D fbo_texture;\r\n" + "uniform float offset;\r\n"
			+ "varying vec2 f_texcoord;\r\n" + "\r\n" + "void main(void) {\r\n" + "  vec2 texcoord = f_texcoord;\r\n"
			+ "  texcoord.x += sin(texcoord.y * 4*2*3.14159 + offset) / 100;\r\n"
			+ "  gl_FragColor = texture2D(fbo_texture, texcoord);\r\n" + "}";
	private String vcoord = "v_coord";
	private int vcoordloc;
	private String fboTex = "fbo_texture";
	private int fboTexloc;
	private String off = "offset";
	private int offsetloc;

	public TestFilter()
	{
		VertexShader vertex = new VertexShader(vcode);
		FragmentShader frament = new FragmentShader(fcode);
		setShader(vertex, frament);

	}

	@Override
	public void update()
	{
		if (!isCreated())
			createProgram();
		vcoordloc = getAttributeLocation(vcoord);
		fboTexloc = getUniformLocation(fboTex);
		offsetloc = getUniformLocation(off);
		pointVertexAttribute(vcoordloc, 2, 0, 0);

		setUniformf(offsetloc, (float) Sirius.timer.getTime());

	}

}
