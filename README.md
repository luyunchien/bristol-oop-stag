# Bristol OOP: STAG Project

Simple Text Adventure Game (STAG) project for Bristol University 'Object-Oriented Programming with Java'.

## Usage

1. Start the server:
   ```shell
   ./mvnw clean compile exec:java@server
   ```

2. Launch one or more game clients, passing the username as the first argument
   ```shell
   ./mvnw clean compile exec:java@client -Dexec.args="username"
   ```

## Playing the game

Basic commands:

```
“inventory” (or “inv” for short): lists all of the artefacts currently being carried by the player
“get”: picks up a specified artefact from the current location and adds it into player’s inventory
“drop”: puts down an artefact from player’s inventory and places it into the current location
“goto”: moves the player to a new location (if there is a path to that location)
“look”: describes the entities in the current location and lists the paths to other locations
```

Example:

```
username:> look
You are in cabin - An empty room

You can see artefacts:
potion - Magic potion

You can see furniture:
trapdoor - Wooden trapdoor

You can access from here:
forest
```

