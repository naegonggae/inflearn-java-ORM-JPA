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

			Member member1 = new Member(150L, "A");
			Member member2 = new Member(160L, "B");

			// JDBC 배치 기능 사용할 수 있다.
			// persistence 에 배치 사이즈만큼 모아서 저장시키기 때문에 적은 요청으로 저장가능
			// 버퍼링 기능이라 하고 쉽게 말하면 하나하나 보내던걸 모아서 보낸다는 느낌
			entityManager.persist(member1);
			entityManager.persist(member2);
			// 영속성 컨텍스트에 데이터를 저장하고, 임시 저장 insert SQL 문을 작성해 둔다. -> 쓰기 지연
			System.out.println("================");

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