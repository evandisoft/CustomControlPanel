A customizable control panel that allows the user to create a list of buttons and customize them to run commands whose output is placed into the output area below the buttons.

The user can also save the "session", which amounts to the current button configuration, window size, and window position, into the chosen session file and load them later.

To load a configuration from the command-line on startup:
java --jar CustomControlPanel.jar session-filename

Motivation:
Being able to easily construct custom UI's for controlling, in a basic way, set's of commands that I want to be performed at the click of a button, while displaying the output of those commands.

This was created quickly for a particular purpose and is not meant to be indicative of perfect program design. I will perhaps come back and refactor things and add new features.


