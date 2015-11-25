package selesdepselesnul.sipakerserver.model;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import selesdepselesnul.sipakerserver.TimeString;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberRequestsKVStoreTest {

    private KvStoreManagerMocker<MemberRequestsKVStore> kvStoreManagerMocker;
    private MemberRequestsKVStore memberRequestsKVStore;

    @Before
    public void setUp() {
        this.kvStoreManagerMocker = new KvStoreManagerMocker<>(MemberRequestsKVStore::new);
        this.memberRequestsKVStore = kvStoreManagerMocker.whenCalledReturn(true).create();
    }

    @Test
    public void testMakeEmptyCollection() {
        assertTrue(this.memberRequestsKVStore.makeEmptyCollection());
    }

    @Test
    public void testStore() {
        this.kvStoreManagerMocker.whenGetValueCalledReturn(Optional.of("1"));
        assertTrue(this.memberRequestsKVStore.store(makeMemberRequestStub()));
    }

    private MemberRequest makeMemberRequestStub() {
        return new MemberRequest(1, 1, "D1234", TimeString.now());
    }

    @Test
    public void testGet() {
        assertThat(this.memberRequestsKVStore.get(1), IsEqual.equalTo(any()));
    }

    @Test
    public void testStream() {

    }

    @Test
    public void testLength() {

    }

    @Test
    public void testDropAll() {

    }

    @Test
    public void testDequeu() {

    }
}