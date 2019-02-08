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

    // Neprihvaceni zahtjevi
    List<Friendship> findByFriendRequester_IdAndConfirmedFalse(Long requesterId);
    List<Friendship> findByFriendReceiver_IdAndConfirmedFalse(Long receiverId);

    // Po jednom korisniku
    List<Friendship> findByFriendRequester_Id(Long id);
    List<Friendship> findByFriendReceiver_Id(Long id);

    List<Friendship> findByFriendRequester_IdAndConfirmedTrue(Long id);
    List<Friendship> findByFriendReceiver_IdAndConfirmedTrue(Long id);

    // Po oba korisnika
    Friendship findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedFalse(Long receiverId, Long requesterId);

    Friendship findByFriendReceiver_IdAndFriendRequester_IdAndConfirmedTrue(Long receiverId, Long requesterId);
}

