package at.flockenberger.sirius.engine.event.gui;

import at.flockenberger.sirius.engine.gui.GUIBase;

@FunctionalInterface
public interface GUIExitedListener
{ public void onGUIExited(GUIBase gui); }
