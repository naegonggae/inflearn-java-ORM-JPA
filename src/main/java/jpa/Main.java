package jpa;

import java.util.List;
import java.util.Set;
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

			Member member = new Member();
			member.setUsername("userA");
			member.setAddress(new Address("c", "s", "10000"));

			// 값 타입은 라이프 사이클이 없음 그래서 member 라이프사이클에 끼어있음
			member.getFavoriteFood().add("치킨");
			member.getFavoriteFood().add("족발");
			member.getFavoriteFood().add("피자");

			member.getAddressHistory().add(new Address("old1", "s", "10000"));
			member.getAddressHistory().add(new Address("old2", "s", "10000"));

			entityManager.persist(member);

			entityManager.flush();
			entityManager.clear();

			System.out.println("START=================");
			Member m = entityManager.find(Member.class, member.getId());

			List<Address> addressHistory = m.getAddressHistory(); // 처음에 조회 안하다가 필요로하니까 조회하기 시작함
			for (Address address : addressHistory) {
				System.out.println("address = " + address.getCity());
			}
			Set<String> favoriteFood = m.getFavoriteFood();
			for (String ff : favoriteFood) {
				System.out.println("favoriteFood = " + ff);
			}

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