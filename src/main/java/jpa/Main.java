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
			// 정상적인 흐름
			Team team = new Team();
			team.setName("TeamA");
			entityManager.persist(team); // persist 하면 id 값을 알 수 있음

			Member member = new Member();
			member.setName("member1");
			member.setTeamId(team.getId()); // 위의 team id 를 여기에 넣어줘
			// 객체지향 스럽지가 않다. setTeam() 이라 해야할것같지만...
			entityManager.persist(member);

			// Member 의 team id 를 조회 할 때도 문제다
			Member findMember = entityManager.find(Member.class, member.getId());
			Long findTeamId = findMember.getId();
			Team findTeam = entityManager.find(Team.class, findTeamId);

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