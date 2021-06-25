package refactor;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Device;

public class Create_table {
	public JTable add_files(String disco,String texto,JTable table) {
		Map<Integer,Device> devicemap=new HashMap<Integer,Device>();
		Get_devices devicelist =new Get_devices(disco,texto);
		devicemap=devicelist.getDevicemap();
		table.setModel(new DefaultTableModel(new Object[][] {{"Ip", "Mac", "Fabricante"},},new String[] {"Ip", "Mac","Fabricante"}));
		DefaultTableModel model=(DefaultTableModel) table.getModel();
		if(!devicemap.isEmpty()) {
			for(Device device : devicemap.values())
				model.addRow(new Object[] {device.getIp(),device.getMac(),device.getBuilder()});
		}
		return table;
	}

}
