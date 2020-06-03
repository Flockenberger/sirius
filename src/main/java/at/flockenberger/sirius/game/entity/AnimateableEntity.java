package at.flockenberger.sirius.game.entity;

import java.util.HashMap;
import java.util.Map;

import at.flockenberger.sirius.engine.animation.Animation;

public abstract class AnimateableEntity extends Entity
{
	protected Map<String, Animation<?>> animationCache;
	protected Animation<?> activeAnimation;

	public AnimateableEntity()
	{
		animationCache = new HashMap<>();
	}

	public void addAnimation(String name, Animation<?> animation)
	{
		animationCache.putIfAbsent(name, animation);
	}

	public void removeAnimation(String name)
	{
		animationCache.remove(name);
	}

	public Animation<?> getAnimation(String name)
	{
		return animationCache.get(name);
	}

	public void setActiveAnimation(String name)
	{
		if (animationCache.containsKey(name))
		{
			activeAnimation = animationCache.get(name);
		}
	}
	
	public Animation<?> activeAnimation()
	{
		return activeAnimation;
	}
}
