package at.flockenberger.sirius.engine.render.gl.shader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>ShaderCode</h1> <br>
 * Represents a GLSL shader code. This file is internally used for all types of
 * shaders.
 * 
 * @author Florian Wagner
 *
 */
public class ShaderCode
{

	private List<String> lines;

	/**
	 * Creates a new ShaderCode instance.
	 */
	public ShaderCode()
	{
		lines = new LinkedList<>();
	}

	/**
	 * Creates a new ShaderCode instance. <br>
	 * Additionally sets the shader code.
	 * 
	 * @param code the code of the shader
	 */
	public ShaderCode(String code)
	{
		this();
		setCode(code);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param code the code to copy
	 */
	public ShaderCode(ShaderCode code)
	{
		this();
		setCode(code.getCode());
	}

	/**
	 * @return all lines of this shader code
	 */
	public List<String> getLines()
	{ return lines; }

	/**
	 * Adds a line to the shader code.
	 * 
	 * @param line  the line to add
	 * @param index the line index to insert this line
	 */
	public void addLine(String line, int index)
	{
		lines.add(index, line);
	}

	/**
	 * Adds a line to the shader code.
	 * 
	 * @param line the line to add
	 */
	public void addLine(String line)
	{
		lines.add(line);
	}

	/**
	 * Adds the given list of lines to this shader code
	 * 
	 * @param lines the line to add
	 */
	public void addLines(List<String> lines)
	{
		for (String line : lines)
			addLine(line);
	}

	/**
	 * Removes the line at the given index
	 * 
	 * @param index the index of the line to remove
	 */
	public void removeLine(int index)
	{
		lines.remove(index);
	}

	/**
	 * Removes the given lines from this shader code
	 * 
	 * @param lines the lines to remove
	 */
	public void removeLines(List<String> lines)
	{
		for (String line : lines)
		{
			this.lines.remove(line);
		}
	}

	/**
	 * Removes the given line from the code
	 * 
	 * @param line theline to remove
	 */
	public void removeLine(String line)
	{
		lines.remove(line);
	}

	/**
	 * Replaces the line selected with <code> index </code> with the contents of
	 * <code> newline </code>
	 * 
	 * @param index   the index of the line to replace
	 * @param newline the new line
	 */
	public void replaceLine(int index, String newline)
	{
		removeLine(index);
		addLine(newline, index);
	}

	/**
	 * Inserts the given line at the given index
	 * 
	 * @param startIndex the index to insert the line into
	 * @param line       the line to insert
	 */
	public void insertLine(int startIndex, String line)
	{
		lines.add(startIndex, line);
	}

	/**
	 * Inserts the given lines at the given index
	 * 
	 * @param startindex the starting index
	 * @param lines      the lines to insert
	 */
	public void insertLines(int startindex, List<String> lines)
	{
		lines.addAll(startindex, lines);
	}

	/**
	 * Returns the line at the given index
	 * 
	 * @param index the index to retrieve the line
	 * @return the line if found
	 */
	public String getLine(int index)
	{
		return lines.get(index);
	}

	/**
	 * Sets the whole code of this shader.
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code)
	{ lines = new LinkedList<String>(Arrays.asList(code.split(System.lineSeparator()))); }

	/**
	 * @return the whole shader code
	 */
	public String getCode()
	{
		StringBuilder sb = new StringBuilder();
		for (String s : lines)
			sb.append(s + System.lineSeparator());
		return sb.toString();
	}

	/**
	 * Adds the given shader code to this shader code object
	 * 
	 * @param code the code to add
	 */
	public void addCode(ShaderCode code)
	{
		addLines(new LinkedList<String>(Arrays.asList(code.getCode().split(System.lineSeparator()))));

	}

	/**
	 * Inserts the given shader code at the given index
	 * 
	 * @param code       the code to insert
	 * @param startIndex the starting index
	 */
	public void insertCode(ShaderCode code, int startIndex)
	{
		List<String> li = code.getLines();
		insertLines(startIndex, li);
	}

	/**
	 * Removes the given shader code from this shader code object
	 * 
	 * @param code the code to remove
	 */
	public void removeCode(ShaderCode code)
	{
		List<String> li = code.getLines();
		removeLines(li);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		int c = 0;
		for (String line : lines)
		{
			sb.append("[" + c + "] : " + line + System.lineSeparator());
			c++;
		}
		return sb.toString();
	}

}
