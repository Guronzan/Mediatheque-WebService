package fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fr.guronzan.mediatheque.mappingclasses.domain.Cd;

@XmlRootElement
public class ListCds implements Iterable<Cd>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 931470090832156448L;
    private List<Cd> cds = null;

    public ListCds() {
    }

    public ListCds(final ArrayList<Cd> users) {
        this.cds = users;
    }

    public List<Cd> getCds() {
        return this.cds;
    }

    public void setCds(final List<Cd> users) {
        this.cds = users;
    }

    @Override
    public Iterator<Cd> iterator() {
        return this.cds.iterator();
    }
}
