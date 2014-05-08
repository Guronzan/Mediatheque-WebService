package fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

@XmlRootElement
public class DomainObjects implements Iterable<DomainObject>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3868347692860715588L;
    private List<DomainObject> objects = null;

    public DomainObjects() {
    }

    public DomainObjects(final List<? extends DomainObject> domainObjects) {
        this.objects = (List<DomainObject>) domainObjects;
    }

    public List<DomainObject> getObjects() {
        return this.objects;
    }

    public void setObjects(final List<DomainObject> objects) {
        this.objects = objects;
    }

    @Override
    public Iterator<DomainObject> iterator() {
        return this.objects.iterator();
    }
}
