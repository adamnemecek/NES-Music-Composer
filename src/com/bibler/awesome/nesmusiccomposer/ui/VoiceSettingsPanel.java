package com.bibler.awesome.nesmusiccomposer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bibler.awesome.nesmusiccomposer.interfaces.Notifiable;
import com.bibler.awesome.nesmusiccomposer.interfaces.Notifier;

public class VoiceSettingsPanel extends JPanel implements Notifier {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1933056246324297616L;
	private int panelWidth;
	private int panelHeight;
	
	private VoiceStatusPanel squareOnePanel;
	private VoiceStatusPanel squareTwoPanel;
	private VoiceStatusPanel triPanel;
	private VoiceStatusPanel noisePanel;
	
	private Color[] channelColors = new Color[] {
			new Color(0, 0, 255, 175), 
			new Color(255, 0, 0, 175),
			new Color(0, 255, 0, 175),
			new Color(0xFF, 0xAE, 0x3C, 175)
		};
	
	private ArrayList<Notifiable> objectsToNotify = new ArrayList<Notifiable>();
	
	
	public VoiceSettingsPanel(int panelWidth, int panelHeight, Color bg) {
		super();
		setDimensions(panelWidth, panelHeight);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initializeGrid(bg);
	}
	
	public void setDimensions(int panelWidth, int panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}
	
	public void registerObjectToNotify(Notifiable objectToNotify) {
		objectsToNotify.add(objectToNotify);
	}
	
	private void initializeGrid(Color bg) {
		initializeElements(bg);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(squareOnePanel);
		add(squareTwoPanel);
		add(triPanel);
		add(noisePanel);
		
	}
	
	public void initializeElements(Color bg) {
		final int statusWidth = panelWidth;
		final int statusHeight = panelHeight / 4;
		
		squareOnePanel = new VoiceStatusPanel(statusWidth, statusHeight, "Square 1", channelColors[0], bg);
		squareTwoPanel = new VoiceStatusPanel(statusWidth, statusHeight, "Square 2", channelColors[1], bg);
		triPanel = new VoiceStatusPanel(statusWidth, statusHeight, "Tri", channelColors[2], bg);
		noisePanel = new VoiceStatusPanel(statusWidth, statusHeight, "Noise", channelColors[3], bg);
		
	}
	
	
	private class VoiceStatusPanel extends JPanel {
		
		private JLabel voiceLabel;
		private JCheckBox voiceBox;
		private JSlider voiceSlider;
		private JPanel boxSliderPanel;
		private String name;
		
		protected VoiceStatusPanel(int panelWidth, int panelHeight, String name, Color color, Color bg) {
			super();
			this.name = name;
			initialize(panelWidth, panelHeight, name, color);
			setBackground(color);
		}
		
		private void initialize(int panelWidth, int panelHeight, String name, Color bg) {
			setPreferredSize(new Dimension(panelWidth, panelHeight));
			voiceLabel = new JLabel(name);
			voiceBox = new JCheckBox();
			voiceBox.setBackground(bg);
			voiceBox.setSelected(true);
			voiceBox.setActionCommand("MUTE:" + name);
			voiceBox.addActionListener(new MuteListener());
			voiceSlider = new JSlider();
			voiceSlider.setBackground(bg);
			voiceSlider.setMaximum(100);
			voiceSlider.setValue(100);
			voiceSlider.addChangeListener(new VolumeListener());
			boxSliderPanel = new JPanel();
			boxSliderPanel.setLayout(new BorderLayout());
			
			setLayout(new BorderLayout());
			add(voiceLabel, BorderLayout.WEST);
			
			add(boxSliderPanel, BorderLayout.SOUTH);
			
			boxSliderPanel.add(voiceBox, BorderLayout.WEST);
			boxSliderPanel.add(voiceSlider, BorderLayout.EAST);
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		public String getChannelName() {
			return name;
		}
	}
	
	private class MuteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String muteMessage = arg0.getActionCommand();
			if( ((JCheckBox) arg0.getSource()).isSelected()) {
				muteMessage = "UN" + muteMessage;
			} 
			VoiceSettingsPanel.this.notify(muteMessage);
		}
		
	}
	
	private class VolumeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			final JSlider slider = (JSlider) arg0.getSource();
			Component parent = slider.getParent();
			while(!(parent instanceof VoiceStatusPanel)) {
				parent = parent.getParent();
			}
			String name = ((VoiceStatusPanel) parent).getChannelName();
			VoiceSettingsPanel.this.notify("VOLUME:" + name + ":" + (float) slider.getValue() / (float) slider.getMaximum());
			repaint();
		}
		
	}

	@Override
	public void notify(String messageToSend) {
		for(Notifiable objectToNotify : objectsToNotify) {
			objectToNotify.takeNotice(messageToSend, this);
		}
	}

}
