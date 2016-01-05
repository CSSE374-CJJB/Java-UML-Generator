package edu.rosehulman.cjjb.asm;

public class Relation {
	public String base;
	public String relatedTo;
	
	public Relation(String base, String relatedTo) {
		this.base = base;
		this.relatedTo = relatedTo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((relatedTo == null) ? 0 : relatedTo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (relatedTo == null) {
			if (other.relatedTo != null)
				return false;
		} else if (!relatedTo.equals(other.relatedTo))
			return false;
		return true;
	}
}
