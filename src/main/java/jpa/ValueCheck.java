package jpa;

public class ValueCheck {

	public static void main(String[] args) {

		int a = 10;
		int b = 10;

		System.out.println("a == b = " + (a==b));

		Address address = new Address("city", "street", "1000");
		Address address2 = new Address("city", "street", "1000");

		System.out.println("address equals address2 = " + address.equals(address2)); // 오버라이딩 하기 전에 false
		// 객체 비교는 equals 로 해줘야하는데
		// equals 를 오버라이딩해서 재정의해줘야함
		// 실무에서 비교 할 일은 그렇게 없음 , 만약 비교해줘야한다면 이 방법 사용

	}

}
