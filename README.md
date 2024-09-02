Mineshot
========

A Minecraft mod for creating high resolution screenshots.

Commands
--------
- `/zoom [n]`
    - Sets the orthographic view's zoom value to `n`. In top view, the number of blocks visible vertically on-screen is equal to `zoom * 2`.
    - Prints the current zoom value if `n` is not set.
- `/hugescreenshot [width] [height]`
   - Takes a huge screenshot with optional `width` and `height` values.
- `/minimap <x> <z> <x1> <z1> [y] [pixels]` (Singleplayer only)
   - Takes a screenshot that can be used as a minimap. Automatically teleports the player and sets `zoom`.
   - `x` and `z` are the game coordinate of one corner of the minimap.
   - `x1` and `z1` are the coordinates of the opposite corner.
   - `y` can be optionally set to change the y coordinate the player is teleported to.
   - `pixels` determines the number of pixels each block length takes up. Defaults to 16.

### Examples
* To make a full resolution minimap of the Gold Mine, use `/minimap -87 -399 45 -268 150 16`. This is equivalent to the following while in orthographic top view:
  1. `/tp -21.0 150 -333.5`
  2. `/zoom 65.5`
  3. `/hugescreenshot 2112 2096`