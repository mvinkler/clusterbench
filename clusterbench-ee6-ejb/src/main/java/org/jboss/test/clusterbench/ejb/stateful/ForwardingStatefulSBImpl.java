package org.jboss.test.clusterbench.ejb.stateful;

import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

@Stateful
@Clustered
public class ForwardingStatefulSBImpl implements RemoteStatefulSB {
    private RemoteStatefulSB bean;

    @SuppressWarnings("unchecked")
    private RemoteStatefulSB forward() {
        if (bean == null) {
            try {
                Hashtable props = new Hashtable();
                props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                Context context = new InitialContext(props);
                bean = (RemoteStatefulSB) context.lookup("ejb:" + "clusterbench-ee6/clusterbench-ee6-ejb//"
                        + RemoteStatefulSBImpl.class.getSimpleName() + "!" + RemoteStatefulSB.class.getName()
                        + "?stateful");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return bean;
    }

    @Override
    public int getSerial() {
        return forward().getSerial();
    }

    @Override
    public int getSerialAndIncrement() {
        return forward().getSerialAndIncrement();
    }

    @Override
    public byte[] getCargo() {
        return forward().getCargo();
    }
}
