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
		int[] result;
		int index = 2;
		if (payload.length < 126) {
			result = new int[2 + payload.length];
			result[0] = opcode + 128;
			result[1] = payload.length;
		} else {
			result = new int[4 + payload.length];
			result[0] = opcode + 128;
			result[1] = 126;
			index = 4;
			result[2] = payload.length & 0xFF;
			result[3] = (payload.length & 0xFF00) >> 8;
		}
		for (int i = 0; i < payload.length; i++)
			result[index + i] = payload[i];
		return result;
	}

}
