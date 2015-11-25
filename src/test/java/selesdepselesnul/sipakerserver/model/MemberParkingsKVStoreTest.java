package selesdepselesnul.sipakerserver.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import selesdepselesnul.sipakerserver.TimeString;

import java.util.Optional;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberParkingsKVStoreTest {

    private MemberParkingsKVStore memberParkingsKVStore;
    private KvStoreManagerMocker<MemberParkingsKVStore> kvStoreManagerMocker;

    @Before
    public void init() {
        this.kvStoreManagerMocker = new KvStoreManagerMocker<>(MemberParkingsKVStore::new);
        this.memberParkingsKVStore = kvStoreManagerMocker.whenCalledReturn(true).create();
    }

    @Test
    public void testMakeEmptyCollectionWithInternetIsConnected() {
        assertTrue(memberParkingsKVStore.makeEmptyCollection());
    }

    @Test
    public void testMakeEmptyCollectionWithInternetIsNotConnected() {
        this.memberParkingsKVStore = kvStoreManagerMocker.whenCalledReturn(false).create();
        assertFalse(memberParkingsKVStore.makeEmptyCollection());
    }


    @Test
    public void testLengthWithInternetIsConnected() {
        mockLenghtCall();
        this.kvStoreManagerMocker.whenGetValueCalledReturn(Optional.of("1"));
        assertThat(memberParkingsKVStore.length(), is(equalTo(1)));
    }

    @Test
    public void testUpdate() {
        mockLenghtCall();
        assertTrue(memberParkingsKVStore.update(makeParkingAreaStub()));
    }

    private MemberParking makeParkingAreaStub() {
        return new MemberParking(
                1, 2, 3, "D1234", TimeString.now(), TimeString.now()
        );
    }

    @Test
    public void testStore() {
        mockLenghtCall();
        assertTrue(memberParkingsKVStore.store(makeParkingAreaStub()));
    }

    private void mockLenghtCall() {
        this.kvStoreManagerMocker.whenGetValueCalledReturn(Optional.of("1"));
    }

    @Test
    public void testDropCollection() {
        mockLenghtCall();
        assertTrue(memberParkingsKVStore.dropCollection());
    }
}