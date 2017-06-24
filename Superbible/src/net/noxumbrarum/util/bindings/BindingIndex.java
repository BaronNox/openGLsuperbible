package net.noxumbrarum.util.bindings;

public class BindingIndex {
	/**
	 * Get the bindingIndex of supplied type.
	 * bindingIndex is the index of the VAO type is bound to.
	 * @param type Binding enum
	 * @return int representing the bindingIndex
	 */
	public static int get(Indeces type) {
		return type.getBindingIndex();
	}
}
