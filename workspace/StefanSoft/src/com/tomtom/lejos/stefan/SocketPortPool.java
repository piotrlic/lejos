package com.tomtom.lejos.stefan;

public class SocketPortPool {
	private static final int[] SERVER_PORT_POOL = { 6666, 6667 };
	private int i = 0;

	public int getPort() {
		i = (i == 0 ? 1 : 0);
		return SERVER_PORT_POOL[i];
	}
}
