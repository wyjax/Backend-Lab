### Querydsl 학습



##### jpql 

jpql의 단점은 문법 오류를 자바 컴파일러가 알 수 없고, 실제 실행 시점에 알 수 있다.

이것의 단점을 보완해서 querydsl을 사용하여 극복가능



#### QType 생성방법

```java
QMember qMember = new QMember("member"); // 별칭 직접 지정
QMember qMember = QMember.member; // 기본 인스턴스 사용
```



패치 조인은 sql에서 제공하는 기능은 아니고 sql에서 필요한 정보를 한 번에 조회하는 기능



#### 동적 쿼리

1. BooleanBuilder 
2. where 다중 파라미터 사용