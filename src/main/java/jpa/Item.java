package jpa;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)// 상속받는것들을 각각으로 생성
@DiscriminatorColumn// (name = "aaa") 칼럼명 변경 가능 // 상속한것중에 어떤건지 알려줌 , DTYPE 알려줌
// 상속관계를 테이블에 나타낼때 사용하길 권장
public class Item {
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
