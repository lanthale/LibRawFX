// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * int (*pre_identify_callback)(void* ctx);
 * }
 */
public interface pre_identify_callback {

    int apply(java.lang.foreign.MemorySegment ctx);
    static MemorySegment allocate(pre_identify_callback fi, Arena scope) {
        return RuntimeHelper.upcallStub(constants$4.const$0, fi, constants$3.const$5, scope);
    }
    static pre_identify_callback ofAddress(MemorySegment addr, Arena arena) {
        MemorySegment symbol = addr.reinterpret(arena, null);
        return (java.lang.foreign.MemorySegment _ctx) -> {
            try {
                return (int)constants$4.const$1.invokeExact(symbol, _ctx);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


