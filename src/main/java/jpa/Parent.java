package jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Parent {

	@Id @GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	// orphanRemoval = true 해놓으면 childList 에서 값이 빠지면 진짜 삭제해버린다.
	// 참조하는 곳이 하나일 경우 사용해
	private List<Child> childList = new ArrayList<>();

	public void addChild(Child child) {
		childList.add(child);
		child.setParent(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Child> getChildList() {
		return childList;
	}

	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}
}
