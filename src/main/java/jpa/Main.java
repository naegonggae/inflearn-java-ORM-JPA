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
			// 영속성 컨텍스트에 없는걸 조회해서 데이터를 DB 에서 가져오고 영속성 컨텍스트에 저장

			member.setName("AAAAAA");
			// 영속성 컨텍스트에서 값 변경

			entityManager.detach(member);
			// 영속성 컨텍스트에 하나 들어와있던 데이터를 무시하기로 함
			// commit 할때 수정이 되었지만 detach 를 해서 영속성 컨텍스트에서 제외했기때문에 아무일도 일어나지 않음

			// 준영속으로 만들기
//			entityManager.clear();
//			entityManager.close();
//			entityManager.detach(member);

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