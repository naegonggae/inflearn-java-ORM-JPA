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

			Movie movie = new Movie();
			movie.setDirector("aa");
			movie.setActor("BB");
			movie.setName("바람");
			movie.setPrice(10000);

			entityManager.persist(movie);

			entityManager.flush();
			entityManager.clear();

			Item item = entityManager.find(Item.class, movie.getId());// Item 과 inner join 해서 가져옴
			System.out.println("item = " + item);
			// 부모 클래스로 조회하면 자식클래스를 다 뒤진다. 저장할때는 심플함

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
// 싱글 테이블 전략
// 성능이 좋다. insert 쿼리도 한방 select 쿼리도 한방 join 해서 조회할 필요도 없다.