package com.bibler.awesome.nesmusiccomposer.toolbars;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bibler.awesome.nesmusiccomposer.audio.APU;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class ToolBar extends JToolBar {
	
	private JRadioButton buttonPlay;
	private JRadioButton buttonStop;
	private JRadioButton buttonWhole;
	private JRadioButton buttonHalf;
	private JRadioButton buttonQuarter;
	private JRadioButton buttonEighth;
	private JRadioButton buttonSixteenth;
	private JRadioButton buttonThirtySecond;
	
	private JRadioButton buttonDot;
	private JRadioButton buttonTriplet;
	private JButton buttonRest;
	
	private JRadioButton buttonSquareOne;
	private JRadioButton buttonSquareTwo;
	private JRadioButton buttonTri;
	private JRadioButton buttonNoise;
	
	private JSpinner tempoSpinner;
	private JRadioButton buttonEdit;
	
	
	private ButtonGroup noteButtons;
	private ButtonGroup waveSelectButtons;
	private Border emptyness;
	
	private Image image;
	private ImageIcon icon;
	private Dimension buttonDim;
	
	private SpringLayout layout = new SpringLayout();
	private NoteButtonActionListener noteListener;
	private PlayActionListener playListener;
	private WaveActionListener waveListener;
	
	private int width;
	private int height;
	
	private MainFrame frame;
	
	public ToolBar(int w, int h, Border b) {
		super();
		width = w;
		height = h;
		setPreferredSize(new Dimension(width, 24));
		setBorder(b);
		emptyness = BorderFactory.createEmptyBorder(0,2,0,2);
		buttonDim = new Dimension(24, 24);
		noteListener = new NoteButtonActionListener();
		playListener = new PlayActionListener();
		waveListener = new WaveActionListener();
		setupButtons();
		
		add(buttonPlay);
		add(buttonStop);
		add(buttonWhole);
		add(buttonHalf);
		add(buttonQuarter);
		add(buttonEighth);
		add(buttonSixteenth);
		add(buttonThirtySecond);
		add(buttonDot);
		add(buttonTriplet);
		add(buttonRest);
		add(buttonSquareOne);
		add(buttonSquareTwo);
		add(buttonTri);
		add(buttonNoise);
		SpinnerModel model = new SpinnerNumberModel(120, 0, 500, 1);
		tempoSpinner = new JSpinner(model);
		tempoSpinner.setPreferredSize(new Dimension(72, 24));
		tempoSpinner.setMaximumSize(new Dimension(72, 24));
		tempoSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				SpinnerModel spinModel = tempoSpinner.getModel();
				int tempo = (int) spinModel.getValue();
				//frame.setTempo((float) tempo);
			}
			
		});
		JLabel tempoLabel = new JLabel("Tempo");
		add(tempoSpinner);
		add(tempoLabel);
		add(buttonEdit);
		buttonEdit.setSelected(true);
		buttonQuarter.setSelected(true);
	}
	
	public void setupButtons() {
		buttonPlay = new JRadioButton();
		buttonStop = new JRadioButton();
		buttonWhole = new JRadioButton();
		buttonHalf = new JRadioButton();
		buttonQuarter = new JRadioButton();
		buttonEighth = new JRadioButton();
		buttonSixteenth = new JRadioButton();
		buttonThirtySecond = new JRadioButton();
		buttonDot = new JRadioButton();
		buttonTriplet = new JRadioButton();
		buttonRest = new JButton("Rest");
		buttonSquareOne = new JRadioButton("Square 1");
		buttonSquareTwo = new JRadioButton("Square 2");
		buttonTri = new JRadioButton("Triangle");
		buttonNoise = new JRadioButton("Noise");
		buttonEdit = new JRadioButton("Edit");
		noteButtons = new ButtonGroup();
		noteButtons.add(buttonWhole);
		noteButtons.add(buttonHalf);
		noteButtons.add(buttonQuarter);
		noteButtons.add(buttonEighth);
		noteButtons.add(buttonSixteenth);
		noteButtons.add(buttonThirtySecond);
		waveSelectButtons = new ButtonGroup();
		waveSelectButtons.add(buttonSquareOne);
		waveSelectButtons.add(buttonSquareTwo);
		waveSelectButtons.add(buttonTri);
		waveSelectButtons.add(buttonNoise);
			
		setupButton(buttonPlay, "play", true, false, playListener);
		setupButton(buttonStop, "stop",  false, false, playListener);
		
		setupButton(buttonWhole, "note_whole",  true, false, noteListener);
		setupButton(buttonHalf, "note_half", true, false, noteListener);
		setupButton(buttonQuarter, "note_quarter", true, false, noteListener);
		setupButton(buttonEighth, "note_eighth", true, false, noteListener);
		setupButton(buttonSixteenth, "note_sixteenth", true, false, noteListener);
		setupButton(buttonThirtySecond, "note_thirty_second", true, false, noteListener);
		setupButton(buttonDot, "dot",  true, false, noteListener);
		setupButton(buttonTriplet, "triplet",  true, false, noteListener);
		setupButton(buttonSquareOne, "square_wave_one",  true, false, waveListener);
		setupButton(buttonSquareTwo, "square_wave_two",  true, false, waveListener);
		setupButton(buttonTri, "triangle_wave", true, false, waveListener);
		setupButton(buttonNoise, "noise_wave", true, false, waveListener);
		buttonEdit.addActionListener(waveListener);
		buttonEdit.setBorder(emptyness);
		buttonEdit.setActionCommand("edit");
		
		buttonRest.addActionListener(noteListener);
		buttonRest.setBorder(emptyness);
		buttonRest.setActionCommand("rest");
	}
	
	public void setupButton(JRadioButton b, String name, boolean selected, boolean content, ActionListener listener) {
		b.setPreferredSize(buttonDim);
		try {
			image = ImageIO.read(new File("C:/users/ryan/workspace_nes/jnesaudio/bin/images/" + name + ".png"));
			icon = new ImageIcon(image);
			b.setIcon(icon);
			if(selected) {
				image = ImageIO.read(new File("C:/users/ryan/workspace_nes/jnesaudio/bin/images/" + name + "_selected.png"));
				icon = new ImageIcon(image);
				b.setSelectedIcon(icon);
			}
		} catch(IOException e) {}
		b.setActionCommand(name);
		b.addActionListener(listener);
		b.setContentAreaFilled(content);
		b.setBorder(emptyness);
	}
	
	public void changeChannel(int channel) {
		//if(channel == frame.getCurrentChannel())
			//return;
		switch(channel) {
		case APU.SQUARE_1:
			buttonSquareOne.doClick();
			break;
		case APU.SQUARE_2:
			buttonSquareTwo.doClick();
			break;
		case APU.TRI:
			buttonTri.doClick();
			break;
		}
	}
	
	public void changeRadioButtonSelection(JRadioButton button) {
		if(button.isSelected()) {
			button.setSelected(false);
		} else {
			button.setSelected(true);
		}
	}
	
	public class NoteButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			/*if(!buttonDot.isEnabled())
				buttonDot.setEnabled(true);
			if(!buttonEdit.isSelected())
				buttonEdit.doClick();
			String command = e.getActionCommand();
			int dotMask = 1;
			int tupletMask = 1;
			if(buttonDot.isSelected()) {
				dotMask = NoteFactory.DOT_MASK;
			}
			if(buttonTriplet.isSelected()) {
				tupletMask = NoteFactory.TUPLET_MASK;
			}
			if(command.equals("note_whole")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.WHOLE * dotMask * tupletMask;
			}
			if(command.equals("note_half")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.HALF * dotMask * tupletMask;
			}
			if(command.equals("note_quarter")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.QUARTER * dotMask * tupletMask;
			}
			if(command.equals("note_eighth")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.EIGHTH * dotMask * tupletMask;
			}
			if(command.equals("note_sixteenth")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.SIXTEENTH * dotMask * tupletMask;
			}
			if(command.equals("note_thirty_second")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.THIRTY_SECOND * dotMask * tupletMask;
			}
			if(command.equals("note_sixty_fourth")) {
				BaseObject.sRegistry.currentNoteType = NoteFactory.SIXTY_FOURTH * tupletMask;
				buttonDot.setSelected(false);
				buttonDot.setEnabled(false);
			}
			if(command.equals("dot")) {
				if(BaseObject.sRegistry.currentNoteType == NoteFactory.SIXTY_FOURTH) {
					buttonDot.setSelected(false);
					return;
				}
				if(buttonDot.isSelected()) {
					BaseObject.sRegistry.dot = true;
					BaseObject.sRegistry.currentNoteType = BaseObject.sRegistry.currentNoteType * NoteFactory.DOT_MASK;
				} else {
					BaseObject.sRegistry.dot = false;
					BaseObject.sRegistry.currentNoteType = BaseObject.sRegistry.currentNoteType / NoteFactory.DOT_MASK;
				}
			}
			if(command.equals("triplet")) {
				if(buttonTriplet.isSelected()) {
					BaseObject.sRegistry.triplet = true;
				} else {
					BaseObject.sRegistry.triplet = false;
				}
			}
			if(command.equals("rest")) {
				Bar bar = BaseObject.sRegistry.score.bars.get(BaseObject.sRegistry.selectedBar);
				Note n = new Note("rest", BaseObject.sRegistry.currentNoteType, BaseObject.sRegistry.currentInstrument, 
						0, bar.highlightPos.x, BaseObject.sRegistry.currentChannel);
				bar.insertNote(n);
			}*/
			//BaseObject.sRegistry.sheetManager.currentSheet.updateNoteType();
			//BaseObject.sRegistry.sheetManager.repaint();
		}
	}
	
	public class PlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*String command = arg0.getActionCommand();
			if(command.equals("play")) {
				if(buttonPlay.isSelected()) {
					BaseObject.sRegistry.score.playScore();
				} else {
					BaseObject.sRegistry.score.pauseScore();
				}
			}
			if(command.equals("stop")) {
				BaseObject.sRegistry.score.stopScore();
				buttonPlay.setSelected(false);
			}*/
			
		}
		
	}
	
	public class WaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*String command = arg0.getActionCommand();
			if(command.equals("square_wave_one")) {
				BaseObject.sRegistry.currentChannel = Score.SQUARE_1;
			}
			if(command.equals("square_wave_two")) {
				BaseObject.sRegistry.currentChannel = Score.SQUARE_2;
			}
			if(command.equals("triangle_wave")) {
				BaseObject.sRegistry.currentChannel = Score.TRI;
			}
			
			if(command.equals("noise_wave")) {
				BaseObject.sRegistry.currentChannel = Score.NOISE;
			}
			
			if(command.equals("scales")) {
				BaseObject.sRegistry.scaleSelector.showScaleDialog();
			}
			if(command.equals("edit")) {
				BaseObject.sRegistry.editMode = buttonEdit.isSelected();
			}*/
		}
	}
}
