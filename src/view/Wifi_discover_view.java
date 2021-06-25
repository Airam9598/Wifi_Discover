package view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import refactor.Create_table;
import refactor.Get_devices;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Wifi_discover_view {

	private JFrame frmWifi;
	private JTextField textField;
	private JTable table;
	
	public Wifi_discover_view(String disco,String version) {
		initialize(disco,version);
		frmWifi.setVisible(true);
	}

	private void initialize(String disco,String version) {
		frmWifi = new JFrame();
		frmWifi.setResizable(false);
		frmWifi.setTitle(version);
		frmWifi.setBounds(100, 100, 804, 499);
		frmWifi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWifi.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setText("192.168.1.1/255");
		textField.setFont(new Font("Tahoma", Font.BOLD, 15));
		textField.setBounds(123, 11, 271, 20);
		frmWifi.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Rango de ip:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 103, 20);
		frmWifi.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Escanear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table=new Create_table().add_files(disco,textField.getText(),table);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(653, 12, 125, 23);
		frmWifi.getContentPane().add(btnNewButton);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Ip", "Mac","Fabricante"},
			},
			new String[] {
				"Ip", "Mac","Fabricante"
			}
		));
		table.setRowHeight(24);
		table.setGridColor(Color.BLACK);
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Tahoma", Font.BOLD, 18));
		table.setBorder(null);
		table.setBackground(new Color(30, 144, 255));
		table.setBounds(0, 92, 800, 379);
		frmWifi.getContentPane().add(table);
		textField.setText(Get_devices.getLocalip()+"/255");
	}
}
