package fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fr.guronzan.mediatheque.mappingclasses.domain.User;

@XmlRootElement
public class ListUsers implements Iterable<User>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2070243750745122099L;
    private List<User> users = null;

    public ListUsers() {
    }

    public ListUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    @Override
    public Iterator<User> iterator() {
        return this.users.iterator();
    }
}
