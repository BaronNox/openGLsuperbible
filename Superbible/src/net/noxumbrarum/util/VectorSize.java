package net.noxumbrarum.util;

public enum VectorSize {
	Vec4f(4, PrimitiveSize.FLOAT);
	
	
	
	private int size;
	private VectorSize(int count, PrimitiveSize primitiveSize) {
		this.size = count * primitiveSize.getSize();
	}
	
	
	public int getSizeAsBytes() {
		return size;
	}
}
