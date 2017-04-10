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
		int[] result = new int[10 + payload.length];
		result[0] = opcode + 128;
		result[1] = 127;
		int len = payload.length;
		byte[] lenBytes = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(len).array();
		for (int i = 0; i < 4; i++)
			result[2 + i] = 0x00;
		for (int i = 0; i < 4; i++)
			result[6 + i] = lenBytes[i];
		for (int i = 0; i < payload.length; i++)
			result[10 + i] = payload[i];
		return result;
	}

}
