package jpa;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager entityManager = emf.createEntityManager();

		EntityTransaction tx = entityManager.getTransaction();
		tx.begin(); // 트랜잭션 시작

		try {
			// jpql 말고 그냥 쿼리 시작전에는 flush 를 해주자 영속성 컨텍스트에 있는거를 db 에 반영해주고 쿼리를 날려야 하기 때문
			entityManager.createNativeQuery("select MEMBER_ID, USERNAME, city, street, zipcode from MEMBER")
							.getResultList();

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
//기본타입
// int a = 1;
// int b = a; // 그냥 복사해서 값 전달한거임 a가 바껴도 b는 여전히 값을 가짐

// 객체 타입
// A a = new A();
// b = a; // 주소를 할당한거임 a가 바뀌면 b도 똑같이 바뀜 왜? 바라보는 곳이 바꼈으니까 그리고 이걸 막을 방법은 없음
// 그래서 불변 객체를 만들어서 아예 수정할 수 없게 만들어야한다. ex Integer, String
// setter 를 다 지운다.