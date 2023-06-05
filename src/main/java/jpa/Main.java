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

			Member member = entityManager.find(Member.class, 150L);
			member.setName("ZZZZ");
			// 수정할때는 그냥 값만 변경해줘도 알아서 commit 할때 수정쿼리날려줌
			// 변경감지
			// commit 하면 영속성 컨텍스트 내부에 flush() 가 호출됨
			// 1차 캐시에 객체 아이디, 객체, 이전에 저장되었던 객체 스냅샷 이렇게 있는데 현재 객체와 이전 스냅샷을 비교함
			// 비교해서 달라진게 있으면 쓰기 지연 SQL 저장소에 update 쿼리문 작성해서 모아둠
			// DB 에 flush 함 -> commit DB 에 반영
			// 삭제도 똑같이 메커니즘으로 스냅샷과 비교해서 객체가 없으면 remove 쿼리를 날림

//			entityManager.persist(member); 수정한다고 이렇게 하면 안된다.

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