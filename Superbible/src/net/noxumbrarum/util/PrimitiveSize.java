package net.noxumbrarum.util;

public enum PrimitiveSize {
	BYTE(1),
	SHORT(2),
	INT(4),
	LONG(8),
	FLOAT(4),
	DOUBLE(8),
	CHAR(2);
	private int size;
	private PrimitiveSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	
	
}
