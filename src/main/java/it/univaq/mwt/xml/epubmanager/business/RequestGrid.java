package it.univaq.mwt.xml.epubmanager.business;

public class RequestGrid implements java.io.Serializable {
	private long iDisplayStart;
	private long iDisplayLength;
	private String sEcho;
	private String sSearch;
	private String sortCol;
	private String sortDir;
	
	
	public long getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(long iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public long getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(long iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	
	
}
