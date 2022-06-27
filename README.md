# Defense Condition (DEFCON) desktop widget

The defense readiness condition (DEFCON) is an alert state used by the United States Armed Forces. The DEFCON system was developed by the Joint Chiefs of Staff (JCS) and unified and specified combatant commands. It prescribes five graduated levels of readiness (or states of alert) for the U.S. military. It increases in severity from DEFCON 5 (least severe) to DEFCON 1 (most severe) to match varying military situations, with DEFCON 1 signalling the outbreak of nuclear warfare.

This program written in Java acts as a desktop widget to display current DEFCON level. Current level can be set with control buttons. Current level is saved to config file and loaded after running program.

### THIS SOFTWARE IS NOT RELATED TO US GOVERNMENT / US MILITARY AND ACTS ONLY AS A HUMOROUS WIDGET.
![widget](https://user-images.githubusercontent.com/81091594/176002133-56971409-5584-49fb-8d3d-3e35d03521d9.png)

Audio was extracted from C&C Red Alert 2 and COD Black Ops and is being played while changing DEFCON level. Source also contains other unused audio files.

## Bugs / Notes
- Program is being displayed on top of the screen and centered vertically.
- Program may become misplaced when changing screen resolution. Can be fixed by exiting and running program again to match new resolution.
- Program's taskbar is being hidden. Program itself cannot be moved with mouse. There is a code section in constructor (mouseListener) which will allow moving widget after uncommenting.
