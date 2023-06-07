package jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		// persistence.xml 에 있는 Persistence unit name = hello
		// EntityManagerFactory 는 웹이 실행될 때 딱 하나만 생성이 됨
		EntityManager entityManager = emf.createEntityManager();
		// EntityManager 는 고객 요청이 올때마다 썼다가 지웠다가 함
		// 쓰레드간 공유 X, 사용하고 버려야함
		// JPA 의 모든 변경은 트랜잭션 내부에서 실행되어야한다.

		// 실제 동작 코드 작성

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin(); // 트랜잭션 시작

		try {
			// 저장
			Team team = new Team();
			team.setName("TeamA");
//			team.getMembers().add(member); // member 리스트에 저장한경우 null 이 뜬다.
			entityManager.persist(team); // persist 하면 id 값을 알 수 있음

			Member member = new Member();
			member.setUsername("member1");
			member.changeTeam(team); // 이렇게 주인한테 넣어줘야지 정상 반영이된다. /**
			entityManager.persist(member);

			// 3. 그래서 양방향일 경우 주인과 그 반대편 리스트 모두 값을 넣어줘야 객체 지향적이라고 할 수 있다.
//			team.getMembers().add(member); /** 이 두개를 반드시 해줘야하는데 까먹기 쉽다 그래서 setTeam() 메서드에 이 로직을 넣어준다.

			// 2. 하지만 이 두개를 주석처리하면 member 리스트의 값은 나오지 않는다. 문제점 1
//			entityManager.flush();
//			entityManager.clear(); // 이게 있으면 방금 저장한것들이 1차캐시에 머물지 않고 DB로 들어가 join에 의해 값이 공유되서
			// 다음 조회할때 member 리스트에 반영이된다. 하지만 이걸 주석처리하면 1차캐시에만 머물러서 member 리스트는 값을 공유받지 못한 상태에서 출력이된다.

			Team findTeam = entityManager.find(Team.class, team.getId());
			List<Member> members = findTeam.getMembers();
			System.out.println("=============");
			System.out.println("findTeam = " + findTeam); // 양방향 매핑시 member 와 team 에 toString()이 되어있다. 그러면 서로 계속 불러오기 때문에 무한루프에 걸린다.
			// 양방향 매핑할때 toString 어노테이션 쓰지마라, 컨트롤러에서 entity 를 뽑아내게 하지 마라 -> jSON 생성 라이브러리로 인한 문제 해결
			System.out.println("=============");

			tx.commit(); // 트랜잭션 종료 // 임시 저장했던 쿼리를 실제로 날린다.
		} catch (Exception e) {
			// 뭔가 에러나 취소가 있으면 롤백
			tx.rollback();
		} finally {
			entityManager.close();
		}
		// 마무리로 entityManger 닫아줘
		emf.close();
	}
}
// 객체는 참조를 활용하고 테이블은 외래키로 조인을 하는데 뭐가 다른가
// 연관관계 주인이 있구나 알 수 있어
// 객체는 양방향 맵핑을 하기 위해 단방향 조인을 서로서로 했다고 생각해야함
// 테이블은 FK 키를 설정하므로써 양방향이 가능해진다.
// many 쪽을 주인으로 해라
// 처음 설계 할때 일대 다 인경우에 다인 경우에다가 단방향으로 쭉만들고 어쩔 수 없이 다 쪽으로 조회해야한다면 그거만 양방향으로 바꿔라
