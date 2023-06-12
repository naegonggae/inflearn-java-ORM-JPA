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
			List<Member> resultList = entityManager.createQuery(
					// JPQL 은 엔티티를 대상으로 쿼리를 짜고 SQL 은 DB 테이블 대상으로 짠다.
					// 객체 지향 SQL = JPQL
					"select m from Member m where m.username like '%KKim%'",
					Member.class
			).getResultList();

			for (Member member : resultList) {
				System.out.println("member = " + member);
			}

			//Criteria 사용
			// 자바 코드이기 때문에 컴파일 오류를 제공한다. 대신 복잡하다.
			// 김영한은 실무에서 안쓴다고함 유지보수가 힘들고 보기 힘들다
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();

			CriteriaQuery<Member> query = cb.createQuery(Member.class); // Member 에 대한 쿼리를 짤거야

			Root<Member> m = query.from(Member.class);

			CriteriaQuery<Member> cq = query.select(m);

			// 조건에 따라 쿼리를 짜는 동적 쿼리 수행에 있어 유리하다.
			String username = "sadsf";
			if (username != null) {
				cq = cq.where(cb.equal(m.get("username"), "Kim"));
			}
			List<Member> list = entityManager.createQuery(cq)
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