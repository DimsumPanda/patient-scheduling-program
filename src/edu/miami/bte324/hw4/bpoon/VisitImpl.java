package edu.miami.bte324.hw4.bpoon;

import java.util.Date;
import java.util.Comparator;

/**
 * @author Barbara Poon
 *
 */
public class VisitImpl<V,T> implements Visit<V,T>{

	V visitor;
	T host;
	Date vdate;
// ===========================================================
//	Constructor for VisitImpl
//	=========================================================
	VisitImpl (V visitor, T host, Date vdate){
		this.visitor = visitor;
		this.host = host;
		this.vdate = vdate;
	}
	
public void setVisitor(V visitor) {
		this.visitor = visitor;
	}

	public void setHost(T host) {
		this.host = host;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public void setVisitComparator(Comparator<Visit<V, T>> visitComparator) {
		VisitComparator = visitComparator;
	}

	// ===========================================================
//	Override the Getter methods
//	========================================================
	@Override
	public V getVisitor() {
		return visitor;
	}

	@Override
	public T getHost() {
		return host;
	}

	@Override
	public Date getDate() {
		return vdate;
	}
// ===========================================================
//		Override the equals and the Hashcode
//	========================================================	
	@Override
	public boolean equals(Object obj) {
//		if (obj instanceof Visit<?,?>){
//			if(((Visit<?,?>)obj).getDate().equals(getDate()) 
//					&& ((Visit<?,?>)obj).getVisitor().equals(getVisitor())
//					&& ((Visit<?,?>)obj).getHost().equals(getHost()) ){
//				return true;
//			}
//		}
		
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()) {
			return true;
		}
		Visit<?,?> other = (Visit<?,?>) obj;
		if ((this.getVisitor()!= null)&&(this.getHost() != null)&&(this.getDate()!=null)){
			return true;
		}
		if ((this.getVisitor()==(other.getVisitor()))&& (this.getHost().equals(other.getHost()))
				&& (this.getDate().equals(other.getDate())))
			return true;
			return false;
	}
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.visitor != null ? this.visitor.hashCode() : 0);
		hash = 53 * hash + (this.host != null ? this.host.hashCode() : 0);
		hash = 53 * hash + (this.vdate != null ? this.vdate.hashCode() : 0);
		return hash;
	}
// ===========================================================
//	Override the Getter methods
//	========================================================
//	@Override
//	public int compareTo(Visit<V,T> o) {
//		VisitImpl<V,T> other = (VisitImpl<V,T>) o;
//		int i = this.vdate.compareTo(other.vdate);
//		if (i != 0) return i;
//		else return 0;
//	}
	public Comparator<Visit<V,T>> VisitComparator = new Comparator<Visit<V,T>>(){
		public int compare(Visit<V,T> v1, Visit<V,T> v2){
			return v1.getDate().compareTo(v2.getDate());
		}
		
	};
}


