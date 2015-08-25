package com.yw.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class ListPanel extends JScrollPane {

	public ArrayList<String> listData;
	public JList listCheckBox, listDescription;
	public JScrollPane scrollPane;
	public boolean isAllSelected;

	public ListPanel(ArrayList<String> LD) {
		this.listData = LD;
		this.listData.add(0,"Select All");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Unable to find System Look and Feel");
		}
		listCheckBox = new JList(buildCheckBoxItems(listData.size()));
		listDescription = new JList(listData.toArray());
		listDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDescription.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() != 2) {
					return;
				}
				int selectedIndex = listDescription.locationToIndex(me
						.getPoint());
				if (selectedIndex < 0) {
					return;
				}
				if (selectedIndex == 0) {
					if (isAllSelected) {
						for (int i = 1; i < listData.size(); i++) {
							CheckBoxItem item = (CheckBoxItem) listCheckBox
									.getModel().getElementAt(i);
							item.setChecked(false);
						}
					} else {
						for (int i = 1; i < listData.size(); i++) {
							CheckBoxItem item = (CheckBoxItem) listCheckBox
									.getModel().getElementAt(i);
							item.setChecked(true);
						}
					}
					isAllSelected = !isAllSelected;
				}
				CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel()
						.getElementAt(selectedIndex);
				item.setChecked(!item.isChecked());
				listCheckBox.repaint();

			}
		});
		listCheckBox.setCellRenderer(new CheckBoxRenderer());
		listCheckBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCheckBox.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				int selectedIndex = listCheckBox.locationToIndex(me.getPoint());
				if (selectedIndex < 0) {
					return;
				}
				if (selectedIndex == 0) {
					if (isAllSelected) {
						for (int i = 1; i < listData.size(); i++) {
							CheckBoxItem item = (CheckBoxItem) listCheckBox
									.getModel().getElementAt(i);
							item.setChecked(false);
						}
					} else {
						for (int i = 1; i < listData.size(); i++) {
							CheckBoxItem item = (CheckBoxItem) listCheckBox
									.getModel().getElementAt(i);
							item.setChecked(true);
						}
					}
					isAllSelected = !isAllSelected;
				}
				CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel()
						.getElementAt(selectedIndex);
				item.setChecked(!item.isChecked());
				listDescription.setSelectedIndex(selectedIndex);
				listCheckBox.repaint();
			}
		});

		this.setRowHeaderView(listCheckBox);
		this.setViewportView(listDescription);
		listDescription.setFixedCellHeight(20);
		listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
		listCheckBox.setFixedCellWidth(25);
		isAllSelected = true;
		setSize(350, 200);
		setVisible(true);

	}

	public void Refresh(ArrayList<String> listData) {
		listData.add(0,"Select All");
		this.listData = listData;
		listCheckBox.setListData(buildCheckBoxItems(listData.size()));
		listDescription.setListData(listData.toArray());
//		this.setRowHeaderView(listCheckBox);
//		this.setViewportView(listDescription);
//		listDescription.setFixedCellHeight(20);
//		listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
//		listCheckBox.setFixedCellWidth(25);
//		isAllSelected = true;
		this.repaint();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(170,100);
	}
	public Dimension getMinimumSize(){
		return new Dimension(170,100);
	}

	private CheckBoxItem[] buildCheckBoxItems(int totalItems) {
		CheckBoxItem[] checkboxItems = new CheckBoxItem[totalItems];
		for (int counter = 0; counter < totalItems; counter++) {
			checkboxItems[counter] = new CheckBoxItem();

			checkboxItems[counter].setChecked(true);// default checked
		}
		return checkboxItems;
	}

	public ArrayList<String> getSelected() {
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 1; i < listData.size(); i++) {
			CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel()
					.getElementAt(i);
			if (item.isChecked)
				names.add(listData.get(i));
		}
		
		if(names.size() == listData.size()-1){
			return null;
		}

		return names;
	}

	class CheckBoxItem {

		private boolean isChecked;

		public CheckBoxItem() {
			isChecked = false;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean value) {
			isChecked = value;
		}

	}

	/* Inner class that renders JCheckBox to JList */
	class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {

		public CheckBoxRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList listBox,
				Object obj, int currentindex, boolean isChecked,
				boolean hasFocus) {
			setSelected(((CheckBoxItem) obj).isChecked());
			return this;
		}
	}
}
