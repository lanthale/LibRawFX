// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
public interface post_identify_callback {

    void apply(jdk.incubator.foreign.MemoryAddress x0);
    static MemoryAddress allocate(post_identify_callback fi) {
        return RuntimeHelper.upcallStub(post_identify_callback.class, fi, constants$2.post_identify_callback$FUNC, "(Ljdk/incubator/foreign/MemoryAddress;)V");
    }
    static MemoryAddress allocate(post_identify_callback fi, ResourceScope scope) {
        return RuntimeHelper.upcallStub(post_identify_callback.class, fi, constants$2.post_identify_callback$FUNC, "(Ljdk/incubator/foreign/MemoryAddress;)V", scope);
    }
    static post_identify_callback ofAddress(MemoryAddress addr) {
        return (jdk.incubator.foreign.MemoryAddress x0) -> {
            try {
                constants$2.post_identify_callback$MH.invokeExact((Addressable)addr, x0);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}

