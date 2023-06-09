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

			Child child1 = new Child();
			Child child2 = new Child();

			Parent parent = new Parent();
			parent.addChild(child1);
			parent.addChild(child2);

			entityManager.persist(parent);
			entityManager.persist(child1);
			entityManager.persist(child2);

			entityManager.flush();
			entityManager.clear();

			Parent findParent = entityManager.find(Parent.class, parent.getId());
			entityManager.remove(findParent);
			// 이러면 Parent 안에 childList 는 다 지워지기 때문에 child 도 비워진다.
			// cascade All 해놔도 결과는 똑같긴함

//			findParent.getChildList().remove(0);

			// parent 중심으로 Parent 가 저장될때 child 도 저장되면 좋겠어 하면
			// 영속성 전이(CASCADE)와 고아 객체를 동시에 사용하면 뭐가 좋나 parent 만 동작해서 child 까지 관리가능하다
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