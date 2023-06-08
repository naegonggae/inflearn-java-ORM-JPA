package jpa;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager entityManager = emf.createEntityManager();

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin(); // 트랜잭션 시작

		try {

			Member member1 = new Member();
			member1.setUsername("member1");
			entityManager.persist(member1);

			Member member2 = new Member();
			member2.setUsername("member2");
			entityManager.persist(member2);

			entityManager.flush();
			entityManager.clear();

			Member m1 = entityManager.find(Member.class, member1.getId());
			Member m2 = entityManager.getReference(Member.class, member2.getId());
			System.out.println("m1 + m2 = " + (m1.getClass() == m2.getClass())); // 다르다
			// 파일 비교는 == 으로 하면 안됨
			System.out.println("m1 + m2 = " + (m1 instanceof Member)); // 클래스를 비교할때는 이렇게 해야함
			System.out.println("m1 + m2 = " + (m2 instanceof Member));

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
// 조인전략으로 우선 진행하고 만약 비즈니스 모델이 단순하고 확장할 일이 별로 없을것 같다 하면 단일 테이블 전략 사용
// DB 설계자와 상의를 해봐야함