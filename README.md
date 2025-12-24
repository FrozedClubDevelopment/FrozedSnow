# FrozedSnow
Xmas plugin to display particles continously

## Source
- [GitHub](https://github.com/FrozedClubDevelopment/FrozedSnow)
## Version
- Minecraft 1.21+
## Dependencies
- PacketEvents
## Features
- Configurable messages
- Configurable particle
## Commands
- /snow - Toggle particles on or off
- /frozedsnow reload - Reload the FrozedSnow configuration file
## Configuration:
```yaml
particle-settings:
  type: FIREWORK
  amount: 16
  speed: 0.1
  offset-x: 0.1
  offset-y: 0.1
  offset-z: 0.1
  radius-distance-in-blocks: 30
player-settings:
  command-cooldown: 16
  particles-enabled: "&aHas activado las particulas"
  particles-disabled: "&cHas desactivado las particulas"
  cooldown-message: "&cDebes esperar &e{time} &csegundos antes de usar este comando nuevamente!"
```
