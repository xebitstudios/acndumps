import java.util.List;

public class ObjTree {
	
	private String name;
	private List<String> childs;
	private int childcount = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String val) {
		this.name = val;
	}
	public List<String> getChilds() {
		return childs;
	}
	public void setChilds(List<String> val) {
		this.childs = val;
		this.setChildcount(val.size());
	}
	public int getChildcount() {
		return childcount;
	}
	private void setChildcount(int val) {
		this.childcount = val;
	}

}
