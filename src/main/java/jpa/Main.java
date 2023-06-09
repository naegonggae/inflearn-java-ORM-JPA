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

			Address address = new Address("city", "street", "1000");

			Member member1 = new Member();
			member1.setUsername("userA");
			member1.setAddress(address);
			entityManager.persist(member1);

			// setter 를 없애서 불변으로 만들었는데 address 를 변경하고 싶다 하면 아래같이 써서 수정해서 member1.setAddress(address); 통으로 해줘야함
			Address copyAddress = new Address(address.getCity(), address.getStreet(),
					address.getZipcode()); // 이렇게 값을 복사해 놓은걸 써야 독립적으로 사용가능하다.

			Member member2 = new Member();
			member2.setUsername("userA");
			member2.setAddress(copyAddress); // address 가 같은 주소를 바라보고 있음
			entityManager.persist(member2);

			member1.getAddress().setCity("newCity"); // 같은 주소를 바라보고 있는걸 수정하면 둘다 반영됨

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