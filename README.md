# BeatBox
### *Create Music on Go*
A music beat box to play music by selecting the beats of corresponding instruments.
![BeatBox App](https://i.imgur.com/L9JHQ1o.png "BeatBox App")

## Features
- Play & Stop music or Reset player to default
- Change Playback speed
- Many types of Drum Kit instruments

## Concepts Used
- Package [*com.abhisekp*]
- Class [*encapsulation of methods(behaviour) and fields(variables or state)*]
- Class member access specifier [*public, private*]
- Swing GUI API [*JFrame, JPanel, BoxLayout, GridLayout, JButton, JCheckbox*]
- Event Listeners [*addActionListener()*]
- Inner class or Anonymous class [*used to add event listeners*]
- Sound API [*Sequencer, Sequence, Track, MidiEvent*]

## Building
**BeatBox.java** - main class

### Pre-requisites
- Java SE 7+ SDK (Reason: Using Override & Anonymous class)
- Java IDE (e.g. IntelliJ IDEA, Netbeans, Eclipse, etc) (*optional*)

### Compiling from Source (manually)
*All sources in com.abhisekp package*
### In Windows
	cd src\ && mkdir ..\out\
	javac -d ..\out\ com\abhisekp\BeatBox.java

### In Linux
	cd src/ && mkdir ../out/
	javac -d ../out/ com/abhisekp/BeatBox.java

## Running Manually Built 
### In Windows
	cd ..\out\
	java com.abhisekp.BeatBox
	
### In Linux
	cd ../out/
	java com.abhisekp.BeatBox

## Running Packaged jar
	java -jar "Mini Music Player.jar"

### Import in Eclipse (Windows, OSX, Linux, Automatic)
How to import BeatBox sources in Eclipse [see here](https://imgur.com/a/GM8Rf?gallery "Import BeatBox in Eclipse")

## Future (TODOs)
- Save & Load Midi files from FileSystem (.wav or .midi)
- Send Midi using Network
- Chat server & client to transmit music between peers
- Upload Midi music to Online Sound Storage services (e.g. SoundCloud)

## Get Softwares
- [Jetbrains IntelliJ IDEA](http://www.jetbrains.com/idea/download/download_thanks.jsp)
- [Netbeans](https://netbeans.org/downloads/start.html?platform=windows&lang=en&option=javase)
- [Eclipse](https://www.eclipse.org/downloads/packages/eclipse-standard-432/keplersr2)
- [Java SE SDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
