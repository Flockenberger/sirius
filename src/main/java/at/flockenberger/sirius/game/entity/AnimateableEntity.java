package at.flockenberger.sirius.game.entity;

import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.engine.animation.Animation;
import at.flockenberger.sirius.utillity.SUtils;

/**
 * <h1>AnimateableEntity</h1><br>
 * An extension to the {@link Entity} class which also stores a {@link Map} of
 * {@link Animation}.<br>
 * It also has an {@link #activeAnimation()} animation.<br>
 * This allows to easily store any kind of animation that can be used by this
 * {@link Entity}.
 * 
 * @author Florian Wagner
 * @see Entity
 * @see Animation
 */
public abstract class AnimateableEntity extends Entity
{
	/**
	 * the animation stored inside this entity
	 */
	protected Map<String, Animation<?>> animationCache;

	/**
	 * the currently active animation <br>
	 * one can retrieve it with {@link #activeAnimation()}
	 */
	protected Animation<?> activeAnimation;

	/**
	 * Constructor.<br>
	 * Initializes the {@link #animationCache}
	 */
	public AnimateableEntity()
	{
		animationCache = new HashMap<>();
	}

	/**
	 * Adds an animation to this entity.<br>
	 * The <code>name</code> parameter is the key to find the animation inside the
	 * cache.
	 * 
	 * @param name      the key value to store this animation
	 * @param animation the animation to store
	 */
	public void addAnimation(String name, Animation<?> animation)
	{
		SUtils.checkNull(name, String.class);
		SUtils.checkNull(animation, Animation.class);

		animationCache.putIfAbsent(name, animation);
	}

	/**
	 * Removes the animation that is cached with the given key <code>name</code>.
	 * 
	 * @param name the name of the animation to remove
	 */
	public void removeAnimation(String name)
	{
		if (animationCache.containsKey(name))
			animationCache.remove(name);
	}

	/**
	 * Returns the {@link Animation} which corresponds with the given key
	 * <code>name</code>.
	 * 
	 * @param name the name of the animation
	 * @return the found animation or null
	 */
	public Animation<?> getAnimation(String name)
	{
		return animationCache.get(name);
	}

	/**
	 * Sets the {@link Animation} which corresponds to the given key
	 * <code>name</code> as the active animation.<br>
	 * The active animation can be retrieved with {@link #activeAnimation()} <br>
	 * If the given key does not correspond to any {@link Animation} object the
	 * currently active animation wont change!
	 * 
	 * @param name the name of the animation to set active
	 */
	public void setActiveAnimation(String name)
	{
		if (animationCache.containsKey(name))
		{
			activeAnimation = animationCache.get(name);
		}
	}

	/**
	 * @return the active {@link Animation}. If no animation is set to active this
	 *         method may return null!
	 */
	public Animation<?> activeAnimation()
	{
		return activeAnimation;
	}
}
