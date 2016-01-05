[![rock-paper-scissors](https://travis-ci.org/queeno/rock-paper-scissors.png?branch=master)](https://travis-ci.org/queeno/rock-paper-scissors)

# Rock-Paper-Scissors

An implementation of the famous [Rock-Paper-Scissors](https://en.wikipedia.org/wiki/Rock-paper-scissors) game in Java.

## Trello board

A public [trello board](https://trello.com/b/dQTBkDv8/rock-paper-scissors) has been set up to display the development of this application.

## Compile and run

Please make sure you have JRE 1.8 installed on your machine.

Run the following commands to compile and run the application with default options:

```bash
make
make run
```

The Makefile will automatically create a `bin/` directory in which to place the compiled Java code.

Please refer how to the next section if you want to change the defaults. 

## How to configure

Two configuration files are needed in order to start the game:

- `config.xml`
- `shapes-base.csv`

### config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config>
  <player>
    <name>Simon</name>
    <automated>false</automated>
  </player>
  <player>
    <name>Computer</name>
    <automated>true</automated>
  </player>
  <rounds>5</rounds>
  <logging>false</logging>
</config>
```

This file describes the players, game rounds and level of logging.

- **Players:**
  Please configure at least 2 players.
  You should indicate for each player:
  - name: A string identifying the name of the player.
  - automated: A boolean value (true or false) identifying whether the player is a human (false) or a machine (true).
- **Rounds:**
  The number of rounds to complete the game.
  Please specify at least 1 round. 
- **Logging:**
  A boolean value identifying whether to redirect logging to output.

### shapes-base.csv

This file contains a list of shapes and relationships between them.
Example:

``` 
"Rock","Paper","Scissors"
"","0","1"
"1","","0"
"0","1",""
```

The first row of the matrix identifies the names of the shapes, the remaining rows (also referred to as: 'the shape\_matrix') represent an NxN matrix which dictates the relationship between the shapes.

The `n` row and column refer to the element defined in that column.
For example, in the example above, the first column and row refer to 'rock', the second column and row refer to 'paper' and the third column and row refer to 'scissors'.

With that in mind, element M\_{n1,n2}, defines the relationship between `elem_n1` and `elem_n2`.

'0' indicates `elem_n1` is defeated by `elem_n2`.
'1' indicates `elem_n1` beats `elem_n2`.
'' indicates a draw.

For example, element M\_{0,1} = 0 indicates that the rock is defeated by the paper. element M\_{2,1} = 1 indicates that the scissors beat the paper.

During configuration loading, a matrix validation procedure is initiated, which checks that:
- M\_{n1,n1} == "" (the same shape must draw)
- M\_{n1,n2} == !M\_{n2,n1} (if the paper beats the rock, then the rock must be defeated by the paper).

### Custom config files

It is possible to pass custom config files to the Makefile, declaring a `RPS_ARGS` variable. The first element should be the configuration XML file and the second the shapes matrix in CSV format.

For example:

```bash
RPS_ARGS='config.xml shapes-extended.xml' make run
```

## How to play

Playing Rock-Paper-Scissors is easy.

After configuration loading and matrix validation (turn on logging to see), an initial message prints out the game configuration:

```
WELCOME TO ROCK-PAPER-SCISSORS

--- GAME CONFIGURATION ---
Shapes: rock, paper, scissors
Players: Simon (H), Computer (C)
```

The game constists of `n` rounds. During every round, each of the users guesses one shape, the shapes fight each other and the winners earn one point.

A provisional scoreboard is displayed at the end of each round and the final scoreboard is displayed before game termination.

Automated users make automatic guesses. The game will prompt for input for the humans:

```
--- Round 1 of 5 ---

Waiting for all players to make their choices...
Simon, please make a choice [rock, paper, scissors, or empty to abort]:
```

The input is case insensitive. You will be reprompted for non-recognised words.

Enter to terminate the game.

```
Terminating game... Hej hej!
```

## Feedback

Do you love this game? Anything you didn't like? Any bugs or feature request?

Please contact me! [simonaquino@gmail.com](simonaquino@gmail.com)
