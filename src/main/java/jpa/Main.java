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

			Movie findMovie = entityManager.find(Movie.class, movie.getId()); // Item 과 inner join 해서 가져옴
			System.out.println("findMovie = " + findMovie);

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