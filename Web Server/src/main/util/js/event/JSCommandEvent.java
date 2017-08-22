package main.util.js.event;

public class JSCommandEvent extends JSEvent {
	
	private String cmd;
	
	public JSCommandEvent(String cmd) {
		this.cmd = cmd;
	}
	
	public String getCmd() {
		return cmd;
	}

}
