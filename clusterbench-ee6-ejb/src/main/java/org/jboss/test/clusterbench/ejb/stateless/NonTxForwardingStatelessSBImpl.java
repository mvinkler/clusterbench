package org.jboss.test.clusterbench.ejb.stateless;

import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@Clustered
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NonTxForwardingStatelessSBImpl extends AbstractForwardingStatelessSBImpl
        implements RemoteStatelessSB {
}
