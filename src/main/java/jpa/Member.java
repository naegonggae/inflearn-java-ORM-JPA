package jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "Member") // 다른 이름 지정가능
//@Table(name = "Member") // 이름 지정가능 , 엔티티와 메핑할 테이블 지정
public class Member extends BaseEntity {

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	// 즉시가 디폴트인가?
	@ManyToOne(fetch = FetchType.EAGER) // 지연로딩 LAZY 를 사용해서 team 을 프록시로 조회 Member 클래스만 조회하고
	//EAGER = 만약 member 와 team 을 빈번하게 조회한다면 선택, 왜냐 쿼리를 같이 날리기 때문에
	//LAZY = 만약 member 만 많이 쓰고 가끔 team 을 조회한다면 선택, member 쿼리만 날리고 team 은 필요할때 프록시로 사용하기 때문
	@JoinColumn
	private Team team;

	@OneToOne
	@JoinColumn(name = "LOCKER_ID")
	private Locker locker;

	@OneToMany(mappedBy = "member")
	private List<MemberProduct> memberProducts = new ArrayList<>();
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public List<MemberProduct> getMemberProducts() {
		return memberProducts;
	}

	public void setMemberProducts(List<MemberProduct> memberProducts) {
		this.memberProducts = memberProducts;
	}
}

// 용어 정리
// 필드 : 클래스에 정의된 변수들, 종종 칼럼과 같은 의미로 사용되고 데이터의 최소 단위라 생각하면된다.
// 칼럼 : 테이블에서 열을 뜻하고, 데이터베이스에서 속성이라고 생각하면 된다.
// 테이블 : 빠른참조를 위해 표형태로 자료를 나타낸것
// 스키마 : 스키마는 데이터베이스를 구성하는 데이터 개체(Entity), 속성(Attribute), 관계(Relationship)
// 및 데이터 조작 시 데이터 값들이 갖는 제약 조건 등에 관해 전반적으로 정의한다.
