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
		int[] result = new int[2 + payload.length];
		result[0] = opcode + 128;
		result[1] = payload.length;
		for (int i = 0; i < payload.length; i++)
			result[2 + i] = payload[i];
		return result;
	}

}
