package model;

public class Device {
	private String ip;
	private String mac;
	private String Builder;
	public Device(String ip, String mac, String builder) {
		this.ip = ip;
		this.mac = mac;
		this.Builder=builder;
	}
	public String getIp() {
		return ip;
	}
	public String getMac() {
		return mac;
	}
	public String getBuilder() {
		return Builder;
	}
	
	
}
