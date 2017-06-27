package net.noxumbrarum.util;

public enum DataTypeInfo {
	BYT		( 1, -1, -1),
	SHORT	( 2, -1, -1),
	INT		( 4,  4,  4),
	LONG	( 8, -1, -1),
	FLOAT	( 4,  4,  4),
	DOUBLE	( 8, -1, -1),
	CHAR	( 2, -1, -1),
	BOOL	(-1,  4,  4),
	
	VEC2F	( 8,  8,  8),
	VEC3F	(12, 12, 16),
	VEC4F	(16, 16, 16)
	
	
	;
	
	private int javaSize;
	private int gslsSize;
	private int gslsOffset;
	
	private DataTypeInfo(int javaSize, int gslsSize, int gslsOffset) {
		this.javaSize = javaSize;
		this.gslsSize = gslsSize;
		this.gslsOffset = gslsOffset;
	}
	public int getJavaSize() {
			try {
				if(javaSize < 1)
					throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return javaSize;
	}
	
	public int getGSLSSize() {
		try {
			if(gslsSize < 1)
				throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return gslsSize;
}
	
	
}
