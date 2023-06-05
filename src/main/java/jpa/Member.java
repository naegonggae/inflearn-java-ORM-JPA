package jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Member") // 이름 지정가능
public class Member {

	@Id
	private Long id;
	@Column(name = "name") // 이름 지정가능
	private String name;

	public Member() { // 기본 생성자는 무조건 필요하다
	}

	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
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
}
