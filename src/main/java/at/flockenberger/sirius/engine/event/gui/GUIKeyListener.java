package at.flockenberger.sirius.engine.event.gui;

import at.flockenberger.sirius.engine.input.KeyCode;

@FunctionalInterface
public interface GUIKeyListener
{ public void onKey(KeyCode keyCode); }
