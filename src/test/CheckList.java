package test;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class CheckList extends JFrame {

	public ArrayList<String> listData;
	public JList listCheckBox, listDescription;
	public JScrollPane scrollPane;
	public boolean isAllSelected;

	public CheckList(ArrayList<String> listData) {
		this.listData = listData;
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
		scrollPane = new JScrollPane();
		scrollPane.setRowHeaderView(listCheckBox);
		scrollPane.setViewportView(listDescription);
		listDescription.setFixedCellHeight(20);
		listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
		listCheckBox.setFixedCellWidth(25);
		getContentPane().add(scrollPane); // , BorderLayout.CENTER);
		isAllSelected = true;
		setSize(350, 200);
		setVisible(true);

	}

	public void Refresh(String[] listData) {
		listCheckBox.setListData(buildCheckBoxItems(listData.length));
		listDescription.setListData(listData);
		scrollPane.setRowHeaderView(listCheckBox);
		scrollPane.setViewportView(listDescription);
		listDescription.setFixedCellHeight(20);
		listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
		listCheckBox.setFixedCellWidth(20);
	}

	private CheckBoxItem[] buildCheckBoxItems(int totalItems) {
		CheckBoxItem[] checkboxItems = new CheckBoxItem[totalItems];
		for (int counter = 0; counter < totalItems; counter++) {
			checkboxItems[counter] = new CheckBoxItem();

			checkboxItems[counter].setChecked(true);// default checked
		}
		return checkboxItems;
	}
	
	public ArrayList<String> getSelected(){
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0 ; i < listData.size() ; i++){
			CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel()
					.getElementAt(i);
			if(item.isChecked)
				names.add(listData.get(i));
		}
		
		return names;
	}
	
	

	public static void main(String args[]) {

		ArrayList<String> s = new ArrayList<String>();
		s.add("Select All");
		s.add("1");
		s.add("2");
		s.add("3");
		s.add("4");
		s.add("5");
		s.add("6");
		CheckList checkList = new CheckList(s);
		checkList.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				System.exit(0);
			}
		});

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