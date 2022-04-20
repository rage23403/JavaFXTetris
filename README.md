# ConsoleTetris

CURRENTLY NO RELEASES - since JavaFX is no longer included in the base JRE/JDK I haven't been able to create a compiled version of JavaFXTetris that is usable everywhere.

To compile and run. Compile on inputManager.java

Game's "Garbo()" method is purely for debugging in BlueJ because sometimes it can run slowly and needs a garbage collection.

inputManager is the main class and has become the entire interface. Game still exists because the logic and movement will still be running in a separate thread.

Pause button pauses the game's thread. Reset button resets the game and current score.
