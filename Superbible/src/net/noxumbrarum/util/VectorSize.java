package net.noxumbrarum.util;

public enum VectorSize {
	Vec4f(4, DataTypeInfo.FLOAT);
	
	
	
	private int size;
	private VectorSize(int count, DataTypeInfo primitiveSize) {
		this.size = count * primitiveSize.getJavaSize();
	}
	
	
	public int getSizeAsBytes() {
		return size;
	}
}
