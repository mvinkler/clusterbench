package org.jboss.test.clusterbench.ejb.stateless;

import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

@Stateless
@Clustered
public class ForwardingStatelessSBImpl implements RemoteStatelessSB {
    @SuppressWarnings("unchecked")
    private RemoteStatelessSB forward() {
        try {
            Hashtable props = new Hashtable();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            Context context = new InitialContext(props);
            return (RemoteStatelessSB) context.lookup("ejb:" + "clusterbench-ee6/clusterbench-ee6-ejb//"
                    + RemoteStatelessSBImpl.class.getSimpleName() + "!" + RemoteStatelessSB.class.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getNodeName() {
        return forward().getNodeName();
    }
}
