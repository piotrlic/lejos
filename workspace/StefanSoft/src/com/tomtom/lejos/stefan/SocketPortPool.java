package com.tomtom.lejos.stefan;

public class SocketPortPool {
	private static final int[] SERVER_PORT_POOL = { 6667, 6666 };
	private int i = 0;

	public int getPort() {
		i = (i == 0 ? 0 : 1);
		return SERVER_PORT_POOL[i];
	}
}
