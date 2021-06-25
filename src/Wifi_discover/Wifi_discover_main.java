package Wifi_discover;


import view.Wifi_discover_view;

public class Wifi_discover_main {

	public static void main(String[] args) {
		String disco = System.getProperty("user.home").substring(0,System.getProperty("user.home").indexOf(":"))+":\\ProgramData";
		String version="Wifi Discover v1.6";
		new Create_DB(disco);
		new Wifi_discover_view(disco,version);
	}
}
