package selesdepselesnul.sipakerserver.model;

import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselsnul)
 */
public interface MemberRequests {
    void init();
    void store(MemberRequest memberRequest);
    MemberRequest get(int index);
    Stream<MemberRequest> stream();
    int length();
    void dropAll();
    MemberRequest dequeu();
}
