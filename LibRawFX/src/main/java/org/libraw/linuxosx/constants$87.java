// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$87 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$87() {}
    static final VarHandle const$0 = constants$86.const$0.varHandle(MemoryLayout.PathElement.groupElement("ExposureProgram"));
    static final VarHandle const$1 = constants$86.const$0.varHandle(MemoryLayout.PathElement.groupElement("ImageStabilization"));
    static final StructLayout const$2 = MemoryLayout.structLayout(
        JAVA_INT.withName("fsize"),
        JAVA_SHORT.withName("rw"),
        JAVA_SHORT.withName("rh"),
        JAVA_BYTE.withName("lm"),
        JAVA_BYTE.withName("tm"),
        JAVA_BYTE.withName("rm"),
        JAVA_BYTE.withName("bm"),
        JAVA_SHORT.withName("lf"),
        JAVA_BYTE.withName("cf"),
        JAVA_BYTE.withName("max"),
        JAVA_BYTE.withName("flags"),
        MemoryLayout.sequenceLayout(10, JAVA_BYTE).withName("t_make"),
        MemoryLayout.sequenceLayout(20, JAVA_BYTE).withName("t_model"),
        MemoryLayout.paddingLayout(1),
        JAVA_SHORT.withName("offset"),
        MemoryLayout.paddingLayout(2)
    ).withName("libraw_custom_camera_t");
    static final VarHandle const$3 = constants$87.const$2.varHandle(MemoryLayout.PathElement.groupElement("fsize"));
    static final VarHandle const$4 = constants$87.const$2.varHandle(MemoryLayout.PathElement.groupElement("rw"));
    static final VarHandle const$5 = constants$87.const$2.varHandle(MemoryLayout.PathElement.groupElement("rh"));
}

