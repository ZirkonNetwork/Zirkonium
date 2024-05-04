# Zirkonium
A Purpur fork originally for Zirkon Network
## Features
- Higher maximum enchantment levels modified with multiplication by the set value
- Modifiable maximum anvil levels without an extra plugin (can be overridden by plugins)
- [No-Chat-Reports Mod](https://github.com/Aizistral-Studios/No-Chat-Reports)
- [Dynamic View](https://github.com/ldtteam/DynView)
- [Sneaky](https://github.com/adryd325/sneaky)
#### Old Features
These features have been removed for various reasons but are still available under `/patches/removed`.
If you for some reason still want to use them, they must be re-implemented.
You will receive no support for the features or for re-implementing them.
- [Smooth Boot Mod](https://github.com/UltimateBoomer/mc-smoothboot)
  - Issues solved by SB were fixed in 1.19.4
## Building
Note:
These instructions will not work without a Java 21 Development Kit (JDK) or another compatible JDK setup correctly.
The commands are for Bash (or Git Bash), but you should only have to remove `./` if you aren't using bash.\
\
Run `./gradlew applyPatches`, `./gradlew build`,
and then `./gradlew createReobfPaperclipJar` to get a Paperclip jar to use for servers.
This entire process can take anywhere from 5 minutes to an hour plus depending on the available CPU power, available memory, and the speed of the drive this is on
(the higher the r/w speeds the better; HDDs are not recommended).\
After the tasks are completed, the new Paperclip jar can be found in `./build/libs` under the name `Zirkonium-paperclip-<mcVersion>-<apiVersion>-reobf.jar`.
