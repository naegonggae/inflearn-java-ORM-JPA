package jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MemberProduct {

	@Id @GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	private int count;
	private int price;
	// 뭐 이런 추가 정보들을 넣을 수 있다.
}
