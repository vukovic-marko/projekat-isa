package isa.projekat.model.RegisteredUser;


import isa.projekat.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"friend_requester_id", "friend_receiver_id"}),
        @UniqueConstraint(columnNames = {"friend_receiver_id", "friend_requester_id"})
})
public class Friendship implements Serializable {

    @Id
    @Column
    private long id;

    @ManyToOne
    private User friendRequester;

    @ManyToOne
    private User friendReceiver;

    @Column
    private Boolean confirmed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getFriendRequester() {
        return friendRequester;
    }

    public void setFriendRequester(User friendRequester) {
        this.friendRequester = friendRequester;
    }

    public User getFriendReceiver() {
        return friendReceiver;
    }

    public void setFriendReceiver(User friendReceiver) {
        this.friendReceiver = friendReceiver;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
