package jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "team") // 뭐랑 연결한건지 표시해줌, 연관관계 주인은 team
	private List<Member> members = new ArrayList<>(); // null 안뜨도록 new 해주는게 관례다, 이거는 조회용임

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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Team{" +
				"id=" + id +
				", name='" + name + '\'' +
				", members=" + members +
				'}';
	}
}
