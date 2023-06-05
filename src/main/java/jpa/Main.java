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

			Member member = new Member();
			member.setUsername("A");

			System.out.println("==========");
			entityManager.persist(member);
			// IDENTITY 전략을 사용해서 id 를 자동 생성하면 member 를 저장하는 경우 예외적으로 바로 쿼리를 날린다.
			// 이유는 영속성 컨텍스트(1차 캐시)에 저장하려면 id 값이 필요한데 위의 경우에는 알 수 없기 때문이다.
			System.out.println("member.id = " + member.getId());
			// persistence() 하고 쿼리를 바로 날리게 되면 그때 서야 id가 생성되고 id 값을 조회해 출력할 수 있게 된다.
			// 이렇게 되면 버퍼링 기능을 사용할 수 없다는 단점이 있는데 크게 상관은 없다고 한다.
			System.out.println("==========");
			// 시퀀스 전략은 id 를 시퀀스에서 50개씩(디폴트) 생성해서 받아오기때문에 persistence()에서 쿼리를 바로 날리지 않고 트랜잭션을 유지한다.

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