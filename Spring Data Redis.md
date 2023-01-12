# Spring Data Redis

### Redis Support

redis는 key-value 저장소이다. memcached와 유사하지만 데이터 세트는 휘발성이 아니고 같은 memcache와 유사하게 문자열 일 수 있지만 list, set, sorted set일 수도 있다.

### Keys

Redis 키는 binary safe(잘 동작한다라는 뜻인)하다. 

- 매우 긴 키는 좋은 생각이 아님. 1024 byte (한글 512자리)는 메모리 측면 뿐만 아니라 키 조회시 비용이 높게 든다. 이거를 해싱하는 것이 메모리와 대역폭 측면에서 좋을 수 있다.
- 매우 짧은 키도 종종 좋은 생각이 아닐 수 있다. user:1000:followers 라고 쓰는 것을 u1000flw라고 써도 의미가 없게 된다. (짧은 키는 메모리를 덜 사용하지만 효율적인 것이 아닐 수 있다.)
- 스키마를 고수하는게 좋다. `object-type:id` (user:10)라고 쓰는 것은 좋다.
- 허용하는 최대 키 size `512MB`

## 데이터 유형

### string

문자열 유형은 redis 키와 연결할 수 있는 가장 간단한 유형. memcached의 유일한 데이터 유형이다. 

문자열 데이터 유형은 `HTML조각` 또는 `페이지 캐싱`과 같은 경우에 유용

```java
> set 1 good
> get 1
"good"
```

set을 할 때 키가 존재하는 경우 기존의 키가 가지고 있는 value를 대체한다. 

```java
> set counter 100
OK
> incr counter // counter하면 1을 올리는 동시에 get value가 실행, incr은 원자성을 띈다.
(integer) 101

```

키 확인 및 삭제

```java
127.0.0.1:6379> set 1 good
OK
127.0.0.1:6379> exists 1
(integer) 1 // true == 1, false == 0
127.0.0.1:6379> get 1
"good"
127.0.0.1:6379> del 1
(integer) 1 // true == 1, false == 0
127.0.0.1:6379> exists 1
(integer) 0 // true == 1, false == 0
```

### 키 만료

키 만료는 redis의 기능. 만료를 사용하면 수명 또는 TTL이라고도 하는 키 제한 시간을 설정할 수 있다. 

**참고사항**

- s 또는 ms 시간으로 설정할 수 있다.
- 만료에 대한 정보는 dsik에 복제되고 지속되며, 레디스에서 만료되는 날짜를 저장한다.

```java
127.0.0.1:6379> set key some-value
OK
127.0.0.1:6379> expire key 5 // 5초 후에 만료
(integer) 1
127.0.0.1:6379> get key
"some-value"
127.0.0.1:6379> get key
(nil)
127.0.0.1:6379> set 1 good ex 5 // key를 set하면서 expire 를 동시에 등록할 수 있다
OK
127.0.0.1:6379> get 1
"good"
127.0.0.1:6379> get 1
"good"
127.0.0.1:6379> get 1
(nil)
```

### Lists

레디스의 List는 연결 list로 구현이 되어 있다. 그래서 10개의 list가 있다고 했을 때 헤드에 요소를 추가하는 작업과 LPUSH로 천만 개의 데이터를 넣는 속도는 같다. 배열로 구현된 List는 매우 빠른데, 연결 목록으로 구현된 List는 그렇게 빠르지 않다. 

```java
/* 데이터 추가 */
127.0.0.1:6379> rpush list a // 뒤에 1개 추가
(integer) 1
127.0.0.1:6379> rpush list b // 뒤에 1개 추가
(integer) 2
127.0.0.1:6379> lpush list head // 앞에 1개 추가
(integer) 3
/* 데이터 조회 */
127.0.0.1:6379> lrange list 0 -1 // lrange는 {list} {시작 idx} {종료 idx}를 받는다.
1) "head"
2) "a"
3) "b"

/* 데이터 복수 추가 */
127.0.0.1:6379> rpush list 1 2 3 4 5 good // 데이터 구분자(space)로 여러 개 추가 가능
(integer) 9
127.0.0.1:6379> lrange list 0 -1
1) "head"
2) "a"
...
9) "good"
```

요소 꺼내기

```java
127.0.0.1:6379> rpop list // 맨 뒤에 요소 1개를 꺼내는 동시에 return
"good"
127.0.0.1:6379> lpop list // 맨 앞에 요소 1개를 꺼내는 동시에 return
"head"
127.0.0.1:6379> rpop list
(nil) // 데이터가 없으면 null로 나옴
```

List의 일반적인 사례

- 쇼설 네트워크에 게시한 최신 업데이트 목록
    - 사용자가 새로운 게시글을 게시할 때마다 lpush
    - 사용자가 홈페이지에 방문하면 lrange 0 9
- pub/sub 패턴을 사용하는 프로세스 간의 통신

## Set

레디스 set은 고유한 문자열의 정렬되지 않은 모음이다. 아래는 set을 효율적으로 사용하는 경우

- 고유한 항목을 추적한다. (액세스하는 고유한 ip 추적 같은 경우라든지)
- 관계를 나타낸다. (주어진 역할을 가진 모든 사용자 집합)
- 교집합, 합집합, 차이와 같은 집합 연산 가능

```java
127.0.0.1:6379> sadd user:1:favorites 347 // user 1에 favorites 347 등록
(integer) 1
127.0.0.1:6379> sadd user:1:favorites 350 // user 1에 favorites 350 등록
(integer) 1
127.0.0.1:6379> sadd user:2:favorites 500 // user 2에 favorites 500 등록
(integer) 1
127.0.0.1:6379> sismember user:1:favorites 347 // user 1에 347 있는데 확인
(integer) 1 // true
127.0.0.1:6379> sismember user:1:favorites 500 // user 1에 500 있는지 확인
(integer) 0 // false

/* 교집합 : 공통적으로 가지는 값 가져옴 */
127.0.0.1:6379> sinter user:1:favorites user:2:favorites
1) "350"
/* 집합의 크기 구하기 */
127.0.0.1:6379> scard user:1:favorites
(integer) 2
/* 지정 제거 */
127.0.0.1:6379> srem user:1:favorites 350
(integer) 1
127.0.0.1:6379> scard user:1:favorites
(integer) 1
/* 구성원 조회 */
127.0.0.1:6379> smembers user:1:favorites
1) "347"
```

set의 추가, 제거, 존재 체크의 작업은 O(1)이다. 하지만 매우 많은 수십만이상의 데이터를 가지고 있을 경우 `SMEMBERS` 명령은 O(n)을 실행하기 때문에 조심해야 한다.

- SMEMBERS : O(N)
- SSCAN : O(1)

레디스 `Set`은 주로 인덱스로 많이 사용된다. 

### 해쉬 (Hash)

redis hash는 그냥 java에서 map이라고 보면 될 듯 하다.

```java
127.0.0.1:6379> HSET user:123 username martina
(integer) 1
127.0.0.1:6379> hget user:123 username
"martina"
127.0.0.1:6379> hgetall user:123
1) "username"
2) "martina"
```

---

## 레디스 관리

### 메모리

- 리눅스에 swap이 활성화 되어 있지 않고 레디스 인스턴스가 실수로 너무 많은 메모리를 사용하면 redis가 충돌하거나 리눅스 커널 OOM킬러가 Redis 프로세스를 종료할 수 있다.
- 인스턴스에 명시적 maxmemory 옵션을 설정하면 시스템이 메모리 제한에 도달했을 경우 실패하지 ㅇ낳고 오류를 보고하도록 한다. 예로 여유 메모리가 10GB이면 8 or 9로 설정
- 쓰기 작업이 많은 애플리케이션에서 Redis를 사용하는 경우 RDB파일을 디스크에 저장하거나 AOF 로그를 다시 작성하는 동안 Redis에서 일시적으로 많은 메모리를 사용한다. (최대 2배)

---

### 필요 라이브러리

spring-data-redis

### Redis 요구 사항

spring Redis는 Redis 2.6 이상이 필요하고 spring data Redis는 레디스용 자바 라이브러리 Lettuce와 Jedis와 통합된다. 

- Lettuce : netty 기반 오픈소스 커넥터
- Jedis : 커뮤니티 기반 커텍터

### 레디스 연결

Spring과 Redis를 연갈하기 위해 IoC 커테이너를 통해 레디스 저장소에 연결하는 것이다. (java 커텍터가 필요함) 스프링에서 연결하기 위해서는 RedisConnection, RedisConnectionFactory 인터페이스를 사용해야 한다.

### RedisTemplate을 통한 작업

대부분의 사용자들인 RedisTemplate을 통해서 작업을 진행한다. 이유는 풍부한 기능을 제공하기 떄문

| HashOperations | 해시 작업 |
| --- | --- |
| ListOperations | 리스트 작업 |
| SetOperations | 셋 작업 |
| ValueOperations | 문자열 작업 |

보통의 내가 많이 쓰는 기능들로 정리하였다. RedisTemplate은 여러 인스턴스에서 재사용이 가능하게 되어있고 대부분의 작업에서 java 기반 직렬 변환기를 사용한다. 템플릿에서 write, read 모두 java를 통해서 직렬화 및 역직렬화 된다. 

### ReactiveRedisTemplate

| ReactiveSetOpertaions | 셋 작업 |
| --- | --- |
| ReactiveValueOperations | 문자열 작업 |
| ReactiveListOpertaions | 리스트 작업 |
| ReactiveZSetOperations | 정렬된 셋 작업 |
| ReactiveHashOperations | 해시 작업 |

### 직렬 변환기

- StringRedisSerializer :
- Jackson2JsonRedisSerializer

### 동시성 문제

- redis transaction : 낙관적 lock을 할 수 있음
- lua-script : 스크립트 구현이 편함, 스크립트 실행하는 동안 서버 동작이 block 되어 락을 획득하는 것처럼 된다.

[Spring Session + Custom Session Repository 기반 세션 저장소의 메모리 누수 해결](https://hyperconnect.github.io/2023/01/09/redis-session.html?ref=codenary)