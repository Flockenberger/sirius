package at.flockenberger.sirius.utillity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.engine.graphic.Color;
import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * <h1>BaseContainer</h1><br>
 * The BaseContainer class is able to store any value to a corresponding key.
 * This method of storing data is used throughout Cardinal.
 * 
 * @author Florian Wagner
 *
 */
public class BaseContainer implements Serializable
{

	private static final long serialVersionUID = -3992739051020621845L;
	protected Map<Object, Object> container;

	/**
	 * Creates a new BaseContainer instance
	 */
	public BaseContainer()
	{
		container = new HashMap<Object, Object>();
	}

	/**
	 * Copy Constructor
	 * 
	 * @param bc the container to copy
	 */
	public BaseContainer(BaseContainer bc)
	{
		this.container = new HashMap<Object, Object>(bc.container);
	}

	/**
	 * Stores the given (generic) data <code> value </code> to the given key
	 * <code> key </code>
	 * 
	 * @param key   the key to store the data to
	 * @param value the value
	 */
	public void setData(Object key, Object value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a boolean parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setBool(Object key, boolean value)
	{
		container.put(key, value);
	}

	/**
	 * Stores an int parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setInt(Object key, int value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a String parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setString(Object key, String value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a long parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setLong(Object key, long value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a float parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setFloat(Object key, float value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a double parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setDouble(Object key, double value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a short parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setShort(Object key, short value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a byte parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setByte(Object key, byte value)
	{
		container.put(key, value);
	}

	/**
	 * Stores a {@link Color} parameter to the given key
	 * 
	 * @param key   the key to store the value to
	 * @param value the value to store
	 */
	public void setColor(Object key, Color value)
	{
		container.put(key, value);
	}

	/**
	 * Returns a boolean parameter of the given key.<br>
	 * This method returns always false if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public boolean getBool(Object key)
	{
		boolean ret = false;
		try
		{
			ret = (boolean) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getBool() : Boolean value was not found!");
			return false;
		}
	}

	/**
	 * Returns an int parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public int getInt(Object key)
	{
		int ret = -1;
		try
		{
			ret = (int) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getInt() : Integer value was not found!");
			return -1;
		}
	}

	/**
	 * Returns a {@link String} parameter of the given key.<br>
	 * This method returns always an empty {@link String} if the value was not
	 * found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public String getString(Object key)
	{
		String ret;
		try
		{
			ret = (String) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getString() : String value was not found!");
			return "";
		}
	}

	/**
	 * Returns a long parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public long getLong(Object key)
	{
		long ret = -1;
		try
		{
			ret = (long) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getLong() : Long value was not found!");

			return -1;
		}
	}

	/**
	 * Returns a float parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public float getFloat(Object key)
	{
		float ret = -1;
		try
		{
			ret = (float) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getFloat() : Float value was not found!");
			return -1f;
		}
	}

	/**
	 * Returns a double parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public double getDouble(Object key)
	{
		double ret = -1;
		try
		{
			ret = (double) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getDouble() : Double value was not found!");
			return -1d;
		}
	}

	/**
	 * Returns a short parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public short getShort(Object key)
	{
		short ret = -1;
		try
		{
			ret = (short) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getShort() : Short value was not found!");
			return -1;
		}
	}

	/**
	 * Returns a byte parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public byte getByte(Object key)
	{
		byte ret = -1;
		try
		{
			ret = (byte) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getByte() : Byte value was not found!");
			return -1;
		}
	}

	/**
	 * Returns a {@link Color} parameter of the given key.<br>
	 * This method returns always -1 if the value was not found!
	 * 
	 * @param key the key to get the value from
	 * @return the stored value or null if it was not found or not this datatype!
	 */
	public Color getColor(Object key)
	{
		Color ret = new Color();
		try
		{
			ret = (Color) container.get(key);
			return ret;
		} catch (Exception e)
		{
			SLogger.getSystemLogger().error("BaseContainer#getColor() : Color value was not found!");
			return ret;
		}
	}

	/*
	 * Gets the data with the given key.
	 */
	public Object getData(Object key)
	{
		return container.get(key);
	}

	/**
	 * @return a copy of this container
	 */
	public BaseContainer getDataInstance()
	{
		return new BaseContainer(this);
	}
}
