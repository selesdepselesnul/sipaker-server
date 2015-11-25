package selesdepselesnul.sipakerserver.model;

import org.mockito.Mockito;
import selesdepselesnul.sipakerserver.Manager.KVStoreManager;

import java.util.Optional;
import java.util.function.Function;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class KvStoreManagerMocker<T> {

    private T object;
    private KVStoreManager kvStoreManager;

    public KvStoreManagerMocker(Function<KVStoreManager, T> maker) {
        this.kvStoreManager = Mockito.mock(KVStoreManager.class);
        this.object = maker.apply(kvStoreManager);
    }

    public KvStoreManagerMocker<T> whenCalledReturn(boolean returnValue) {
        when(kvStoreManager.createCollection(anyString())).thenReturn(returnValue);
        when(kvStoreManager.storeValue(anyString(), anyString(), anyString())).thenReturn(returnValue);
        when(kvStoreManager.deleteCollection(anyString())).thenReturn(returnValue);
        return this;
    }

    public KvStoreManagerMocker<T> whenGetValueCalledReturn(Function<KVStoreManager, Optional<String>> methodCalled, Optional<String> returnValue) {
        when(methodCalled.apply(this.kvStoreManager)).thenReturn(returnValue);
        return this;
    }

    public T create() {
        return this.object;
    }

    public KvStoreManagerMocker<T> whenGetValueCalledReturn(Optional<String> returnValue) {
        when(kvStoreManager.getValue(anyString(), anyString())).thenReturn(returnValue);
        return this;
    }
}
