package isa.projekat.repository;

import isa.projekat.model.RegisteredUser.Friendship;
import isa.projekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    /*@Query("select Friendship.friendRequester as frReq " +
            "from Friendship " +
            "where frReq.username = :username and " +
            "Friendship.active = true")
    List<User> findFriendsAsReceiver(@Param("username") String username);

   @Query("select Friendship.friendReceiver as frRec " +
            "from Friendship " +
            "where frRec.username = :username and " +
            "Friendship.active = true")
    List<User> findFriendsAsRequester(@Param("username") String username);*/
}

