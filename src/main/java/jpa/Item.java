package jpa;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 싱글 테이블은 상속받은 클래스들을 엔티티로 저장하지 않고 부모클래스에 뭉쳐놓는다
//@DiscriminatorColumn 구현 클래스마다 단일 테이블 전략은 이걸 사용하지 않는다.
public abstract class Item { // 구현 클래스마다 단일 테이블 전략, 이건 쓰지마 그냥
	@Id @GeneratedValue
	private Long id;
	private String name;
	private int price;

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
