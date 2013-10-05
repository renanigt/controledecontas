package br.com.controledecontas.serializer;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class JSONSerializer {

	private static final String DEFAULT_NEW_LINE = "";
	private static final char[] DEFAULT_LINE_INDENTER = {};
    
	private static final String INDENTED_NEW_LINE = "\n";
	private static final char[] INDENTED_LINE_INDENTER = {' ', ' '};
	
	private boolean indented;
	private boolean withoutRoot;
	
	private XStream xstream;
	
	public JSONSerializer() {
		this.xstream = getXStream();
	}
	
	private XStream getXStream() {
        final String newLine = (indented ? INDENTED_NEW_LINE : DEFAULT_NEW_LINE);
        final char[] lineIndenter = (indented ? INDENTED_LINE_INDENTER : DEFAULT_LINE_INDENTER);

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                if (withoutRoot) {
                    return new JsonWriter(writer, lineIndenter, newLine, JsonWriter.DROP_ROOT_MODE);
                }

                return new JsonWriter(writer, lineIndenter, newLine);
            }
        });
        
        xstream.setMode(XStream.NO_REFERENCES);
        
        return xstream;
    }

	public JSONSerializer indented() {
		indented = true;
		xstream = getXStream();
		
		return this;
	}
	
	public JSONSerializer withoutRoot() {
		withoutRoot = true;
		xstream = getXStream();
		
		return this;
	}

	public String serialize(Object object) {
		return xstream.toXML(object);
	}
	
	public String serialize(String alias, Object object) {
		xstream.alias(alias, object.getClass());
		
		return xstream.toXML(object);
	}
	
}
