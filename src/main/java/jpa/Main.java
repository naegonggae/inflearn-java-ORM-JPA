package jpa;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

//			Member member = entityManager.find(Member.class, member1.getId()); // member 만 쿼리로 조회함
			List<Member> members = entityManager.createQuery(
							"select m from Member m", Member.class)
					.getResultList();
			// JPQL 은 "select m from Member m" 이게 번역이 되는데 이러면 member 만 가져오게 됨 -> 이때 즉시 로딩이기 때문에 TEAM 도 불러옴
			// 리스트의 사이즈 만큼 team 을 불러옴
			System.out.println(members.size());

			// SQL : Select * from Member
			// SQL : Select * from Team where TEAM_ID = xxx

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
// 다 지연로딩으로 쓰다가 한번에 불러와야할 경우가 있다면 JPQL 에서 패치 조인해서 불러온다.