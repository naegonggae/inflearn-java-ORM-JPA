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