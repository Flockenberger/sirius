package at.flockenberger.sirius.utillity;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import at.flockenberger.sirius.utillity.logging.SLogger;

/**
 * Utillity class.
 * 
 * @author Florian Wagner
 *
 */
public class SUtils {

	/**
	 * 
	 * Checks if the given {@link Object} <code> t </code> is null. <br>
	 * If the given object is null a new {@link NullPointerException} will be
	 * thrown.
	 * 
	 * @param t    the object to check for null
	 * @param what a parameter to describe what object was null
	 */
	public static boolean checkNull(Object t, String what) {
		if (t == null) {
			NullPointerException ex = new NullPointerException("The object of type: " + what + " was null!");
			SLogger.getSystemLogger().except(ex);
			SLogger.getSystemLogger().trace(ex.getStackTrace());
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given float value <code> f </code> is within the bounds of
	 * <code> min, max </code>. <br>
	 * If f is greater than max, f will be set to max. <br>
	 * If f is less than min, f will be set to min.
	 * 
	 * @param f   the value to check the bounds
	 * 
	 * @param min the inclusive min
	 * 
	 * @param max the inclusive max
	 * 
	 * @return the bound value or the value itself if it is within range
	 */
	public static float putInBounds(float f, float min, float max) {
		if (f > max)
			f = max;
		if (f < min)
			f = min;
		return f;
	}

	/**
	 * Clones the given {@link ByteBuffer} into a new {@link ByteBuffer}. <br>
	 * The original buffer is preserved.
	 * 
	 * @param original the {@link ByteBuffer} to copy
	 * @return a clone of the given buffer
	 */
	public static ByteBuffer clone(ByteBuffer original) {
		ByteBuffer clone = BufferUtils.createByteBuffer(original.capacity());
		original.rewind();// copy from the beginning
		clone.put(original);
		original.rewind();
		clone.flip();
		return clone;
	}

	/**
	 * Checks if the given index <code> index </code>is within 0 and max.
	 * 
	 * @param index
	 * @param max
	 */
	public static void checkIndex(int index, int max) {
		if (index < 0 || index >= max)
			throw new IndexOutOfBoundsException();
	}

}
