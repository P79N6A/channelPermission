package com.haier.svc.util;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class ICStreamReaderDelegate extends StreamReaderDelegate {

    public ICStreamReaderDelegate(XMLStreamReader xsr) {
        super(xsr);
    }

    @Override
    public String getAttributeLocalName(int index) {
        return super.getAttributeLocalName(index).toLowerCase();
    }

    @Override
    public String getLocalName() {
        return super.getLocalName().toLowerCase();
    }
}
