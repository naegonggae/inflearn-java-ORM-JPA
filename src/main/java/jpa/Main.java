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


			entityManager.flush();
			entityManager.clear();

			//#2
			Member member = entityManager.getReference(Member.class, member1.getId());
			System.out.println("member = " + member.getClass()); // 프록시

			Member reference = entityManager.find(Member.class, member1.getId());
			System.out.println("reference = " + reference.getClass()); // class jpa.Member
			// 과연 위의 두개가 같은 타입으로 나올까?
			// 결론 둘다 프록시로 맞춰줌

			//#1
//			Member member = entityManager.find(Member.class, member1.getId());
//			System.out.println("member = " + member.getClass()); // class jpa.Member
//
//			Member reference = entityManager.getReference(Member.class, member1.getId());
//			System.out.println("reference = " + reference.getClass()); // class jpa.Member

			// jpa 에서는 이걸 보장해야해
			System.out.println("a == a = " + (member==reference)); // 한 트랜잭션 안에서 같은 영속성 컨텍스트의 값을 가져오면 == 을 보장함
			// 왜 두개다 class jpa.Member 인가?
			// 1. 영속성 컨텍스트에 class jpa.Member 가 올라가있기때문에 굳이 프록시클래스를 생성할 이유가 없다.
			// 2. == 비교했을때 true 를 반환시키기위해 즉, 같은 영속성 컨텍스트에 있는걸 사용했기때문에 같은 클래스가 나온다.


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