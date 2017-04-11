package main.websocket;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
		int length = payload.length > 8192 ? 8192 : payload.length;
		if (length < 126) {
			result = new int[2 + length];
			result[0] = opcode + 128;
			result[1] = length;
		} else {
			result = new int[4 + length];
			result[0] = opcode + 128;
			result[1] = 126;
			index = 4;
			byte[] lengthBytes = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(length).array();
			result[2] = lengthBytes[2];
			result[3] = lengthBytes[3];
		}
		for (int i = 0; i < length; i++)
			result[index + i] = payload[i];
		return result;
	}

}
