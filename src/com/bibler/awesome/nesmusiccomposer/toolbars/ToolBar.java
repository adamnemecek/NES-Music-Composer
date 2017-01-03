package com.bibler.awesome.nesmusiccomposer.toolbars;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;
import com.bibler.awesome.nesmusiccomposer.ui.MainFrame;

public class ToolBar extends JToolBar implements Notifier, Notifiable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7463016686391341749L;
	private JRadioButton buttonPlay;
	private JRadioButton buttonStop;
	private JRadioButton buttonWhole;
	private JRadioButton buttonHalf;
	private JRadioButton buttonQuarter;
	private JRadioButton buttonEighth;
	private JRadioButton buttonSixteenth;
	private JRadioButton buttonThirtySecond;
	
	private JRadioButton buttonDot;
	private JRadioButton buttonRest;
	
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
	
	private final String iconRoot = "C:/users/rbibl/workspace/NES-Music-Composer/files/icons/";
	
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	public ToolBar(int w, int h, Border b, Color bg) {
		super();
		width = w;
		height = h;
		setBackground(bg);
		setPreferredSize(new Dimension(width, 24));
		setBorder(b);
		emptyness = BorderFactory.createEmptyBorder(0,2,0,2);
		buttonDim = new Dimension(24, 24);
		noteListener = new NoteButtonActionListener();
		playListener = new PlayActionListener();
		waveListener = new WaveActionListener();
		setupButtons(bg);
		
		add(buttonPlay);
		add(buttonStop);
		add(buttonWhole);
		add(buttonHalf);
		add(buttonQuarter);
		add(buttonEighth);
		add(buttonSixteenth);
		add(buttonThirtySecond);
		add(buttonRest);
		add(buttonDot);
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
	
	private void setupButtons(Color bg) {
		buttonPlay = new JRadioButton();
		buttonStop = new JRadioButton();
		buttonWhole = new JRadioButton();
		buttonHalf = new JRadioButton();
		buttonQuarter = new JRadioButton();
		buttonEighth = new JRadioButton();
		buttonSixteenth = new JRadioButton();
		buttonThirtySecond = new JRadioButton();
		buttonDot = new JRadioButton();
		buttonRest = new JRadioButton();
		buttonSquareOne = new JRadioButton();
		buttonSquareTwo = new JRadioButton();
		buttonTri = new JRadioButton();
		buttonNoise = new JRadioButton();
		buttonEdit = new JRadioButton("Edit");
		noteButtons = new ButtonGroup();
		noteButtons.add(buttonWhole);
		noteButtons.add(buttonHalf);
		noteButtons.add(buttonQuarter);
		noteButtons.add(buttonEighth);
		noteButtons.add(buttonSixteenth);
		noteButtons.add(buttonThirtySecond);
		noteButtons.add(buttonRest);
		waveSelectButtons = new ButtonGroup();
		waveSelectButtons.add(buttonSquareOne);
		waveSelectButtons.add(buttonSquareTwo);
		waveSelectButtons.add(buttonTri);
		waveSelectButtons.add(buttonNoise);
			
		setupButton(buttonPlay, "button_play", true, false, playListener);
		setupButton(buttonStop, "button_stop",  false, false, playListener);
		
		setupButton(buttonWhole, "note_whole",  true, false, noteListener);
		setupButton(buttonHalf, "note_half", true, false, noteListener);
		setupButton(buttonQuarter, "note_quarter", true, false, noteListener);
		setupButton(buttonEighth, "note_eighth", true, false, noteListener);
		setupButton(buttonSixteenth, "note_sixteenth", true, false, noteListener);
		setupButton(buttonThirtySecond, "note_thirty_second", true, false, noteListener);
		setupButton(buttonDot, "note_dot",  true, false, noteListener);
		setupButton(buttonRest, "note_rest", true, false, noteListener);
		setupButton(buttonSquareOne, "button_square_one_wave",  true, false, waveListener);
		setupButton(buttonSquareTwo, "button_square_two_wave",  true, false, waveListener);
		setupButton(buttonTri, "button_tri_wave", true, false, waveListener);
		setupButton(buttonNoise, "button_noise_wave", true, false, waveListener);
		buttonEdit.addActionListener(waveListener);
		buttonEdit.setBorder(emptyness);
		buttonEdit.setActionCommand("edit");
		buttonEdit.setBackground(bg);
	}
	
	private void setupButton(JRadioButton b, String name, boolean selected, boolean content, ActionListener listener) {
		b.setPreferredSize(buttonDim);
		try {
			image = ImageIO.read(new File(iconRoot + name + ".png"));
			icon = new ImageIcon(image);
			b.setIcon(icon);
			if(selected) {
				image = ImageIO.read(new File(iconRoot + name + "_selected.png"));
				icon = new ImageIcon(image);
				b.setSelectedIcon(icon);
			}
		} catch(IOException e) {}
		b.setActionCommand(name);
		b.addActionListener(listener);
		b.setContentAreaFilled(content);
		b.setBorder(emptyness);
		b.setToolTipText(name);
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
	
	public void changeNoteLength(int noteLength) {
		switch(noteLength) {
		case 0:
			buttonWhole.doClick();
			break;
		case 1:
			buttonHalf.doClick();
			break;
		case 2:
			buttonQuarter.doClick();
			break;
		case 3:
			buttonEighth.doClick();
			break;
		case 4:
			buttonSixteenth.doClick();
			break;
		case 5:
			buttonThirtySecond.doClick();
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
	
	private class NoteButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!buttonDot.isEnabled())
				buttonDot.setEnabled(true);
			if(!buttonEdit.isSelected())
				buttonEdit.doClick();
			String command = e.getActionCommand();
			ToolBar.this.notify(command);
		}
	}
	
	private class PlayActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			if(command.equals("button_play")) {
				if(buttonPlay.isSelected()) {
					ToolBar.this.notify("PLAY");
				} else {
					ToolBar.this.notify("PAUSE");
				}
			}
			if(command.equals("button_stop")) {
				ToolBar.this.notify("STOP");
				buttonPlay.setSelected(false);
			}
		}
	}
	
	private class WaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			ToolBar.this.notify(command);
		}
	}
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}

	@Override
	public void notify(String messageToSend) {
		for(Notifiable notifiable : objectsToNotify) {
			notifiable.takeNotice(messageToSend, this);
		}
	}

	@Override
	public void takeNotice(String message, Object Notifier) {
		if(message.contains("CHANGE_NOTE")) {
			int noteToChange = Integer.parseInt(message.split(":")[1]);
			changeNoteLength(noteToChange);
		}
	}
}
