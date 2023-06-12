package jpa;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String city;
	private String street;
	@Column(name = "zipcode")//이름 지정 가능
	private String zipcode;

	// entity 도 들어올 수 있음
//	private Member member;

	public Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	private void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	private void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Address address)) {
			return false;
		}
		return Objects.equals(getCity(), address.getCity()) && Objects.equals(
				getStreet(), address.getStreet()) && Objects.equals(getZipcode(),
				address.getZipcode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCity(), getStreet(), getZipcode());
	}
}
