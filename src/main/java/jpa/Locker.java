package jpa;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {

	@Id @GeneratedValue
	@Column(name = "LOCKER_ID")
	private Long id;

	private String name;

	@OneToOne(mappedBy = "locker") // 일대일 양방향 설정
	private Member member;
}
