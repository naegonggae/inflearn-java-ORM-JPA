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
// 일대다는 권장하지 않음 다대일을 그냥 쓰자
// 실무에서는 setter 를 잘 쓰지 않음
// 코드가 많아지고 테이블이 많아지고 하면 도대체 저장만했는데 update 쿼리가 날라갔는지 당황할 수 있다. 물론 쿼리 수가 많아진다는 단점도 있는데
// 신경쓸 정도의 단점은 아니다.
