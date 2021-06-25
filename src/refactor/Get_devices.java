package refactor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import model.Device;
public class Get_devices {
	private HashMap<Integer,Device> devicemap=new HashMap<Integer,Device>();
	public Get_devices(String disco, String valores) {
		File directorio=new File("ips");
		directorio.mkdir();
		try {
			String localip=valores.substring(0,valores.indexOf("/")-1);
			int limit=Integer.parseInt(valores.substring(valores.indexOf("/")+1,valores.length()));
			if(localip.length()>1) {
				for(int i=1;i<limit;i++) {
					test(localip+i);	
				}
				for(File file:directorio.listFiles()) {
					String ip=file.getName().substring(0,file.getName().length()-5);
					String mac=get_mac(ip);
					if(mac.length()<1)mac=get_mac(ip);
					String model="";
					if(mac.length()>2) {
						model=getbuilder(mac.substring(0,8),disco);
					}
					devicemap.put(devicemap.size(),new Device(ip,mac,model));
				}
				directorio.delete();
			}
		}catch(Exception e) {
		}
	}
	

	public Map<Integer, Device> getDevicemap() {
		return devicemap;
	}
	
	private void test(String ip) {
	           new Thread(new Runnable() {

	               public void run() {
	            	   try {
	          		     Process p=Runtime.getRuntime().exec("ping "+ip+ " -n 1 -w 5");
	          		     BufferedReader s=new BufferedReader(new InputStreamReader(p.getInputStream()));
	          		     p.waitFor();
	          		     for(int i=0;i<2;i++) {s.readLine();}
	          		     String data=s.readLine();
	          		     if(data.contains("agotado")){
	          		    	 s.close();
	          		     }else {
	          		    	 new File ("ips/"+ip+".data").createNewFile();
	          		    	 s.close();
	          		     }
	          		    } catch (Exception e1) {}
	               }
	           }).start();
	}
	private String get_mac(String ip) {
		try {
		     Process p=Runtime.getRuntime().exec("cmd /c arp -a | find \""+ip+" \"");
		     BufferedReader s=new BufferedReader(new InputStreamReader(p.getInputStream()));
		     p.waitFor();
		     String data=s.readLine();
		     data=data.replaceAll("\\s+"," ").trim().split(" ")[1];
		     if(data.equals(ip)) {
		    	 Process p2=Runtime.getRuntime().exec("cmd /c ipconfig /all | find /N \"IPv4\"");
			     BufferedReader s2=new BufferedReader(new InputStreamReader(p2.getInputStream()));
			     p2.waitFor();
			     String site;
			     while((site=s2.readLine())!= null) {
			    	 if(site.contains(ip)) {
			    		 break;
			    	 }
			     }
			     int pos=Integer.parseInt(site.substring(site.indexOf("[")+1,site.indexOf("]")))-4;
			     s2.close();
			     
			     p2=Runtime.getRuntime().exec("cmd /c ipconfig /all | find /N \" Dirección física\" | find \"["+pos+"]\" ");
			     s2=new BufferedReader(new InputStreamReader(p2.getInputStream()));
			     p2.waitFor();
			     site=s2.readLine();
			     site=site.substring(site.indexOf(":")+1,site.length()).trim();
			     s2.close();
			     data=site;
		     }
		     s.close();
		     return data.replaceAll("-",":");
		} catch (Exception e1) {
		    return "";
		}
	}

	public static String getLocalip() {
		try {
		     Process p=Runtime.getRuntime().exec("cmd /c Nslookup exit");
		     BufferedReader s=new BufferedReader(new InputStreamReader(p.getInputStream()));
		     p.waitFor();
		     s.readLine();
		     String data=s.readLine();
		     s.close();
		    return data.substring(data.indexOf(":")+1,data.length()).trim();
		    } catch (Exception e1) {
		    	return "";
		    }
	}
	
	private String getbuilder(String mac,String disco) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(disco+"\\manuf")));
			String str;
			while ((str = br.readLine()) != null) {
				if(str.contains(mac.toUpperCase())) {
					str = str.replaceAll("\\s+", " ").trim();
					String[] div = str.split(" ");
					br.close();
					return div[1];
				}
			}
			try{br.close();}catch(Exception e) {}
			return "";
		} catch (Exception e1) {
			try{br.close();}catch(Exception e) {}
			return "";
		}
	}
	
}
