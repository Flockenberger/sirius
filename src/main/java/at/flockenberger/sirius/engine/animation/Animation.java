package at.flockenberger.sirius.engine.animation;

import java.util.List;

import at.flockenberger.sirius.engine.IFreeable;
import at.flockenberger.sirius.engine.Sirius;
import at.flockenberger.sirius.utillity.SUtils;

public class Animation<T> implements IFreeable
{

	protected T[] keyFrames;
	private float frameDuration;
	private float animationDuration;
	private float timer;

	private int lastFrameNumber;
	private float lastStateTime;

	private AnimationMode aniMode = AnimationMode.NORMAL;

	public Animation(float frameDuration, List<? extends T> keyFrames)
	{
		this(frameDuration, keyFrames, AnimationMode.NORMAL);
	}

	@SafeVarargs
	public Animation(float frameDuration, T... keyFrames)
	{
		this.frameDuration = frameDuration;
		setKeyFrames(keyFrames);
	}

	public Animation(float f, T[] walkFrames, AnimationMode playMode)
	{
		this.frameDuration = f;
		setKeyFrames(walkFrames);
		setAnimationMode(playMode);

	}

	public Animation(float frameDuration, List<? extends T> keyFrames, AnimationMode playMode)
	{
		this.frameDuration = frameDuration;
		@SuppressWarnings("unchecked")
		T[] frames = (T[]) keyFrames.toArray();
		setKeyFrames(frames);
		setAnimationMode(playMode);
	}

	/**
	 * @return the next keyframe to play. <br>
	 *         This method uses the internal timer.
	 */
	public T getNextFrame()
	{
		timer += Sirius.timer.getDelta();
		return getKeyFrame(timer);
	}

	public T getKeyFrame(float stateTime, boolean looping)
	{
		AnimationMode oldPlayMode = aniMode;
		if (looping && (aniMode == AnimationMode.NORMAL || aniMode == AnimationMode.REVERSED))
		{
			if (aniMode == AnimationMode.NORMAL)
				aniMode = AnimationMode.LOOP;
			else
				aniMode = AnimationMode.LOOP_REVERSED;
		} else if (!looping && !(aniMode == AnimationMode.NORMAL || aniMode == AnimationMode.REVERSED))
		{
			if (aniMode == AnimationMode.LOOP_REVERSED)
				aniMode = AnimationMode.REVERSED;
			else
				aniMode = AnimationMode.LOOP;
		}

		T frame = getKeyFrame(stateTime);
		aniMode = oldPlayMode;
		return frame;
	}

	public T getKeyFrame(float stateTime)
	{
		int frameNumber = getKeyFrameIndex(stateTime);
		return keyFrames[frameNumber];
	}

	public int getKeyFrameIndex(float stateTime)
	{
		if (keyFrames.length == 1)
			return 0;

		int frameNumber = (int) (stateTime / frameDuration);
		switch (aniMode)
		{
		case NORMAL:
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		case LOOP:
			frameNumber = frameNumber % keyFrames.length;
			break;
		case REVERSED:
			frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
			break;
		case LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.length;
			frameNumber = keyFrames.length - frameNumber - 1;
			break;
		}

		lastFrameNumber = frameNumber;
		lastStateTime = stateTime;

		return frameNumber;
	}

	public T[] getKeyFrames()
	{ return keyFrames; }

	public void setKeyFrame(T key, int index)
	{
		SUtils.checkNull(key, "Animation: KeyFrame");
		SUtils.checkIndex(index, 0, keyFrames.length - 1);
		SUtils.checkNull(keyFrames, "Animation: KeyFrames");

		keyFrames[index] = key;
	}

	public void setKeyFrames(@SuppressWarnings("unchecked") T... keyFrames)
	{
		SUtils.checkNull(keyFrames, "Animation: KeyFrames");
		this.keyFrames = keyFrames;
		this.animationDuration = keyFrames.length * frameDuration;
	}

	public AnimationMode getAnimationMode()
	{ return aniMode; }

	public void setAnimationMode(AnimationMode playMode)
	{ this.aniMode = playMode; }

	public boolean isAnimationFinished(float stateTime)
	{
		int frameNumber = (int) (stateTime / frameDuration);
		return keyFrames.length - 1 < frameNumber;
	}

	public void setFrameDuration(float frameDuration)
	{
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
	}

	public float getFrameDuration()
	{ return frameDuration; }

	public float getAnimationDuration()
	{ return animationDuration; }

	public int getLastFrameNumber()
	{ return lastFrameNumber; }

	public float getLastStateTime()
	{ return lastStateTime; }

	@Override
	public void free()
	{
		this.keyFrames = null;
	}

}