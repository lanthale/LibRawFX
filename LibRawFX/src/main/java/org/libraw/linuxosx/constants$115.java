// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$115 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$115() {}
    static final VarHandle const$0 = constants$114.const$0.varHandle(MemoryLayout.PathElement.groupElement("nextifd"));
    static final VarHandle const$1 = constants$114.const$0.varHandle(MemoryLayout.PathElement.groupElement("pad2"));
    static final VarHandle const$2 = constants$114.const$0.varHandle(MemoryLayout.PathElement.groupElement("nexif"));
    static final VarHandle const$3 = constants$114.const$0.varHandle(MemoryLayout.PathElement.groupElement("pad3"));
    static final VarHandle const$4 = constants$114.const$0.varHandle(MemoryLayout.PathElement.groupElement("ngps"));
    static final FunctionDescriptor const$5 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        JAVA_INT
    );
    static final MethodHandle const$6 = RuntimeHelper.downcallHandle(
        "libraw_strerror",
        constants$115.const$5
    );
}

