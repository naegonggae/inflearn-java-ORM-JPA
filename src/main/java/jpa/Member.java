package jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "Member") // 다른 이름 지정가능
//@Table(name = "Member") // 이름 지정가능 , 엔티티와 메핑할 테이블 지정
public class Member {

	@Id
	private Long id;

	// 객체에서는 username 쓰고 column 에서는 name 쓰고 싶을때
	// 유니크 조건은 여기서 쓰지말고 @Table 에서 쓰자
	@Column(name = "name")
	private String username;

	private Integer age;

	// enum 을 DB에 쓰고 싶을때
	// 디폴트가 ORDINAL 인데 이거는 enum 에 쓰인 순서대로 0,1,2 카운트해서 숫자로 표시하는거야
	// 그래서 변경되었을때 오류가 많아서 반드시 String 사용
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	// 시간을 DB에 저장할때는 Temporal 를 사용하는데 3가지 내부 속성중에 골라준다.
	@Temporal(TemporalType.TIMESTAMP) // 요건 날짜 시간 둘다 속함
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// 최신 기술은 이거임
	private LocalDate testLocalDate; // DATE
	private LocalTime testLocalTime; // TIME
	private LocalDateTime testLocalDateTime; // TIMESTAMP

	// varchar 를 넘어서는 큰 컨텐츠를 사용할때
	@Lob
	private String description; // dlob clob 있는데 String 은 clob 임

	// DB 에 저장하지 않는 필드로 지정
	@Transient
	private int temp;
	public Member() { // 기본 생성자는 무조건 필요하다
	}
}

// 용어 정리
// 필드 : 클래스에 정의된 변수들, 종종 칼럼과 같은 의미로 사용되고 데이터의 최소 단위라 생각하면된다.
// 칼럼 : 테이블에서 열을 뜻하고, 데이터베이스에서 속성이라고 생각하면 된다.
// 테이블 : 빠른참조를 위해 표형태로 자료를 나타낸것
// 스키마 : 스키마는 데이터베이스를 구성하는 데이터 개체(Entity), 속성(Attribute), 관계(Relationship)
// 및 데이터 조작 시 데이터 값들이 갖는 제약 조건 등에 관해 전반적으로 정의한다.
