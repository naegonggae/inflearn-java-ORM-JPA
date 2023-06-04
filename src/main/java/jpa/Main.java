package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		// persistence.xml 에 있는 Persistence unit name = hello
		EntityManager entityManager = emf.createEntityManager();
		// 실제 동작 코드 작성

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin(); // 트랜잭션 시작

		try {
			// 정상적인 흐름
			Member member = new Member();
			member.setId(2L);
			member.setName("helloB");
			entityManager.persist(member);

			tx.commit(); // 트랜잭션 종료
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