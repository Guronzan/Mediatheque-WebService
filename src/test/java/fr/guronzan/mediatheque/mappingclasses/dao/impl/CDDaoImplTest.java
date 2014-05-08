package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.domain.CD;
import fr.guronzan.mediatheque.mappingclasses.domain.types.CDKindType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

public class CDDaoImplTest extends SpringTests {

    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final CDKindType CD_KIND_TYPE = CDKindType.POP;

    @Resource
    private CDDao cdDao;

    @Before
    public void cleanDB() {
        this.cdDao.removeAllCDs();
    }

    private Integer addNewCD() {
        final CD cd = new CD(TITLE);
        cd.setAuthorName(AUTHOR);
        cd.setKind(CD_KIND_TYPE);
        return this.cdDao.create(cd);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddDuplicate() {
        addNewCD();
        addNewCD();
    }

    @Test
    public final void testGetCdByTitle() {
        assertFalse(this.cdDao.contains(TITLE));

        addNewCD();
        assertTrue(this.cdDao.contains(TITLE));
        assertFalse(this.cdDao.contains("title2"));
    }

    @Test
    public final void testGetCdsByAuthor() {

        assertTrue(this.cdDao.getCdsByAuthor(AUTHOR).isEmpty());
        addNewCD();
        final Collection<CD> cdsByAuthor = this.cdDao.getCdsByAuthor(AUTHOR);
        assertFalse(cdsByAuthor.isEmpty());
        assertThat(cdsByAuthor.size(), is(1));
    }

}
