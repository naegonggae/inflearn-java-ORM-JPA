package jpa;

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

			Member member = new Member();
			member.setUsername("memberA");
			entityManager.persist(member);

			Team team = new Team();
			team.setName("TeamA");
			// 여기서 update 쿼리가 나감
			team.getMembers().add(member);

			entityManager.persist(team);

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
// 일대일 관계가 명확할 경우에만 사용하는데
// 만약 일대일 관계를 사용할 경우 member 객체에서 Locker 를 가지고 member 테이블에 FK 를 둔다.
// 주 테이블에 외래키 vs 대상 테이블에 외래키 관련 장단점 볼 필요 있음
// 주 테이블에 외래키는 FK 가 null 이 날 수 있다.
// 대상 테이블에 외래키는 지연로딩이 안된다. 어짜피 대상 테이블까지 뒤져봐야하니까