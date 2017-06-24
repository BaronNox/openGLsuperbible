package net.noxumbrarum.util.bindings;

public enum Indeces {
	VERTIX(0, 0),
	
	
	
	ATTRIB_TEST(100, -1),
	;
	private int attribIndex;
	private int bindingIndex;
	
	private Indeces(int atttribIndex, int bindingIndex) {
		this.attribIndex = atttribIndex;
		this.bindingIndex = bindingIndex;
	}

	public int getAttribIndex() {
			try {
				if(attribIndex < 0)
					throw new IllegalIndexException("attribIndex is < 0 (" + attribIndex + ")");
			} catch (IllegalIndexException e) {
				e.printStackTrace();
			}
		return attribIndex;
	}

	public int getBindingIndex() {
		try {
			if(bindingIndex < 0)
				throw new IllegalIndexException("bindingIndex is < 0 (" + bindingIndex + ")");
		} catch (IllegalIndexException e) {
			e.printStackTrace();
		}
		return bindingIndex;
	}
}
