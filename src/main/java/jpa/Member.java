package jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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

	@Embedded
	private Period period;

	@Embedded
	private Address address;

	// 컬렉션을 저장하기 위한 별도의 테이블들임
	// 타입 컬렉션은 지연로딩 사용한다.
	@ElementCollection
	@CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
	@Column(name = "FOOD_NAME")
	private Set<String> favoriteFood = new HashSet<>();

//	@ElementCollection
//	@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//	private List<Address> addressHistory = new ArrayList<>();
	// 변경이 있을경우 다 지우고 다시 다 추가함 .. 쓰면 안된다.. 이걸쓰지말고 일대다를 쓰는걸 고려해야해

	// 이렇게 사용하는게 낫다
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MEMBER_ID")
	private List<AddressEntity> addressHistory = new ArrayList<>();

	@Embedded // 이렇게 중복 사용은 안됨
	// 아래 어노테이셔 붙여서 하나하나 칼럼 이름을 재정의 해주면 가능
	@AttributeOverrides({
			@AttributeOverride(name = "city", column = @Column(name = "x_city")),
			@AttributeOverride(name = "street", column = @Column(name = "x_street")),
			@AttributeOverride(name = "zipcode", column = @Column(name = "x_zipcode"))
	})
	private Address workAddress;

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

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<String> getFavoriteFood() {
		return favoriteFood;
	}

	public void setFavoriteFood(Set<String> favoriteFood) {
		this.favoriteFood = favoriteFood;
	}

	public List<AddressEntity> getAddressHistory() {
		return addressHistory;
	}

	public void setAddressHistory(List<AddressEntity> addressHistory) {
		this.addressHistory = addressHistory;
	}

	public Address getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}
}

// 용어 정리
// 필드 : 클래스에 정의된 변수들, 종종 칼럼과 같은 의미로 사용되고 데이터의 최소 단위라 생각하면된다.
// 칼럼 : 테이블에서 열을 뜻하고, 데이터베이스에서 속성이라고 생각하면 된다.
// 테이블 : 빠른참조를 위해 표형태로 자료를 나타낸것
// 스키마 : 스키마는 데이터베이스를 구성하는 데이터 개체(Entity), 속성(Attribute), 관계(Relationship)
// 및 데이터 조작 시 데이터 값들이 갖는 제약 조건 등에 관해 전반적으로 정의한다.
