package com.abhisekp;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Abhisek Pattnaik <abhisekp@engineer.com>
 * Creation Date: 19-05-2014 09:45 AM
 * Description:-
 * Beat Box
 */
public class BeatBox {
	private Sequencer sequencer;
	private final int[] instruments;
	private final String[] instrumentNames;
	private Sequence sequence;
	private JFrame mainFrame;
	private JButton playBTN;
	private JButton stopBTN;
	private JButton resetBTN;
	private JButton tempoUpBTN;
	private JButton tempoDownBTN;
	private JPanel mainPanel;
	private ArrayList<JCheckBox> checkboxList;
	private StringBuilder title;
	boolean isPlaying;
	boolean isEmptyNoteEvents;
	private Track track;
	private float BPM;

	public static void main(String[] args) {
		new BeatBox().run();
	}

	public BeatBox() {
		instruments = new int[]{
				35, 42, 46, 38,
				49, 39, 50, 60,
				70, 72, 64, 56,
				58, 47, 67, 63
		};
		instrumentNames = new String[]{
				"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare",
				"Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
				"Maracas", "Whistle", "Low Conga", "Cowbell",
				"Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
		};
	}

	public void run() {
		// setup application
		setupGUI();
		setupPlayer();

		// run/generate application
		createGUI();
		createListeners();

		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.requestFocus();
	}


	private void setupGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		title = new StringBuilder("BeatBox");
		mainFrame = new JFrame(title.toString());

		mainPanel = new JPanel(new GridLayout(16, 17, 1, 2));

		// List of checkboxes in array
		checkboxList = new ArrayList<JCheckBox>(256);

		// buttons config.
		playBTN = new JButton("Play");
		stopBTN = new JButton("Stop");
		resetBTN = new JButton("Reset");
		tempoUpBTN = new JButton(">");
		tempoDownBTN = new JButton("<");
	}


	private void createGUI() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JPanel instrumentsPanel = new JPanel(new GridLayout(16, 1, 1, 2));
		for (int i = 0; i < 16; i++) {
			// create JLabel of Instruments & add to WEST box
			JLabel instrumentName = new JLabel(instrumentNames[i], SwingConstants.LEFT);
			instrumentName.setFont(new Font("Georgia", Font.PLAIN, 15));
			instrumentsPanel.add(instrumentName);

			// create unselected checkboxes of corresponding instruments
			for (int j = 0; j < 16; j++) {
				// add checkboxes
				JCheckBox cb = new JCheckBox();
				cb.setSelected(false);
				checkboxList.add(cb);
				mainPanel.add(cb);
			}
		}

		// add buttons to buttonsPanel
		Box buttonsPanel = new Box(BoxLayout.Y_AXIS);
		// @FIxMe Align buttons to right
		playBTN.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonsPanel.add(playBTN);
		stopBTN.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonsPanel.add(stopBTN);
		resetBTN.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonsPanel.add(resetBTN);

		// add tempoButton to tempoPanel
		Box tempoPanel = new Box(BoxLayout.X_AXIS);
		// @FixMe Align buttons to center
		tempoDownBTN.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tempoPanel.add(tempoDownBTN);
		tempoUpBTN.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tempoPanel.add(tempoUpBTN);

		// add padding to mainFrame
		JPanel backgroundPanel = new JPanel(new BorderLayout());
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// add instrumentsPanel to WEST of backgroundPanel
		backgroundPanel.add(instrumentsPanel, BorderLayout.WEST);

		// add buttonsPanel to rightPanel
		rightPanel.add(buttonsPanel);
		rightPanel.add(tempoPanel);
		// add rightPanel to EAST of backgroundPanel
		backgroundPanel.add(rightPanel, BorderLayout.EAST);

		// add mainPanel to CENTER of backgroundPanel
		backgroundPanel.add(mainPanel, BorderLayout.CENTER);

		// add backgroundPanel to mainFrame
		mainFrame.getContentPane().add(backgroundPanel);
	}

	private void setupPlayer() {
		try {
			BPM = 120;
			sequencer = MidiSystem.getSequencer();
			sequence = new Sequence(Sequence.PPQ, 4, 0);

			isPlaying = false;
			isEmptyNoteEvents = true;
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	public void startPlayer() {

		// start playing
		if (sequencer.isRunning()) {
			stopPlayer();
		}
		try {
//			System.out.println("\nOpened Sequencer"); // @FixME Delete
			sequencer.open();
			track = sequence.createTrack();
			createSong();
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.setTempoInBPM(BPM);
			sequencer.start();
			isPlaying = true;
			updateTitle();
//			System.out.println("Total events in track = " + track.size()); // @FixME Delete
			if (isEmptyNoteEvents) {
				stopPlayer();
			}
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

	}

	public void stopPlayer() {

		sequence.deleteTrack(track);

		// stop playing
//		System.out.println("\nClosed Sequencer"); // @FixME Delete
		sequencer.close();

		isPlaying = false;
		isEmptyNoteEvents = true;
	}

	public void resetPlayer() {
		stopPlayer();
		BPM = 120;

		for (JCheckBox aCheckboxList : checkboxList) {
			aCheckboxList.setSelected(false);
		}
	}

	public void tempoUpPlayer() {
		BPM += 50;
		if(sequencer.isRunning()){
			restartPlayer();
		}
	}

	public void tempoDownPlayer() {
		BPM -= 50;
		if(sequencer.isRunning()){
			restartPlayer();
		}
	}

	public void restartPlayer() {
		if (sequencer.isRunning()) {
			stopPlayer();
		}
		if (!sequencer.isRunning()) {
			startPlayer();
		}
	}

	private void createSong() {
		int volume = 127; // Range - 0-127
		// add instruments[chanNo] change event in channel[chanNo]
		track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 9, 1, 127, 0));

		for (int chanNo = 0; chanNo < 16; chanNo++) { // row - instrument [0 - 15] or channel
			for (int j = 0; j < 16; j++) { // column - beat [0 - 15]
				if (checkboxList.get(j + chanNo * 16).isSelected()) {

					// add Note ON event to channel[chanNo]
					track.add(makeEvent(ShortMessage.NOTE_ON, 9, instruments[chanNo], volume, j));
//					System.out.println("Added Drum ON - " + instrumentNames[chanNo] + " at Beat #" + j); // @FixME Delete

					// add Note OFF event
					track.add(makeEvent(ShortMessage.NOTE_OFF, 9, instruments[chanNo], volume, j + 1));
//					System.out.println("Added Drum OFF - " + instrumentNames[chanNo] + " at Beat #" + (j + 1)); // @FixME Delete

					isEmptyNoteEvents = false;
				}
			}

			// @TODO add control change event to beat[15 + noteOffset]
			track.add(makeEvent(ShortMessage.CONTROL_CHANGE, 9, 127, 0, 16));
		}
	}

	public void createListeners() {
		playBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// start playing after generating
				startPlayer();
			}
		});

		stopBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// stop playing & clear sequence
				stopPlayer();
			}
		});

		resetBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// stop the player & clear checkboxes
				resetPlayer();
			}
		});

		tempoUpBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// speed up player
				tempoUpPlayer();
			}
		});

		tempoDownBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// speed up player
				tempoDownPlayer();
			}
		});
	}

	private MidiEvent makeEvent(int cmd, int chan, int data1, int data2, long tick) {
		ShortMessage msg = null;
		try {
			msg = new ShortMessage(cmd, chan, data1, data2);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return new MidiEvent(msg, tick);
	}

	private void updateTitle() {
		// @FIXME might be redundant method
		mainFrame.setTitle(title.toString() + " - BPM: " + BPM);
	}

	public void updateGUI() {
	}
}
