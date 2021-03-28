package io.educare.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.educare.domain.User;
import io.educare.dto.UserDTO.UserRegister;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByUserId(String userId);

	Optional<User> findByUserId(String userId);

	User findUserByName(String username);

	User findByUserNum(Long userNum);

	void save(UserRegister entity);

	// 해당 가게를 리스트에 등록한 유저 반환
	@Query(value = "select user.* from user, res, user_res_list where res.res_num = :resNum and res.res_num = user_res_list.res_num and user_res_list.user_num = user.user_num", nativeQuery = true)
	List<User> findUsersOfRes(@Param("resNum") Long resNum);

	// 팔로워 많은 유저 순으로 반환
	@Query(value = "select user.* from user, (select followers.to_user_fk k from followers group by followers.to_user_fk order by count(*)) f where f.k = user.user_num", nativeQuery = true)
	List<User> findUsersByFollwerCount();

	// 내가 작성한 리뷰개수 카운트
	@Query(value = "select count(*) from review where review.user_num  = :userNum", nativeQuery = true)
	Long findcountOrderByUserNum(@Param("userNum") Long userNum);

	// 내가 만든 리스트 카운트
	@Query(value = "select count(distinct url.list_name) from user_res_list url, user ur where ur.user_num  = "
			+ ":user and url.user_num = ur.user_num", nativeQuery = true)
	Long findcountListByUserNum(@Param("user") Long userNum);

	// food_type 갯수로 내림차순 //named parameter
	@Query(value = "select rev.f_type from ( select review.food_type f_type from review where review.user_num = :userNum) rev group by rev.f_type order by count(rev.f_type) desc", nativeQuery = true)
	List<String> findListByUserNumNFoodType(@Param("userNum") Long userNum);
	
}
