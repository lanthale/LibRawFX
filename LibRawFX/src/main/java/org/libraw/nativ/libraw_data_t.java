// Generated by jextract

package org.libraw.nativ;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     ushort (*image)[4];
 *     libraw_image_sizes_t sizes;
 *     libraw_iparams_t idata;
 *     libraw_lensinfo_t lens;
 *     libraw_makernotes_t makernotes;
 *     libraw_shootinginfo_t shootinginfo;
 *     libraw_output_params_t params;
 *     libraw_raw_unpack_params_t rawparams;
 *     unsigned int progress_flags;
 *     unsigned int process_warnings;
 *     libraw_colordata_t color;
 *     libraw_imgother_t other;
 *     libraw_thumbnail_t thumbnail;
 *     libraw_thumbnail_list_t thumbs_list;
 *     libraw_rawdata_t rawdata;
 *     void *parent_class;
 * }
 * }
 */
public class libraw_data_t {

    libraw_data_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_POINTER.withName("image"),
        libraw_image_sizes_t.layout().withName("sizes"),
        libraw_iparams_t.layout().withName("idata"),
        libraw_lensinfo_t.layout().withName("lens"),
        libraw_makernotes_t.layout().withName("makernotes"),
        libraw_shootinginfo_t.layout().withName("shootinginfo"),
        MemoryLayout.paddingLayout(2),
        libraw_output_params_t.layout().withName("params"),
        libraw_raw_unpack_params_t.layout().withName("rawparams"),
        libraw_h.C_INT.withName("progress_flags"),
        libraw_h.C_INT.withName("process_warnings"),
        libraw_colordata_t.layout().withName("color"),
        libraw_imgother_t.layout().withName("other"),
        libraw_thumbnail_t.layout().withName("thumbnail"),
        libraw_thumbnail_list_t.layout().withName("thumbs_list"),
        libraw_rawdata_t.layout().withName("rawdata"),
        libraw_h.C_POINTER.withName("parent_class")
    ).withName("$anon$1070:11");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout image$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("image"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ushort (*image)[4]
     * }
     */
    public static final AddressLayout image$layout() {
        return image$LAYOUT;
    }

    private static final long image$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ushort (*image)[4]
     * }
     */
    public static final long image$offset() {
        return image$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ushort (*image)[4]
     * }
     */
    public static MemorySegment image(MemorySegment struct) {
        return struct.get(image$LAYOUT, image$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ushort (*image)[4]
     * }
     */
    public static void image(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(image$LAYOUT, image$OFFSET, fieldValue);
    }

    private static final GroupLayout sizes$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("sizes"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_image_sizes_t sizes
     * }
     */
    public static final GroupLayout sizes$layout() {
        return sizes$LAYOUT;
    }

    private static final long sizes$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_image_sizes_t sizes
     * }
     */
    public static final long sizes$offset() {
        return sizes$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_image_sizes_t sizes
     * }
     */
    public static MemorySegment sizes(MemorySegment struct) {
        return struct.asSlice(sizes$OFFSET, sizes$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_image_sizes_t sizes
     * }
     */
    public static void sizes(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, sizes$OFFSET, sizes$LAYOUT.byteSize());
    }

    private static final GroupLayout idata$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("idata"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_iparams_t idata
     * }
     */
    public static final GroupLayout idata$layout() {
        return idata$LAYOUT;
    }

    private static final long idata$OFFSET = 192;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_iparams_t idata
     * }
     */
    public static final long idata$offset() {
        return idata$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_iparams_t idata
     * }
     */
    public static MemorySegment idata(MemorySegment struct) {
        return struct.asSlice(idata$OFFSET, idata$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_iparams_t idata
     * }
     */
    public static void idata(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, idata$OFFSET, idata$LAYOUT.byteSize());
    }

    private static final GroupLayout lens$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("lens"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_lensinfo_t lens
     * }
     */
    public static final GroupLayout lens$layout() {
        return lens$LAYOUT;
    }

    private static final long lens$OFFSET = 632;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_lensinfo_t lens
     * }
     */
    public static final long lens$offset() {
        return lens$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_lensinfo_t lens
     * }
     */
    public static MemorySegment lens(MemorySegment struct) {
        return struct.asSlice(lens$OFFSET, lens$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_lensinfo_t lens
     * }
     */
    public static void lens(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, lens$OFFSET, lens$LAYOUT.byteSize());
    }

    private static final GroupLayout makernotes$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("makernotes"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_makernotes_t makernotes
     * }
     */
    public static final GroupLayout makernotes$layout() {
        return makernotes$LAYOUT;
    }

    private static final long makernotes$OFFSET = 1928;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_makernotes_t makernotes
     * }
     */
    public static final long makernotes$offset() {
        return makernotes$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_makernotes_t makernotes
     * }
     */
    public static MemorySegment makernotes(MemorySegment struct) {
        return struct.asSlice(makernotes$OFFSET, makernotes$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_makernotes_t makernotes
     * }
     */
    public static void makernotes(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, makernotes$OFFSET, makernotes$LAYOUT.byteSize());
    }

    private static final GroupLayout shootinginfo$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("shootinginfo"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_shootinginfo_t shootinginfo
     * }
     */
    public static final GroupLayout shootinginfo$layout() {
        return shootinginfo$LAYOUT;
    }

    private static final long shootinginfo$OFFSET = 4880;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_shootinginfo_t shootinginfo
     * }
     */
    public static final long shootinginfo$offset() {
        return shootinginfo$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_shootinginfo_t shootinginfo
     * }
     */
    public static MemorySegment shootinginfo(MemorySegment struct) {
        return struct.asSlice(shootinginfo$OFFSET, shootinginfo$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_shootinginfo_t shootinginfo
     * }
     */
    public static void shootinginfo(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, shootinginfo$OFFSET, shootinginfo$LAYOUT.byteSize());
    }

    private static final GroupLayout params$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("params"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_output_params_t params
     * }
     */
    public static final GroupLayout params$layout() {
        return params$LAYOUT;
    }

    private static final long params$OFFSET = 5024;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_output_params_t params
     * }
     */
    public static final long params$offset() {
        return params$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_output_params_t params
     * }
     */
    public static MemorySegment params(MemorySegment struct) {
        return struct.asSlice(params$OFFSET, params$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_output_params_t params
     * }
     */
    public static void params(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, params$OFFSET, params$LAYOUT.byteSize());
    }

    private static final GroupLayout rawparams$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("rawparams"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_raw_unpack_params_t rawparams
     * }
     */
    public static final GroupLayout rawparams$layout() {
        return rawparams$LAYOUT;
    }

    private static final long rawparams$OFFSET = 5328;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_raw_unpack_params_t rawparams
     * }
     */
    public static final long rawparams$offset() {
        return rawparams$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_raw_unpack_params_t rawparams
     * }
     */
    public static MemorySegment rawparams(MemorySegment struct) {
        return struct.asSlice(rawparams$OFFSET, rawparams$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_raw_unpack_params_t rawparams
     * }
     */
    public static void rawparams(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, rawparams$OFFSET, rawparams$LAYOUT.byteSize());
    }

    private static final OfInt progress_flags$LAYOUT = (OfInt)$LAYOUT.select(groupElement("progress_flags"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int progress_flags
     * }
     */
    public static final OfInt progress_flags$layout() {
        return progress_flags$LAYOUT;
    }

    private static final long progress_flags$OFFSET = 5376;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int progress_flags
     * }
     */
    public static final long progress_flags$offset() {
        return progress_flags$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int progress_flags
     * }
     */
    public static int progress_flags(MemorySegment struct) {
        return struct.get(progress_flags$LAYOUT, progress_flags$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int progress_flags
     * }
     */
    public static void progress_flags(MemorySegment struct, int fieldValue) {
        struct.set(progress_flags$LAYOUT, progress_flags$OFFSET, fieldValue);
    }

    private static final OfInt process_warnings$LAYOUT = (OfInt)$LAYOUT.select(groupElement("process_warnings"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * unsigned int process_warnings
     * }
     */
    public static final OfInt process_warnings$layout() {
        return process_warnings$LAYOUT;
    }

    private static final long process_warnings$OFFSET = 5380;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * unsigned int process_warnings
     * }
     */
    public static final long process_warnings$offset() {
        return process_warnings$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * unsigned int process_warnings
     * }
     */
    public static int process_warnings(MemorySegment struct) {
        return struct.get(process_warnings$LAYOUT, process_warnings$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * unsigned int process_warnings
     * }
     */
    public static void process_warnings(MemorySegment struct, int fieldValue) {
        struct.set(process_warnings$LAYOUT, process_warnings$OFFSET, fieldValue);
    }

    private static final GroupLayout color$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("color"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_colordata_t color
     * }
     */
    public static final GroupLayout color$layout() {
        return color$LAYOUT;
    }

    private static final long color$OFFSET = 5384;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_colordata_t color
     * }
     */
    public static final long color$offset() {
        return color$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_colordata_t color
     * }
     */
    public static MemorySegment color(MemorySegment struct) {
        return struct.asSlice(color$OFFSET, color$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_colordata_t color
     * }
     */
    public static void color(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, color$OFFSET, color$LAYOUT.byteSize());
    }

    private static final GroupLayout other$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("other"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_imgother_t other
     * }
     */
    public static final GroupLayout other$layout() {
        return other$LAYOUT;
    }

    private static final long other$OFFSET = 192416;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_imgother_t other
     * }
     */
    public static final long other$offset() {
        return other$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_imgother_t other
     * }
     */
    public static MemorySegment other(MemorySegment struct) {
        return struct.asSlice(other$OFFSET, other$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_imgother_t other
     * }
     */
    public static void other(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, other$OFFSET, other$LAYOUT.byteSize());
    }

    private static final GroupLayout thumbnail$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("thumbnail"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_thumbnail_t thumbnail
     * }
     */
    public static final GroupLayout thumbnail$layout() {
        return thumbnail$LAYOUT;
    }

    private static final long thumbnail$OFFSET = 193216;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_thumbnail_t thumbnail
     * }
     */
    public static final long thumbnail$offset() {
        return thumbnail$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_thumbnail_t thumbnail
     * }
     */
    public static MemorySegment thumbnail(MemorySegment struct) {
        return struct.asSlice(thumbnail$OFFSET, thumbnail$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_thumbnail_t thumbnail
     * }
     */
    public static void thumbnail(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, thumbnail$OFFSET, thumbnail$LAYOUT.byteSize());
    }

    private static final GroupLayout thumbs_list$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("thumbs_list"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_thumbnail_list_t thumbs_list
     * }
     */
    public static final GroupLayout thumbs_list$layout() {
        return thumbs_list$LAYOUT;
    }

    private static final long thumbs_list$OFFSET = 193240;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_thumbnail_list_t thumbs_list
     * }
     */
    public static final long thumbs_list$offset() {
        return thumbs_list$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_thumbnail_list_t thumbs_list
     * }
     */
    public static MemorySegment thumbs_list(MemorySegment struct) {
        return struct.asSlice(thumbs_list$OFFSET, thumbs_list$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_thumbnail_list_t thumbs_list
     * }
     */
    public static void thumbs_list(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, thumbs_list$OFFSET, thumbs_list$LAYOUT.byteSize());
    }

    private static final GroupLayout rawdata$LAYOUT = (GroupLayout)$LAYOUT.select(groupElement("rawdata"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * libraw_rawdata_t rawdata
     * }
     */
    public static final GroupLayout rawdata$layout() {
        return rawdata$LAYOUT;
    }

    private static final long rawdata$OFFSET = 193504;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * libraw_rawdata_t rawdata
     * }
     */
    public static final long rawdata$offset() {
        return rawdata$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * libraw_rawdata_t rawdata
     * }
     */
    public static MemorySegment rawdata(MemorySegment struct) {
        return struct.asSlice(rawdata$OFFSET, rawdata$LAYOUT.byteSize());
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * libraw_rawdata_t rawdata
     * }
     */
    public static void rawdata(MemorySegment struct, MemorySegment fieldValue) {
        MemorySegment.copy(fieldValue, 0L, struct, rawdata$OFFSET, rawdata$LAYOUT.byteSize());
    }

    private static final AddressLayout parent_class$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("parent_class"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * void *parent_class
     * }
     */
    public static final AddressLayout parent_class$layout() {
        return parent_class$LAYOUT;
    }

    private static final long parent_class$OFFSET = 381248;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * void *parent_class
     * }
     */
    public static final long parent_class$offset() {
        return parent_class$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * void *parent_class
     * }
     */
    public static MemorySegment parent_class(MemorySegment struct) {
        return struct.get(parent_class$LAYOUT, parent_class$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * void *parent_class
     * }
     */
    public static void parent_class(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(parent_class$LAYOUT, parent_class$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}
