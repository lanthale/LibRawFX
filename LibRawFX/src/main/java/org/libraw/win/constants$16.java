// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$16 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$16() {}
    static final VarHandle const$0 = constants$14.const$2.varHandle(MemoryLayout.PathElement.groupElement("pixel_aspect"));
    static final VarHandle const$1 = constants$14.const$2.varHandle(MemoryLayout.PathElement.groupElement("flip"));
    static final VarHandle const$2 = constants$14.const$2.varHandle(MemoryLayout.PathElement.groupElement("raw_aspect"));
    static final StructLayout const$3 = MemoryLayout.structLayout(
        JAVA_SHORT.withName("t"),
        JAVA_SHORT.withName("l"),
        JAVA_SHORT.withName("b"),
        JAVA_SHORT.withName("r")
    ).withName("libraw_area_t");
    static final VarHandle const$4 = constants$16.const$3.varHandle(MemoryLayout.PathElement.groupElement("t"));
    static final VarHandle const$5 = constants$16.const$3.varHandle(MemoryLayout.PathElement.groupElement("l"));
}


