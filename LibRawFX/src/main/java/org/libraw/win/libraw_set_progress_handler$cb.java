// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * int (*libraw_set_progress_handler$cb)(void*,enum LibRaw_progress,int,int);
 * }
 */
public interface libraw_set_progress_handler$cb {

    int apply(java.lang.foreign.MemorySegment data, int stage, int iteration, int expected);
    static MemorySegment allocate(libraw_set_progress_handler$cb fi, SegmentScope scope) {
        return RuntimeHelper.upcallStub(constants$7.libraw_set_progress_handler$cb_UP$MH, fi, constants$7.libraw_set_progress_handler$cb$FUNC, scope);
    }
    static libraw_set_progress_handler$cb ofAddress(MemorySegment addr, SegmentScope scope) {
        MemorySegment symbol = MemorySegment.ofAddress(addr.address(), 0, scope);
        return (java.lang.foreign.MemorySegment _data, int _stage, int _iteration, int _expected) -> {
            try {
                return (int)constants$7.libraw_set_progress_handler$cb_DOWN$MH.invokeExact(symbol, _data, _stage, _iteration, _expected);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}

