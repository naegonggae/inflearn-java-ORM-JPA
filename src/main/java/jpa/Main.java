package jpa;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Hibernate;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager entityManager = emf.createEntityManager();

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin(); // 트랜잭션 시작

		try {

			Team team = new Team();
			team.setName("teamA");
			entityManager.persist(team);

			Member member1 = new Member();
			member1.setUsername("member1");
			member1.setTeam(team);
			entityManager.persist(member1);

			entityManager.flush();
			entityManager.clear();

			Member member = entityManager.find(Member.class, member1.getId()); // member 만 쿼리로 조회함
			System.out.println("member = " + member.getTeam().getClass()); // 프록시 클래스로 뜸

			System.out.println("==============");
			member.getTeam().getName(); // 이 시점에 team 을 사용하는 시점에 쿼리가 나간다.
			// 팀이 초기화 되면서 쿼리가 나감
			// 즉, LAZY 로 패치해놓으면 연관된걸 프록시로 가져온다.
			System.out.println("==============");

			tx.commit(); // 트랜잭션 종료 // 임시 저장했던 쿼리를 실제로 날린다.
		} catch (Exception e) {
			// 뭔가 에러나 취소가 있으면 롤백
			tx.rollback();
			e.printStackTrace();
//			System.out.println("e = " + e);
		} finally {
			entityManager.close();
		}
		// 마무리로 entityManger 닫아줘
		emf.close();
	}

	private static void printMember(Member member) {
		String username = member.getUsername();
		System.out.println("username = " + username);
	}

	private static void printMemberAndTeam(Member member) {
		String username = member.getUsername();
		System.out.println("username = " + username);

		Team team = member.getTeam();
		System.out.println("team = " + team.getName());

	}
}
// 근데 실무에서 그냥 즉시 로딩 쓰지마
// 예상치 못한 쿼리가 나갈 수 있다.