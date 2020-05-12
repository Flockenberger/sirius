package at.flockenberger.sirius.engine.gl.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.GL_ACTIVE_UNIFORMS;
import static org.lwjgl.opengl.GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetActiveUniform;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import at.flockenberger.sirius.engine.gl.VertexAttribute;
import at.flockenberger.sirius.utillity.SUtils;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>ShaderProgram</h1><br>
 * Represents a shader program.
 * 
 * @author Florian Wagner
 *
 */
public class ShaderProgram
{
	private int id = -1;
	private List<Shader> shader;

	private static FloatBuffer fbuf16;
	private static IntBuffer ibuf4;
	private List<VertexAttribute> attributes;

	protected HashMap<String, Integer> uniforms = new HashMap<String, Integer>();

	public ShaderProgram()
	{
		shader = new ArrayList<Shader>();

	}

	public ShaderProgram(Shader... shaders)
	{
		this();
		shader = Arrays.asList(shaders);
	}

	public ShaderProgram(String vertexShader, String fragmentShader)
	{
		this(vertexShader, fragmentShader, null);

	}

	public ShaderProgram(String vertexShader, String fragmentShader, List<VertexAttribute> attr)
	{
		this();
		if (vertexShader == null || vertexShader.isEmpty())
			return;
		if (fragmentShader == null || fragmentShader.isEmpty())
			return;

		attributes = attr;

		VertexShader vS = new VertexShader(vertexShader);
		FragmentShader fS = new FragmentShader(fragmentShader);
		shader = Arrays.asList(new Shader[] { vS, fS });
		createProgram();
	}

	/**
	 * Tells OpenGL to use this shader program.
	 * 
	 */
	public void useProgram()
	{
		GL20.glUseProgram(getID());
	}

	/**
	 * Tells OpenGL to not use the program anymore.
	 */
	public void closeProgram()
	{
		GL20.glUseProgram(0);
	}

	/**
	 * Frees the shader program.
	 */
	public void free()
	{
		GL20.glDeleteProgram(getID());
	}

	public void createProgram()
	{
		SLogger.getSystemLogger().info("Creating Shader Program");
		if (getID() != -1)
		{
			GL20.glDeleteProgram(id);
		}

		this.id = glCreateProgram();

		if (attributes != null)
		{
			for (VertexAttribute a : attributes)
			{
				if (a != null)
					glBindAttribLocation(id, a.getLocation(), a.getName());
			}
		}

		for (Shader s : shader)
		{
			s.compileShader();
			s.attachShader(getID());
		}

		glLinkProgram(getID());

		int success = glGetProgrami(id, GL_LINK_STATUS);
		if (success == 0)
		{
			String infoLog = glGetProgramInfoLog(getID());
			SLogger.getSystemLogger().error("LINKING_FAILED\n" + infoLog);
		}

		fetchUniforms();

		for (Shader s : shader)
			s.free();

	}

	/**
	 * Binds the fragment out color variable.
	 *
	 * @param number Color number you want to bind
	 * @param name   Variable name
	 */
	public void bindFragmentDataLocation(int number, CharSequence name)
	{
		glBindFragDataLocation(id, number, name);
	}

	/**
	 * Gets the location of an attribute variable with specified name.
	 *
	 * @param name Attribute name
	 *
	 * @return Location of the attribute
	 */
	public int getAttributeLocation(CharSequence name)
	{
		return glGetAttribLocation(id, name);
	}

	/**
	 * Enables a vertex attribute.
	 *
	 * @param location Location of the vertex attribute
	 */
	public void enableVertexAttribute(int location)
	{
		glEnableVertexAttribArray(location);
	}

	/**
	 * Disables a vertex attribute.
	 *
	 * @param location Location of the vertex attribute
	 */
	public void disableVertexAttribute(int location)
	{
		glDisableVertexAttribArray(location);
	}

	/**
	 * Sets the vertex attribute pointer.
	 *
	 * @param location Location of the vertex attribute
	 * @param size     Number of values per vertex
	 * @param stride   Offset between consecutive generic vertex attributes in bytes
	 * @param offset   Offset of the first component of the first generic vertex
	 *                 attribute in bytes
	 */
	public void pointVertexAttribute(int location, int size, int stride, int offset)
	{
		glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
	}

	private void fetchUniforms()
	{
		int len = glGetProgrami(id, GL_ACTIVE_UNIFORMS);
		// max length of all uniforms stored in program
		int strLen = glGetProgrami(id, GL_ACTIVE_UNIFORM_MAX_LENGTH);
		IntBuffer b1 = BufferUtils.createIntBuffer(100);
		IntBuffer b2 = BufferUtils.createIntBuffer(100);

		for (int i = 0; i < len; i++)
		{
			String name = glGetActiveUniform(id, i, strLen, b1, b2);
			int _id = glGetUniformLocation(id, name);
			uniforms.put(name, _id);
		}
	}

	/**
	 * Returns the location of the uniform by name. If the uniform is not found and
	 * we are in strict mode, an IllegalArgumentException will be thrown, otherwise
	 * -1 will be returned if no active uniform by that name exists.
	 * 
	 * @param name the uniform name
	 * @return the ID (location) in the shader program
	 */
	public int getUniformLocation(String name)
	{
		int location = -1;
		Integer locI = uniforms.get(name);
		if (locI == null)
		{ // maybe it's not yet cached?
			location = glGetUniformLocation(id, name);
			uniforms.put(name, location);
		} else
			location = locI.intValue();
		// throw an exception if not found...
		if (location == -1)
			throw new IllegalArgumentException(
					"no active uniform by name '" + name + "' " + "(disable strict compiling to suppress warnings)");
		return location;
	}

	/**
	 * Sets the uniform variable for specified location.
	 *
	 * @param location Uniform location
	 * @param value    Value to set
	 */
	public void setUniformMatrix(int location, Matrix4f value)
	{
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			SUtils.toBuffer(value, buffer);

			glUniformMatrix4fv(location, false, buffer);
		}
	}

	/**
	 * Creates and returns an array for all active uniforms that were found when
	 * linking the program.
	 * 
	 * @return an array list of active uniform names
	 */
	public String[] getUniformNames()
	{
		return uniforms.keySet().toArray(new String[uniforms.size()]);
	}

	/**
	 * Returns true if an active uniform by the given name was found when linking.
	 * 
	 * @param name the active uniform name
	 * @return true if the uniform was found
	 */
	public boolean hasUniform(String name)
	{
		return uniforms.containsKey(name);
	}

	/**
	 * Sets the uniform variable for specified location.
	 *
	 * @param location Uniform location
	 * @param value    Value to set
	 */
	public void setUniform(int location, int value)
	{
		glUniform1i(location, value);
	}

	public int getID()
	{
		return this.id;
	}

	public List<Shader> getShader()
	{
		return shader;
	}

	/** ----- UNIFORM GETTERS ----- */

	private FloatBuffer uniformf(int loc)
	{
		if (fbuf16 == null)
			fbuf16 = BufferUtils.createFloatBuffer(16);
		fbuf16.clear();
		if (loc == -1)
			return fbuf16;
		getUniform(loc, fbuf16);
		return fbuf16;
	}

	private IntBuffer uniformi(int loc)
	{
		if (ibuf4 == null)
			ibuf4 = BufferUtils.createIntBuffer(4);
		ibuf4.clear();
		if (loc == -1)
			return ibuf4;
		getUniform(loc, ibuf4);
		return ibuf4;
	}

	/**
	 * Retrieves data from a uniform and places it in the given buffer.
	 * 
	 * @param loc the location of the uniform
	 * @param buf the buffer to place the data
	 */
	public void getUniform(int loc, FloatBuffer buf)
	{
		GL20.glGetUniformfv(id, loc, buf);

	}

	/**
	 * Retrieves data from a uniform and places it in the given buffer.
	 * 
	 * @param loc the location of the uniform
	 * @param buf the buffer to place the data
	 */
	public void getUniform(int loc, IntBuffer buf)
	{
		GL20.glGetUniformiv(id, loc, buf);
	}

	/**
	 * Retrieves data from a uniform and places it in the given buffer. If strict
	 * mode is enabled, this will throw an IllegalArgumentException if the given
	 * uniform is not 'active' -- i.e. if GLSL determined that the shader isn't
	 * using it. If strict mode is disabled, this method will return <tt>true</tt>
	 * if the uniform was found, and <tt>false</tt> otherwise.
	 * 
	 * @param name the name of the uniform
	 * @param buf  the buffer to place the data
	 * @return true if the uniform was found, false if there is no active uniform by
	 *         that name
	 */
	public boolean getUniform(String name, FloatBuffer buf)
	{
		int id = getUniformLocation(name);
		if (id == -1)
			return false;
		getUniform(id, buf);
		return true;
	}

	/**
	 * Retrieves data from a uniform and places it in the given buffer. If strict
	 * mode is enabled, this will throw an IllegalArgumentException if the given
	 * uniform is not 'active' -- i.e. if GLSL determined that the shader isn't
	 * using it. If strict mode is disabled, this method will return <tt>true</tt>
	 * if the uniform was found, and <tt>false</tt> otherwise.
	 * 
	 * @param name the name of the uniform
	 * @param buf  the buffer to place the data
	 * @return true if the uniform was found, false if there is no active uniform by
	 *         that name
	 */
	public boolean getUniform(String name, IntBuffer buf)
	{
		int id = getUniformLocation(name);
		if (id == -1)
			return false;
		getUniform(id, buf);
		return true;
	}

	/**
	 * A convenience method to retrieve an integer/sampler2D uniform. The return
	 * values are undefined if the uniform is not found.
	 * 
	 * @param loc the uniform location
	 * @return the value
	 */
	public int getUniform1i(int loc)
	{
		return uniformi(loc).get(0);
	}

	/**
	 * A convenience method to retrieve an integer/sampler2D uniform. The return
	 * values are undefined if the uniform is not found.
	 * 
	 * @param name the uniform location
	 * @return the value
	 */
	public int getUniform1i(String name)
	{
		return getUniform1i(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve an ivec2 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(int, IntBuffer) with a shared
	 * buffer.
	 * 
	 * @param loc the uniform location
	 * @return a newly created int[] array with 2 elements; e.g. (x, y)
	 */
	public int[] getUniform2i(int loc)
	{
		IntBuffer buf = uniformi(loc);
		return new int[] { buf.get(0), buf.get(1) };
	}

	/**
	 * A convenience method to retrieve an ivec2 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(int, IntBuffer) with a shared
	 * buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the uniform name
	 * @return a newly created int[] array with 2 elements; e.g. (x, y)
	 */
	public int[] getUniform2i(String name)
	{
		return getUniform2i(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve an ivec3 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(String, IntBuffer) with a
	 * shared buffer.
	 * 
	 * @param loc the name of the uniform
	 * @return a newly created int[] array with 3 elements; e.g. (x, y, z)
	 */
	public int[] getUniform3i(int loc)
	{
		IntBuffer buf = uniformi(loc);
		return new int[] { buf.get(0), buf.get(1), buf.get(2) };
	}

	/**
	 * A convenience method to retrieve an ivec3 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(String, IntBuffer) with a
	 * shared buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the name of the uniform
	 * @return a newly created int[] array with 3 elements; e.g. (x, y, z)
	 */
	public int[] getUniform3i(String name)
	{
		return getUniform3i(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve an ivec4 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(String, IntBuffer) with a
	 * shared buffer.
	 * 
	 * @param loc the location of the uniform
	 * @return a newly created int[] array with 2 elements; e.g. (r, g, b, a)
	 */
	public int[] getUniform4i(int loc)
	{
		IntBuffer buf = uniformi(loc);
		return new int[] { buf.get(0), buf.get(1), buf.get(2), buf.get(3) };
	}

	/**
	 * A convenience method to retrieve an ivec4 uniform; for maximum performance
	 * and memory efficiency you should use getUniform(String, IntBuffer) with a
	 * shared buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the name of the uniform
	 * @return a newly created int[] array with 2 elements; e.g. (r, g, b, a)
	 */
	public int[] getUniform4i(String name)
	{
		return getUniform4i(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve a float uniform.
	 * 
	 * @param location the location of the uniform
	 * @return the value
	 */
	public float getUniform1f(int loc)
	{
		return uniformf(loc).get(0);
	}

	/**
	 * A convenience method to retrieve a float uniform. The return values are
	 * undefined if the uniform is not found.
	 * 
	 * @param name the uniform name
	 * @return the value
	 */
	public float getUniform1f(String name)
	{
		return getUniform1f(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve a vec2 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer.
	 * 
	 * @param location the location of the uniform
	 * @return a newly created float[] array with 2 elements; e.g. (x, y)
	 */
	public float[] getUniform2f(int loc)
	{
		FloatBuffer buf = uniformf(loc);
		return new float[] { buf.get(0), buf.get(1) };
	}

	/**
	 * A convenience method to retrieve a vec2 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the name of the uniform
	 * @return a newly created float[] array with 2 elements; e.g. (x, y)
	 */
	public float[] getUniform2f(String name)
	{
		return getUniform2f(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve a vec3 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer.
	 * 
	 * @param location the location of the uniform
	 * @return a newly created float[] array with 3 elements; e.g. (x, y, z)
	 */
	public float[] getUniform3f(int loc)
	{
		FloatBuffer buf = uniformf(loc);
		return new float[] { buf.get(0), buf.get(1), buf.get(2) };
	}

	/**
	 * A convenience method to retrieve a vec3 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the name of the uniform
	 * @return a newly created float[] array with 3 elements; e.g. (x, y, z)
	 */
	public float[] getUniform3f(String name)
	{
		return getUniform3f(getUniformLocation(name));
	}

	/**
	 * A convenience method to retrieve a vec4 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer.
	 * 
	 * @param location the location of the uniform
	 * @return a newly created float[] array with 4 elements; e.g. (r, g, b, a)
	 */
	public float[] getUniform4f(int loc)
	{
		FloatBuffer buf = uniformf(loc);
		return new float[] { buf.get(0), buf.get(1), buf.get(2), buf.get(3) };
	}

	/**
	 * A convenience method to retrieve a vec4 uniform; for maximum performance and
	 * memory efficiency you should use getUniform(String, FloatBuffer) with a
	 * shared buffer. The return values are undefined if the uniform is not found.
	 * 
	 * @param name the name of the uniform
	 * @return a newly created float[] array with 4 elements; e.g. (r, g, b, a)
	 */
	public float[] getUniform4f(String name)
	{
		return getUniform4f(getUniformLocation(name));
	}

	/** ----- UNIFORM LOCATION SETTERS ----- */

	/**
	 * Sets the value of a float uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param f   the float value
	 */
	public void setUniformf(int loc, float f)
	{
		if (loc == -1)
			return;
		glUniform1f(loc, f);
	}

	/**
	 * Sets the value of a vec2 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / tex.s
	 * @param b   vec.y / tex.t
	 */
	public void setUniformf(int loc, float a, float b)
	{
		if (loc == -1)
			return;
		glUniform2f(loc, a, b);
	}

	/**
	 * Sets the value of a vec3 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / color.r / tex.s
	 * @param b   vec.y / color.g / tex.t
	 * @param c   vec.z / color.b / tex.p
	 */
	public void setUniformf(int loc, float a, float b, float c)
	{
		if (loc == -1)
			return;
		glUniform3f(loc, a, b, c);
	}

	/**
	 * Sets the value of a vec4 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / color.r
	 * @param b   vec.y / color.g
	 * @param c   vec.z / color.b
	 * @param d   vec.w / color.a
	 */
	public void setUniformf(int loc, float a, float b, float c, float d)
	{
		if (loc == -1)
			return;
		glUniform4f(loc, a, b, c, d);
	}

	/**
	 * Sets the value of an int or sampler2D uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param i   the integer / active texture (e.g. 0 for TEXTURE0)
	 */
	public void setUniformi(int loc, int i)
	{
		if (loc == -1)
			return;
		glUniform1i(loc, i);
	}

	/**
	 * Sets the value of a ivec2 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / tex.s
	 * @param b   vec.y / tex.t
	 */
	public void setUniformi(int loc, int a, int b)
	{
		if (loc == -1)
			return;
		glUniform2i(loc, a, b);
	}

	/**
	 * Sets the value of a ivec3 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / color.r
	 * @param b   vec.y / color.g
	 * @param c   vec.z / color.b
	 */
	public void setUniformi(int loc, int a, int b, int c)
	{
		if (loc == -1)
			return;
		glUniform3i(loc, a, b, c);
	}

	/**
	 * Sets the value of a ivec4 uniform.
	 * 
	 * @param loc the location of the uniform
	 * @param a   vec.x / color.r
	 * @param b   vec.y / color.g
	 * @param c   vec.z / color.b
	 * @param d   vec.w / color.a
	 */
	public void setUniformi(int loc, int a, int b, int c, int d)
	{
		if (loc == -1)
			return;
		glUniform4i(loc, a, b, c, d);
	}

	/** ----- UNIFORM STRING SETTERS ----- */

	/**
	 * Sets the value of a float uniform.
	 * 
	 * @param name the name of the uniform
	 * @param f    the float value
	 */
	public void setUniformf(String name, float f)
	{
		setUniformf(getUniformLocation(name), f);
	}

	/**
	 * Sets the value of a vec2 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / tex.s
	 * @param b    vec.y / tex.t
	 */
	public void setUniformf(String name, float a, float b)
	{
		setUniformf(getUniformLocation(name), a, b);
	}

	/**
	 * Sets the value of a vec3 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / color.r / tex.s
	 * @param b    vec.y / color.g / tex.t
	 * @param c    vec.z / color.b / tex.p
	 */
	public void setUniformf(String name, float a, float b, float c)
	{
		setUniformf(getUniformLocation(name), a, b, c);
	}

	/**
	 * Sets the value of a vec4 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / color.r
	 * @param b    vec.y / color.g
	 * @param c    vec.z / color.b
	 * @param d    vec.w / color.a
	 */
	public void setUniformf(String name, float a, float b, float c, float d)
	{
		setUniformf(getUniformLocation(name), a, b, c, d);
	}

	/**
	 * Sets the value of an int or sampler2D uniform.
	 * 
	 * @param name the name of the uniform
	 * @param i    the integer / active texture (e.g. 0 for TEXTURE0)
	 */
	public void setUniformi(String name, int i)
	{
		setUniformi(getUniformLocation(name), i);
	}

	/**
	 * Sets the value of a ivec2 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / tex.s
	 * @param b    vec.y / tex.t
	 */
	public void setUniformi(String name, int a, int b)
	{
		setUniformi(getUniformLocation(name), a, b);
	}

	/**
	 * Sets the value of a ivec3 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / color.r
	 * @param b    vec.y / color.g
	 * @param c    vec.z / color.b
	 */
	public void setUniformi(String name, int a, int b, int c)
	{
		setUniformi(getUniformLocation(name), a, b, c);
	}

	/**
	 * Sets the value of a ivec4 uniform.
	 * 
	 * @param name the name of the uniform
	 * @param a    vec.x / color.r
	 * @param b    vec.y / color.g
	 * @param c    vec.z / color.b
	 * @param d    vec.w / color.a
	 */
	public void setUniformi(String name, int a, int b, int c, int d)
	{
		setUniformi(getUniformLocation(name), a, b, c, d);
	}

	/** ----- MATRIX SETTERS ----- */

	public void setUniformMatrix(String name, boolean transpose, Matrix3f m)
	{
		setUniformMatrix(getUniformLocation(name), transpose, m);
	}

	public void setUniformMatrix(String name, boolean transpose, Matrix4f m)
	{
		setUniformMatrix(getUniformLocation(name), transpose, m);
	}

	public void setUniformMatrix(int loc, boolean transpose, Matrix3f m)
	{
		if (loc == -1)
			return;
		if (fbuf16 == null)
			fbuf16 = BufferUtils.createFloatBuffer(16);
		fbuf16.clear();

		SUtils.matrixToBuffer(m, fbuf16);
		fbuf16.flip();

		GL20.glUniformMatrix3fv(loc, transpose, fbuf16);
	}

	public void setUniformMatrix(int loc, boolean transpose, Matrix4f m)
	{
		if (loc == -1)
			return;
		if (fbuf16 == null)
			fbuf16 = BufferUtils.createFloatBuffer(16);
		fbuf16.clear();
		SUtils.matrixToBuffer(m, fbuf16);
		fbuf16.flip();
		GL20.glUniformMatrix4fv(loc, transpose, fbuf16);
	}

	/** ----- VECTOR SETTERS ----- */

	public void setUniformf(String name, Vector2f v)
	{
		setUniformf(getUniformLocation(name), v);
	}

	public void setUniformf(String name, Vector3f v)
	{
		setUniformf(getUniformLocation(name), v);
	}

	public void setUniformf(String name, Vector4f v)
	{
		setUniformf(getUniformLocation(name), v);
	}

	public void setUniformf(int loc, Vector2f v)
	{
		if (loc == -1)
			return;
		setUniformf(loc, v.x, v.y);
	}

	public void setUniformf(int loc, Vector3f v)
	{
		if (loc == -1)
			return;
		setUniformf(loc, v.x, v.y, v.z);
	}

	public void setUniformf(int loc, Vector4f v)
	{
		if (loc == -1)
			return;
		setUniformf(loc, v.x, v.y, v.z, v.w);
	}
}
