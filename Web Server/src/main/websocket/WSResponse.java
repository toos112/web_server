package main.websocket;

public class WSResponse {
	
	private int opcode;
	private byte[] payload;
	
	public WSResponse(int opcode) {
		this.opcode = opcode;
	}
	
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}
	
	public int[] getBytes() {
		if (payload == null)
			payload = new byte[0];
		int[] result = new int[10 + payload.length];
		result[0] = opcode;
		result[1] = 127;
		int len = payload.length;
		for (int i = 0; i < 8; i++)
			result[2 + i] = (byte) ((len & (0xFF << (i * 8))) >> (i * 8));
		for (int i = 0; i < payload.length; i++)
			result[10 + i] = payload[i];
		return result;
	}

}
