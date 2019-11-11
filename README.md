# DinosaurGame
This repository contains the code for the dinosaur game we are working on as a grade 12 ICS assignment.

# Planning

## NOTE:
- We will be using the issues tab as well as the project tab for scheduling and organizing tasks/problems we encounter.

## CONCEPT:
- Re-making dinosaur game from Google Chrome when the internet is down
- A guy/dinosaur/character is trying to avoid blocks at various heights in the air at random intervals.
- Score increases as time survives

## OBJECTIVES:
- Avoid the blocks
- Get as high of a score as possible

## RULES/GAME MECHANICS:
- There are 3 levels of difficulty, each one increases the speed of the game;
	- Easy
	- Medium
	- Hard
- The character can jump and crouch to avoid the 3 levels of obstacles;
	- Low block, only avoidable by jumping
	- Medium block, avoidable by crouching or jumping
	- High block, avoidable by doing nothing/crouching, like a flinch test tactic
- The obstacles are scrolling from right to left towards to character as he remains on the same x-position
- Character jumps by hitting 'W', crouches using 'S'
	- Keys can be held to keep doing/redo the action

## FLOWCHART
[![DinoGame Flowchart](https://i.gyazo.com/6d25080aa252f5ffc234b79b17d38b56.png)](https://gyazo.com/6d25080aa252f5ffc234b79b17d38b56)

## UML DIAGRAMS
All UML diagrams for crucial classes below. They are subject to change as we refine our plan.

### GameScreen
[![UML GameScreen](https://i.gyazo.com/7ba93bba6232c81faf4eda0e15124f19.png)](https://gyazo.com/7ba93bba6232c81faf4eda0e15124f19)

### Player
[![UML Player](https://i.gyazo.com/480c14fb49c6a276d11cfa9a162042c6.png)](https://gyazo.com/480c14fb49c6a276d11cfa9a162042c6)

### Enemy
[![UML Enemy](https://i.gyazo.com/d742b511dfab07f962bd46d28514e9b7.png)](https://gyazo.com/d742b511dfab07f962bd46d28514e9b7)

## GUI Plans
Contains images showing roughly what the GUI will look like
### Main menu
[![Main Menu](https://i.gyazo.com/506ae6dad90477c6d328ee1c49663dcd.png)](https://gyazo.com/506ae6dad90477c6d328ee1c49663dcd)

### Instructions page
[![Instructions](https://i.gyazo.com/23f45f9d9ae59d647efe2ad24249099a.png)](https://gyazo.com/23f45f9d9ae59d647efe2ad24249099a)

### Highscores page
[![Highscores](https://i.gyazo.com/f1b6f44041c2ed34d9b3407f0d181dfb.png)](https://gyazo.com/f1b6f44041c2ed34d9b3407f0d181dfb)

### Loss page
[![Loss page](https://i.gyazo.com/4461fc34e01fa4d47f855f62b3985a20.png)](https://gyazo.com/4461fc34e01fa4d47f855f62b3985a20)

## Algorithm
See [Document](https://docs.google.com/document/d/1wBf-HjNaGea9tyG-j_FRho5JtjuYvdS6VkcOEQDVxGs/edit?usp=sharing)

## Pseudocode
See [Document](https://docs.google.com/document/d/1LtI6y2jdtDfSV5lt1JmzDZsYh6ew1CtRT1pEDO8lWJg/edit?usp=sharing)
