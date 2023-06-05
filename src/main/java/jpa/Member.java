package jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "Member") // 다른 이름 지정가능
//@Table(name = "Member") // 이름 지정가능 , 엔티티와 메핑할 테이블 지정
public class Member {

	@Id // 이거하나만 생성하면 ID 수동 할당
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 이거까지 추가하면 id 자동할당함
	private Long id;

	// 객체에서는 username 쓰고 column 에서는 name 쓰고 싶을때
	// 유니크 조건은 여기서 쓰지말고 @Table 에서 쓰자
	@Column(name = "name")
	private String username;

	public Member() { // 기본 생성자는 무조건 필요하다
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

// 용어 정리
// 필드 : 클래스에 정의된 변수들, 종종 칼럼과 같은 의미로 사용되고 데이터의 최소 단위라 생각하면된다.
// 칼럼 : 테이블에서 열을 뜻하고, 데이터베이스에서 속성이라고 생각하면 된다.
// 테이블 : 빠른참조를 위해 표형태로 자료를 나타낸것
// 스키마 : 스키마는 데이터베이스를 구성하는 데이터 개체(Entity), 속성(Attribute), 관계(Relationship)
// 및 데이터 조작 시 데이터 값들이 갖는 제약 조건 등에 관해 전반적으로 정의한다.
