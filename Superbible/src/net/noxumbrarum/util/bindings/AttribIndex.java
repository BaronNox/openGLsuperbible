package net.noxumbrarum.util.bindings;

public class AttribIndex {
	/**
	 * Get the attribIndex (e.g. layout (location = 0): 0 is attribIndex) of supplied type
	 * @param type Bindings enum
	 * @return int representing the attribIndex
	 */
	public static int get(Indeces type) {
		return type.getAttribIndex();
	}
}
