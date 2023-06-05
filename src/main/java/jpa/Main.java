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

			Member findMember1 = entityManager.find(Member.class, 101L);
			// 영속성 컨텍스트(1차 캐시)에 101번 객체가 없어서 DB 에서 데이터를 가져온다. -> 조회 쿼리문 날림
			// DB 에서 가져온 101번 객체를 영속성 컨텍스트에 둔다.
			Member findMember2 = entityManager.find(Member.class, 101L);
			// 영속성 컨텍스트에 101번 객체가 있기때문에 조회 쿼리를 날리지 않고 그냥 가져온다.

			System.out.println("result = " + (findMember1 == findMember2));
			// 영속 엔티티는 동일성도 보장한다.

			tx.commit(); // 트랜잭션 종료 // 영속상태에 있는거를 DB에 저장 쿼리문 날리는 곳
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