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

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	// cascade 하니까 main 에서 parent 저장하니까 join 되어있던 child 도 올라감
	// 영속성 전이 저장, 실무에서 자주 사용한다함
	// 그럼 언제 사용하냐 parent 만 child 를 관리할때 사용함 어느 게시물의 첨부파일마냥 child 는 parent 말고 다른 곳에서 쓰이면 안됨
	// child 의 소유자가 하나일때
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
