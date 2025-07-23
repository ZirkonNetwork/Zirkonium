# Zirkonium
A Leaf fork. Originally a Purpur fork for Zirkon Network.
## Features
- [Sneaky](https://github.com/adryd325/sneaky)
## Coming Soon
- [Dynamic View](https://github.com/ldtteam/DynView)
## Building
Note:
These instructions will not work without a Java 21 Development Kit (JDK) or another compatible JDK setup correctly.
The commands are for Bash (or Git Bash), but you should only have to remove `./` if you aren't using bash.\
\
Run `./gradlew applyAllPatches` and then `./gradlew createMojmapPaperclipJar` to get a Paperclip jar to use for servers.
This entire process can take anywhere from 5 minutes to an hour plus depending on the available CPU power, available memory, and the speed of the drive this is on
(the higher the r/w speeds the better; HDDs are not recommended).\
After the tasks are completed, the new Paperclip jar can be found in `./build/libs` under the name `Zirkonium-paperclip-<mcVersion>-<apiVersion>-reobf.jar`.
