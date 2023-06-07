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
			entityManager.persist(team); // persist 하면 id 값을 알 수 있음

			Member member = new Member();
			member.setUsername("member1");
			member.setTeam(team); // 이렇게 하면 알아서 조인 해줌

			entityManager.persist(member);

			entityManager.flush();
			entityManager.clear();

			// 조회
			Member findMember = entityManager.find(Member.class, member.getId());
			List<Member> members = findMember.getTeam().getMembers(); // 양방향 연관관계
			for (Member m : members) {
				System.out.println("m = " + m.getUsername());
			}

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
