package main.websocket;

import java.io.IOException;
import java.io.InputStream;

public class WSRequest {

	private int opcode;
	private byte[] payload;

	public WSRequest(InputStream is) throws WSParseException, IOException {
		opcode = is.read();
		if (opcode == -1)
			throw new WSParseException(true);
		int length = 0;
		int lengthBits = is.read() - 128;
		if (lengthBits > 125) {
			if (lengthBits == 126)
				for (int i = 0; i < 2; i++)
					length = length * 256 + is.read();
			else if (lengthBits == 127)
				for (int i = 0; i < 8; i++)
					length = length * 256 + is.read();
			else
				throw new WSParseException(false);
		} else
			length = lengthBits;
		int[] key = new int[4];
		for (int i = 0; i < 4; i++)
			key[i] = is.read();
		payload = new byte[length];
		for (int i = 0; i < length; i++)
			payload[i] = (byte) (is.read() ^ key[i & 0x3]);
	}

	public byte getOpcode() {
		return (byte) (opcode & 0x7F);
	}

	public byte[] getPayloadBytes() {
		return payload;
	}

	public String getPayloadString() {
		if (opcode != 129)
			return null;
		return new String(payload);
	}

	public boolean isFinished() {
		return (opcode & 0x80) == 0x80;
	}

}
